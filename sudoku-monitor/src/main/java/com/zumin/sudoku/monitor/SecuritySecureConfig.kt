package com.zumin.sudoku.monitor

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
class SecuritySecureConfig(adminServerProperties: AdminServerProperties) : WebSecurityConfigurerAdapter() {
  private val adminContextPath: String

  init {
    adminContextPath = adminServerProperties.contextPath
  }

  override fun configure(http: HttpSecurity) {
    val successHandler = SavedRequestAwareAuthenticationSuccessHandler().apply {
      setTargetUrlParameter("redirectTo")
      setDefaultTargetUrl("$adminContextPath/")
    }
    http.authorizeRequests()
      .antMatchers("$adminContextPath/assets/**").permitAll()
      .antMatchers("$adminContextPath/login").permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin().loginPage("$adminContextPath/login").successHandler(successHandler).and()
      .logout().logoutUrl("$adminContextPath/logout").and()
      .httpBasic().and()
      .csrf()
      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .ignoringAntMatchers(
        "$adminContextPath/instances",
        "$adminContextPath/actuator/**"
      )
  }
}