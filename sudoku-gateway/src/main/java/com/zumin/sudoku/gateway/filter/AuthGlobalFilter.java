package com.zumin.sudoku.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.zumin.sudoku.common.core.auth.AuthConstants;
import com.zumin.sudoku.common.core.auth.AuthRedisKey;
import com.zumin.sudoku.common.core.auth.AuthStatusCode;
import com.zumin.sudoku.common.redis.utils.RedisUtils;
import com.zumin.sudoku.gateway.utils.GatewayUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 */
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

  private final RedisUtils redisUtils;

  /**
   * 过滤请求
   *
   * @param exchange Web交互对象
   * @param chain    网关过滤器链
   * @return 过滤结果
   */
  @Override
  @SneakyThrows
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();

    // 非JWT或者JWT为空不作处理
    String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
    if (StrUtil.isBlank(token) || !token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
      return chain.filter(exchange);
    }

    // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在拦截响应token失效
    token = token.replace(AuthConstants.AUTHORIZATION_PREFIX, Strings.EMPTY);
    JWSObject jwsObject = JWSObject.parse(token);
    String payload = jwsObject.getPayload().toString();
    JSONObject jsonObject = JSONUtil.parseObj(payload);
    String jti = jsonObject.getStr(AuthConstants.JWT_JTI);
    boolean isBlack = redisUtils.has(AuthRedisKey.TOKEN_BLACKLIST_PREFIX + jti);
    if (isBlack) {
      return GatewayUtils.writeFailedToResponse(response, AuthStatusCode.TOKEN_ACCESS_FORBIDDEN);
    }

    // 存在token且不是黑名单，request写入JWT的载体信息
    request = exchange.getRequest().mutate().header(AuthConstants.JWT_PAYLOAD_KEY, payload).build();
    exchange = exchange.mutate().request(request).build();
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
