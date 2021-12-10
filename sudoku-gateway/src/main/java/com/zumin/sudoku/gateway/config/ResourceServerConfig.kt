package com.zumin.sudoku.gateway.config

import com.zumin.sudoku.common.core.auth.AUTHORITY_PREFIX
import com.zumin.sudoku.common.core.auth.JWT_AUTHORITIES_KEY
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.gateway.utils.writeFailed
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import reactor.core.publisher.Mono

/**
 * 资源服务器配置
 */
@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(WhiteListProperties::class)
class ResourceServerConfig(
  private val authorizationManager: AuthorizationManager,
  private val whiteListProperties: WhiteListProperties,
) {

  /**
   * 自定义处理WebSecurity
   *
   * @param http 安全对象
   * @return 调用链
   */
  @Bean
  fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
    http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint())
    http.authorizeExchange()
      .pathMatchers(*whiteListProperties.urls.toTypedArray()).permitAll()
      .anyExchange().access(authorizationManager)
      .and()
      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandler())
      .authenticationEntryPoint(authenticationEntryPoint())
      .and().csrf().disable()
    return http.build()
  }

  /**
   * 处理未授权
   *
   * @return 服务被拒绝的处理器
   */
  @Bean
  fun accessDeniedHandler(): ServerAccessDeniedHandler {
    return ServerAccessDeniedHandler { exchange, _ ->
      Mono.defer { Mono.just(exchange.response) }
        .flatMap { it.writeFailed(AuthStatusCode.ACCESS_UNAUTHORIZED) }
    }
  }

  /**
   * 处理token无效或者已过期自定义响应
   *
   * @return 服务器身份验证入口点
   */
  @Bean
  fun authenticationEntryPoint(): ServerAuthenticationEntryPoint {
    return ServerAuthenticationEntryPoint { exchange, _ ->
      Mono.defer { Mono.just(exchange.response) }
        .flatMap { it.writeFailed(AuthStatusCode.TOKEN_INVALID_OR_EXPIRED) }
    }
  }

  /**
   * 把jwt的Claim中的authorities加入ServerHttpSecurity中的Authentication
   *
   * @return 转换器
   */
  @Bean
  fun jwtAuthenticationConverter(): Converter<Jwt, out Mono<out AbstractAuthenticationToken?>?> {
    val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter().apply {
      setAuthorityPrefix(AUTHORITY_PREFIX)
      setAuthoritiesClaimName(JWT_AUTHORITIES_KEY)
    }
    val jwtAuthenticationConverter = JwtAuthenticationConverter().apply {
      setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)
    }
    return ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter)
  }
}