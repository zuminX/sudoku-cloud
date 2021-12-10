package com.zumin.sudoku.common.core

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("common.core")
data class CommonCoreProperties(
  // 项目包名
  val projectPackage: String,
)
