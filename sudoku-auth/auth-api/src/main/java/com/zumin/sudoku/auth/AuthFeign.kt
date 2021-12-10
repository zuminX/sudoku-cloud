package com.zumin.sudoku.auth

import com.zumin.sudoku.common.core.CommonResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "sudoku-auth", contextId = "captcha")
interface CaptchaFeign {
  @GetMapping("/captcha/check")
  fun checkCaptcha(@RequestParam("uuid") uuid: String, @RequestParam("code") code: String): CommonResult<*>
}

@FeignClient(name = "sudoku-auth", contextId = "oauth")
interface OAuthFeign {
  @PostMapping("/oauth/token")
  fun postAccessToken(@RequestParam parameters: Map<String, String>): CommonResult<OAuth2TokenDTO>
}