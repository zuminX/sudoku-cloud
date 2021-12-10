package com.zumin.sudoku.auth

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@NoArg
@ApiModel("验证码显示类")
data class CaptchaVO(
  @ApiModelProperty("唯一标识")
  val uuid: String,
  @ApiModelProperty("验证码图片Base64编码")
  val captchaBase64: String,
)