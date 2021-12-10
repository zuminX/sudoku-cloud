package com.zumin.sudoku.auth

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("验证码显示类")
class CaptchaVO(
  @ApiModelProperty("唯一标识")
  val uuid: String,
  @ApiModelProperty("验证码图片Base64编码")
  val captchaBase64: String,
) : Serializable {

  companion object {
    private const val serialVersionUID = 8718519598958140442L
  }
}