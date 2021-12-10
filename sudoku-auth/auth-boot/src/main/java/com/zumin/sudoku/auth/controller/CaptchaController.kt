package com.zumin.sudoku.auth.controller

import com.zumin.sudoku.auth.CaptchaVO
import com.zumin.sudoku.auth.service.CaptchaService
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.CommonResult.Companion.success
import com.zumin.sudoku.common.web.ComRestController
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ComRestController(path = ["/captcha"], tags = ["验证码API接口"])
class CaptchaController(private val captchaService: CaptchaService) {

  @GetMapping("/captchaImage")
  @ApiOperation("获取验证码")
  fun getCaptcha(): CommonResult<CaptchaVO> {
    return success(captchaService.generateCaptcha())
  }

  @GetMapping("/check")
  @ApiOperation("校验验证码")
  fun checkCaptcha(@RequestParam("uuid") uuid: String, @RequestParam("code") code: String): CommonResult<*> {
    captchaService.checkCaptcha(uuid, code)
    return success<Any>()
  }
}