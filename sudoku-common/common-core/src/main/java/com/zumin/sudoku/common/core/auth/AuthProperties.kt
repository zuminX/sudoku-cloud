package com.zumin.sudoku.common.core.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("auth")
data class AuthProperties(
  val clientSecret: String,
)
