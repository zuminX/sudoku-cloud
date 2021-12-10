package com.zumin.sudoku.auth.config

import com.zumin.sudoku.auth.domain.User
import com.zumin.sudoku.auth.service.JdbcClientDetailsServiceImpl
import com.zumin.sudoku.auth.service.UserDetailsServiceImpl
import com.zumin.sudoku.common.core.CommonResult.Companion.error
import com.zumin.sudoku.common.core.auth.*
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.core.utils.returnJsonData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import org.springframework.security.web.AuthenticationEntryPoint
import java.security.KeyPair
import javax.sql.DataSource

/**
 * 授权服务配置
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
  private val dataSource: DataSource,
  private val authenticationManager: AuthenticationManager,
  private val userDetailsService: UserDetailsServiceImpl,
) : AuthorizationServerConfigurerAdapter() {

  /**
   * 配置客户端详情
   */
  override fun configure(clients: ClientDetailsServiceConfigurer) {
    val jdbcClientDetailsService = JdbcClientDetailsServiceImpl(dataSource).apply {
      setFindClientDetailsSql(FIND_CLIENT_DETAILS_SQL)
      setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL)
    }
    clients.withClientDetails(jdbcClientDetailsService)
  }

  /**
   * 配置授权以及令牌的访问端点和令牌服务
   */
  override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
    val tokenEnhancers = mutableListOf<TokenEnhancer>(tokenEnhancer(), jwtAccessTokenConverter())
    val tokenEnhancerChain = TokenEnhancerChain().apply { setTokenEnhancers(tokenEnhancers) }
    endpoints.authenticationManager(authenticationManager)
      .accessTokenConverter(jwtAccessTokenConverter())
      .tokenEnhancer(tokenEnhancerChain)
      .userDetailsService(userDetailsService)
      .reuseRefreshTokens(true)
  }

  /**
   * 配置安全配置器
   *
   * @param security 授权服务器安全配置器
   */
  override fun configure(security: AuthorizationServerSecurityConfigurer) {
    val endpointFilter = CustomClientCredentialsTokenEndpointFilter(security).apply {
      afterPropertiesSet()
      setAuthenticationEntryPoint(authenticationEntryPoint())
    }
    security.addTokenEndpointAuthenticationFilter(endpointFilter)
    security.authenticationEntryPoint(authenticationEntryPoint())
      .tokenKeyAccess("isAuthenticated()")
      .checkTokenAccess("permitAll()")
  }

  /**
   * 自定义认证异常响应数据
   *
   * @return 认证入口点
   */
  @Bean
  fun authenticationEntryPoint(): AuthenticationEntryPoint {
    return AuthenticationEntryPoint { _, response, e ->
      e.printStackTrace()
      response.returnJsonData(error<Any>(AuthStatusCode.CLIENT_AUTHENTICATION_FAILED))
    }
  }

  /**
   * 使用非对称加密算法对token签名
   */
  @Bean
  fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
    return JwtAccessTokenConverter().apply { setKeyPair(keyPair()) }
  }

  /**
   * 从classpath下的密钥库中获取密钥对(公钥+私钥)
   */
  @Bean
  fun keyPair(): KeyPair {
    val factory = KeyStoreKeyFactory(ClassPathResource("sudoku.jks"), "2233qqaazz".toCharArray())
    return factory.getKeyPair("sudoku", "2233qqaazz".toCharArray())
  }

  /**
   * JWT内容增强
   */
  @Bean
  fun tokenEnhancer(): TokenEnhancer {
    return TokenEnhancer { accessToken, authentication ->
      val user = authentication.userAuthentication.principal as User
      (accessToken as DefaultOAuth2AccessToken).additionalInformation = mutableMapOf(
        AUTH_USER_ID to user.id as Any,
        AUTH_CLIENT_ID to user.clientId as Any,
        AUTH_USERNAME to user.username as Any
      )
      accessToken
    }
  }
}