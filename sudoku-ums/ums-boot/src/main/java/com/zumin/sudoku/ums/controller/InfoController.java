package com.zumin.sudoku.ums.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.ums.convert.UserConvert;
import com.zumin.sudoku.ums.service.SysUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ComRestController(path = "/info", tags = "用户信息API接口")
public class InfoController {

  private final SysUserService sysUserService;
  private final UserConvert userConvert;

}
