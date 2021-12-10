package com.zumin.sudoku.ums.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("profile")
data class ProfileProperties(
  val avatar: AvatarProperties,
) {

  data class AvatarProperties(
    val dir: String,
    val maxSize: Long,
  )
}