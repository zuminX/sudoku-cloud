package com.zumin.sudoku.auth.controller

import com.zumin.sudoku.auth.OAuth2TokenDTO
import com.zumin.sudoku.auth.service.CaptchaService
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.CommonResult.Companion.success
import com.zumin.sudoku.common.core.auth.AUTH_CAPTCHA_CODE
import com.zumin.sudoku.common.core.auth.AUTH_CAPTCHA_UUID
import com.zumin.sudoku.common.core.auth.WEAPP_CLIENT_ID
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.getAuthClientId
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import springfox.documentation.annotations.ApiIgnore
import java.security.Principal

@ComRestController(path = ["/oauth"], tags = ["认证中心"])
class OAuthController(
  private val tokenEndpoint: TokenEndpoint,
  private val captchaService: CaptchaService,
) {

  @ApiOperation(value = "OAuth2认证", notes = "login")
  @ApiImplicitParams(ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
    ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
    ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
    ApiImplicitParam(name = "refresh_token", value = "刷新token"),
    ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
    ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"))
  @PostMapping("/token")
  fun postAccessToken(
    @ApiIgnore principal: Principal,
    @ApiIgnore @RequestParam parameters: Map<String, String>,
  ): CommonResult<OAuth2TokenDTO> {
    val accessToken = when (getAuthClientId()) {
      WEAPP_CLIENT_ID -> throw UnsupportedOperationException("暂不支持微信")
      else -> handlerForSystem(principal, parameters)
    }
    return success(OAuth2TokenDTO(
      token = accessToken.value,
      refreshToken = accessToken.refreshToken.value,
      expiresIn = accessToken.expiresIn))
  }

  /**
   * 处理系统用户
   *
   * @return Token信息
   */
  private fun handlerForSystem(principal: Principal, parameters: Map<String, String>): OAuth2AccessToken {
    val uuid = parameters[AUTH_CAPTCHA_UUID]
    val code = parameters[AUTH_CAPTCHA_CODE]
    captchaService.checkCaptcha(uuid!!, code)
    return tokenEndpoint.postAccessToken(principal, parameters).body!!
  }
}