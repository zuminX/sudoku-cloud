package com.zumin.sudoku.gateway.filter

import cn.hutool.json.JSONUtil
import com.nimbusds.jose.JWSObject
import com.zumin.sudoku.common.core.auth.*
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.redis.RedisUtils
import com.zumin.sudoku.gateway.writeFailed
import org.apache.logging.log4j.util.Strings
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * 全局过滤器
 */
@Component
class AuthGlobalFilter(
  private val redisUtils: RedisUtils,
) : GlobalFilter, Ordered {

  /**
   * 过滤请求
   *
   * @param exchange Web交互对象
   * @param chain    网关过滤器链
   * @return 过滤结果
   */
  override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
    // 非JWT或者JWT为空不作处理
    var token = exchange.request.headers.getFirst(AUTHORIZATION_KEY)
    if (token.isNullOrBlank() || !token.startsWith(AUTHORIZATION_PREFIX)) {
      return chain.filter(exchange)
    }
    // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在拦截响应token失效
    token = token.replace(AUTHORIZATION_PREFIX, Strings.EMPTY)
    val payload = JWSObject.parse(token).payload.toString()
    val isBlack = redisUtils.has("$TOKEN_BLACKLIST_PREFIX${JSONUtil.parseObj(payload).getStr(JWT_JTI)}")
    if (isBlack) {
      return exchange.response.writeFailed(AuthStatusCode.TOKEN_ACCESS_FORBIDDEN)
    }
    // 存在token且不是黑名单，request写入JWT的载体信息
    return chain.filter(exchange.mutate().request(exchange.request.mutate().header(JWT_PAYLOAD_KEY, payload).build()).build())
  }

  override fun getOrder() = 0
}