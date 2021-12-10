package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.ums.UserDTO
import com.zumin.sudoku.ums.pojo.*
import com.zumin.sudoku.ums.service.SysUserService
import com.zumin.sudoku.ums.toUserDTO
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@ComRestController(path = ["/user"], tags = ["用户API接口"])
class UserController(
  private val userService: SysUserService,
) {

  @GetMapping("/username/{username}")
  @ApiOperation(value = "根据用户名获取用户信息")
  @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
  fun getUserByUsername(@PathVariable username: String): UserDTO {
    val user = userService.getUserWithRoleByUsername(username) ?: throw BaseException(AuthStatusCode.USER_NOT_EXIST)
    return user.toUserDTO()
  }

  @ApiOperation("获取用户列表")
  @GetMapping("/list")
  fun getUserList(): Page<UserDetailVO> {
    return userService.getUserList()
  }

  @PostMapping("/modify")
  @ApiOperation("修改用户信息")
  @ApiImplicitParam(name = "modifyUserBody", value = "修改的用户信息", dataTypeClass = ModifyUserBody::class, required = true)
  fun modifyUser(@RequestBody @Valid modifyUserBody: ModifyUserBody) {
    userService.modifyUser(modifyUserBody)
  }

  @PostMapping("/add")
  @ApiOperation("新增用户")
  @ApiImplicitParam(name = "addUserBody", value = "新增用户的信息", dataTypeClass = AddUserBody::class, required = true)
  fun addUser(@RequestBody @Valid addUserBody: AddUserBody) {
    userService.addUser(addUserBody)
  }

  @PostMapping("/search")
  @ApiOperation("根据条件搜索用户")
  @ApiImplicitParam(name = "searchUserBody", value = "搜索用户的条件", dataTypeClass = SearchUserBody::class, required = true)
  fun searchUser(@RequestBody @Valid searchUserBody: SearchUserBody): Page<UserDetailVO> {
    return userService.searchUser(searchUserBody)
  }

  @GetMapping("/searchByName")
  @ApiOperation("根据名称搜索用户")
  @ApiImplicitParam(name = "username", value = "名称", dataTypeClass = String::class, required = true)
  fun searchUserByName(@RequestParam("name") name: String): Page<UserDetailVO> {
    return userService.searchUserByName(name)
  }
}