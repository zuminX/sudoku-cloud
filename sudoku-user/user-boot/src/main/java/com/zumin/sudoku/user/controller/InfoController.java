package com.zumin.sudoku.user.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.user.convert.UserConvert;
import com.zumin.sudoku.user.service.SysUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ComRestController(path = "/info", tags = "用户信息API接口")
public class InfoController {

  private final SysUserService sysUserService;
  private final UserConvert userConvert;

}
