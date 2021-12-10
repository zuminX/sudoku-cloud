package com.zumin.sudoku.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("captcha")
data class CaptchaProperties(
  // 验证码的宽度
  val width: Int = 112,
  // 验证码的高度
  val height: Int = 36,
  // 验证码字符的个数
  val codeNumber: Int = 4,
  // 验证码干扰线的个数
  val lineCount: Int = 100,
  // 验证码有效期(分钟)
  val expireTime: Int = 5,
)
