package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.utils.getCurrentUserId
import com.zumin.sudoku.ums.UmsStatusCode
import com.zumin.sudoku.ums.exception.UserException
import com.zumin.sudoku.ums.pojo.UserVO
import com.zumin.sudoku.ums.service.SysUserService
import com.zumin.sudoku.ums.toUserVO
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping

@ComRestController(path = ["/info"], tags = ["用户信息API接口"])
class InfoController(
  private val userService: SysUserService,
) {

  @GetMapping("/basic")
  @ApiOperation("获取当前用户的基本信息")
  fun basicInfo(): UserVO {
    val userId: Long = getCurrentUserId() ?: throw UserException(UmsStatusCode.USER_NOT_LOGIN)
    return userService.getUserWithRoleById(userId).toUserVO()
  }
}