package com.zumin.sudoku.ums.controller;

import com.zumin.sudoku.auth.feign.AuthFeign;
import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.ums.convert.UserConvert;
import com.zumin.sudoku.ums.enums.UmsStatusCode;
import com.zumin.sudoku.ums.exception.UserException;
import com.zumin.sudoku.ums.pojo.body.LoginBody;
import com.zumin.sudoku.ums.pojo.body.RegisterUserBody;
import com.zumin.sudoku.ums.pojo.entity.SysUser;
import com.zumin.sudoku.ums.pojo.vo.UserVO;
import com.zumin.sudoku.ums.service.SysUserService;
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

  private final AuthFeign authFeign;

  private final UserConvert userConvert;

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "登录用户表单信息", dataTypeClass = LoginBody.class, required = true)
  public CommonResult<OAuth2TokenDTO> login(@RequestBody @Valid LoginBody loginBody) {
    return sysUserService.login(loginBody);
  }

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserBody.class, required = true)
  public UserVO registerUser(@RequestBody @Valid RegisterUserBody registerUser) {
    authFeign.checkCaptcha(registerUser.getUuid(), registerUser.getCode());
    SysUser sysUser = sysUserService.registerUser(registerUser);
    return userConvert.convert(sysUser);
  }
}
