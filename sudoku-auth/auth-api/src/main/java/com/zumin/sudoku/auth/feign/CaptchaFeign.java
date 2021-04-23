package com.zumin.sudoku.auth.feign;

import com.zumin.sudoku.common.core.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sudoku-auth", contextId = "captcha")
public interface CaptchaFeign {

  @GetMapping("/captcha/check")
  CommonResult<?> checkCaptcha(@RequestParam("uuid") String uuid, @RequestParam("code") String code);
}
