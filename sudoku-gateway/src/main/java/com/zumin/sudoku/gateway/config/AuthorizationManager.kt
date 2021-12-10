package com.zumin.sudoku.gateway.config

import com.zumin.sudoku.common.core.auth.RESOURCE_ROLES_KEY
import com.zumin.sudoku.common.redis.RedisUtils
import mu.KotlinLogging
import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.ReactiveAuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.server.authorization.AuthorizationContext
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.util.PathMatcher
import reactor.core.publisher.Mono

/**
 * 鉴权管理器
 */
@Component
class AuthorizationManager(
  private val redisUtils: RedisUtils,
) : ReactiveAuthorizationManager<AuthorizationContext> {

  /**
   * 自定义鉴权逻辑
   *
   * @param mono                 要检查的身份验证
   * @param authorizationContext 要检查的对象
   * @return 校验结果
   */
  override fun check(mono: Mono<Authentication>, authorizationContext: AuthorizationContext): Mono<AuthorizationDecision> {
    val request = authorizationContext.exchange.request
    val restPath = "${request.methodValue}_${request.uri.path}"
    logger.info("请求路径：{}", restPath)
    // 对于跨域的预检请求直接放行
    if (request.method == HttpMethod.OPTIONS) {
      return Mono.just(AuthorizationDecision(true))
    }
    val resourceRolesMap = redisUtils.getMap<Set<String?>>(RESOURCE_ROLES_KEY)
    val authorities = getLongestMatch(restPath, resourceRolesMap)
    // 资源没有设置访问权限，直接放行
    if (authorities.isNullOrEmpty()) {
      return Mono.just(AuthorizationDecision(true))
    }
    return mono
      .filter(Authentication::isAuthenticated)
      .flatMapIterable(Authentication::getAuthorities)
      .map(GrantedAuthority::getAuthority)
      .any(authorities::contains)
      .map { AuthorizationDecision(it) }
      .defaultIfEmpty(AuthorizationDecision(false))
  }

  /**
   * 获取最长匹配的路径所需要的角色
   *
   * @param restPath         访问路径
   * @param resourceRolesMap 资源对应的角色
   * @return 所需的角色
   */
  private fun getLongestMatch(restPath: String, resourceRolesMap: Map<String, Set<String?>>): Set<String?>? {
    val pathMatcher: PathMatcher = AntPathMatcher()
    return resourceRolesMap.entries
      .filter { pathMatcher.match(it.key, restPath) }
      .maxByOrNull { it.key.length }?.value
  }
}

private val logger = KotlinLogging.logger {}