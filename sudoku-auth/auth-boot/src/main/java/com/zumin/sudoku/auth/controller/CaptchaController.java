package com.zumin.sudoku.auth.controller;

import com.zumin.sudoku.auth.pojo.vo.CaptchaVO;
import com.zumin.sudoku.auth.service.CaptchaService;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/captcha", tags = "验证码API接口")
public class CaptchaController {

  private final CaptchaService captchaService;

  @GetMapping("/captchaImage")
  @ApiOperation("获取验证码")
  public CommonResult<CaptchaVO> getCaptcha() {
    return CommonResult.success(captchaService.generateCaptcha());
  }
}
