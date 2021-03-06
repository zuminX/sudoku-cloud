package com.zumin.sudoku.auth

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@NoArg
@ApiModel("Oauth2获取Token返回信息封装")
data class OAuth2TokenDTO(
  @ApiModelProperty("访问令牌")
  val token: String,
  @ApiModelProperty("刷令牌")
  val refreshToken: String,
  @ApiModelProperty("有效时间（秒）")
  val expiresIn : Int,
)