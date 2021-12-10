package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.auth.CaptchaFeign
import com.zumin.sudoku.auth.OAuth2TokenDTO
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.ums.pojo.LoginBody
import com.zumin.sudoku.ums.pojo.RegisterUserBody
import com.zumin.sudoku.ums.pojo.UserVO
import com.zumin.sudoku.ums.service.SysUserService
import com.zumin.sudoku.ums.toUserVO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@ComRestController(path = ["/security"], tags = ["用户安全API接口"])
class SecurityController(
  private val sysUserService: SysUserService,
  private val captchaFeign: CaptchaFeign,
) {

  @PostMapping("/login")
  @ApiOperation("登录")
  @ApiImplicitParam(name = "loginBody", value = "登录用户表单信息", dataTypeClass = LoginBody::class, required = true)
  fun login(@RequestBody @Valid loginBody: LoginBody): CommonResult<OAuth2TokenDTO> {
    return sysUserService.login(loginBody)
  }

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserBody::class, required = true)
  fun registerUser(@RequestBody @Valid registerUser: RegisterUserBody): UserVO {
    captchaFeign.checkCaptcha(registerUser.uuid, registerUser.code)
    return sysUserService.registerUser(registerUser).toUserVO()
  }
}