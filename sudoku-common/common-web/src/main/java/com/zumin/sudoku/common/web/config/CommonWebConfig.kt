package com.zumin.sudoku.common.web.config

import cn.hutool.extra.spring.SpringUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@Import(SpringUtil::class)
class CommonWebConfig {
  /**
   * 设置密码编码器
   *
   * @return 密码编码器
   */
  @Bean
  @Lazy
  fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}