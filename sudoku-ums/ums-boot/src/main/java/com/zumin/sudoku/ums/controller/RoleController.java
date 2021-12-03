package com.zumin.sudoku.ums.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.ums.pojo.entity.SysRole;
import com.zumin.sudoku.ums.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@ComRestController(path = "/role", tags = "系统角色API接口")
public class RoleController {

  private final SysRoleService roleService;

  @GetMapping("/roleList")
  @ApiOperation("获取系统角色列表")
  public List<SysRole> getRoleList() {
    return roleService.list();
  }
}
