package com.zumin.sudoku.gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * 白名单配置
 */
@ConstructorBinding
@ConfigurationProperties("whitelist")
data class WhiteListProperties(
  val urls: List<String> = emptyList(),
)