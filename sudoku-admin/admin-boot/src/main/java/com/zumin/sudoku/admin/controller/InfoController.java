package com.zumin.sudoku.admin.controller;

import com.zumin.sudoku.admin.convert.UserConvert;
import com.zumin.sudoku.admin.pojo.dto.UserDTO;
import com.zumin.sudoku.admin.pojo.entity.SysUser;
import com.zumin.sudoku.admin.service.SysUserService;
import com.zumin.sudoku.common.core.enums.AuthStatusCode;
import com.zumin.sudoku.common.core.exception.BaseException;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@ComRestController(path = "/info", tags = "用户信息API接口")
public class InfoController {

  private final SysUserService sysUserService;
  private final UserConvert userConvert;

  @GetMapping("/username/{username}")
  @ApiOperation(value = "根据用户名获取用户信息")
  @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
  public UserDTO getUserByUsername(@PathVariable String username) {
    SysUser user = sysUserService.getUserWithRoleByUsername(username);
    if (user == null) {
      throw new BaseException(AuthStatusCode.USER_NOT_EXIST);
    }
    return userConvert.entityToDTO(user);
  }
}
