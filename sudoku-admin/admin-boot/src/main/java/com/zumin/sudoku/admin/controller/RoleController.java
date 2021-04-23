package com.zumin.sudoku.admin.controller;

import com.zumin.sudoku.admin.pojo.entity.SysRole;
import com.zumin.sudoku.admin.service.SysRoleService;
import com.zumin.sudoku.common.web.annotation.ComRestController;
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
