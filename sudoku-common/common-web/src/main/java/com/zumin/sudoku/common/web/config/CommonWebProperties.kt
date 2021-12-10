package com.zumin.sudoku.common.web.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("common.web")
data class CommonWebProperties(
  // 是否开启全局数据绑定
  val dataBinding: Boolean = true,
  // 是否开启全局异常处理
  val exceptionHandler: Boolean = true,
  // 是否开启全局响应处理
  val response: Boolean = true,
  // Swagger配置信息
  val api: ApiProperties,
) {

  data class ApiProperties(
    val title: String = "",
    val description: String = "",
  )
}