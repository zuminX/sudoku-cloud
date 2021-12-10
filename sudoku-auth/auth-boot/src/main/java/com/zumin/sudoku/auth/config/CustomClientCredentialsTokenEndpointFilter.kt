package com.zumin.sudoku.auth.config

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter
import org.springframework.security.web.AuthenticationEntryPoint

/**
 * 重写filter实现客户端自定义异常处理
 */
class CustomClientCredentialsTokenEndpointFilter(
  private val configurer: AuthorizationServerSecurityConfigurer,
) : ClientCredentialsTokenEndpointFilter() {

  private var authenticationEntryPoint: AuthenticationEntryPoint? = null

  /**
   * 自定义身份认证端点
   *
   * @param authenticationEntryPoint 身份认证端点
   */
  override fun setAuthenticationEntryPoint(authenticationEntryPoint: AuthenticationEntryPoint) {
    super.setAuthenticationEntryPoint(null)
    this.authenticationEntryPoint = authenticationEntryPoint
  }

  /**
   * 获取身份验证管理器
   *
   * @return 身份验证管理器
   */
  override fun getAuthenticationManager(): AuthenticationManager {
    return configurer.and().getSharedObject(AuthenticationManager::class.java)
  }

  /**
   * 配置后处理器
   */
  override fun afterPropertiesSet() {
    setAuthenticationFailureHandler { request, response, exception ->
      authenticationEntryPoint!!.commence(request, response, exception)
    }
    setAuthenticationSuccessHandler { _, _, _ ->  }
  }
}