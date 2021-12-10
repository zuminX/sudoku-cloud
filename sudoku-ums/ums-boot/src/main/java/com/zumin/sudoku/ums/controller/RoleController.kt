package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.ums.pojo.SysRole
import com.zumin.sudoku.ums.service.SysRoleService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping

@ComRestController(path = ["/role"], tags = ["系统角色API接口"])
class RoleController(
  private val roleService: SysRoleService,
) {

  @ApiOperation("获取系统角色列表")
  @GetMapping("/roleList")
  fun getRoleList(): List<SysRole> = roleService.list()
}