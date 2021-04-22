package com.zumin.sudoku.user.controller;

import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.user.pojo.body.LoginBody;
import com.zumin.sudoku.user.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@ComRestController(path = "/security", tags = "用户安全API接口")
public class SecurityController {

  private final SysUserService sysUserService;

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "登录用户表单信息", dataTypeClass = LoginBody.class, required = true)
  public CommonResult<OAuth2TokenDTO> login(@RequestBody @Valid LoginBody loginBody) {
    return sysUserService.login(loginBody);
  }
}
