package com.zumin.sudoku.ums.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.ums.convert.UserConvert;
import com.zumin.sudoku.ums.enums.UmsStatusCode;
import com.zumin.sudoku.ums.exception.UserException;
import com.zumin.sudoku.ums.pojo.body.LoginBody;
import com.zumin.sudoku.ums.pojo.vo.UserVO;
import com.zumin.sudoku.ums.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@ComRestController(path = "/info", tags = "用户信息API接口")
public class InfoController {

  private final SysUserService userService;
  private final UserConvert userConvert;

  @GetMapping("/basic")
  @ApiOperation("获取当前用户的基本信息")
  public UserVO basicInfo() {
    Long userId = SecurityUtils.getUserId();
    if (userId == null) {
      throw new UserException(UmsStatusCode.USER_NOT_LOGIN);
    }
    return userConvert.convert(userService.getUserWithRoleById(userId));
  }
}
