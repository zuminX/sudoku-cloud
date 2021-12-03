package com.zumin.sudoku.ums.controller;


import com.zumin.sudoku.common.core.auth.AuthStatusCode;
import com.zumin.sudoku.common.core.exception.BaseException;
import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.ums.convert.UserConvert;
import com.zumin.sudoku.ums.dto.UserDTO;
import com.zumin.sudoku.ums.pojo.body.AddUserBody;
import com.zumin.sudoku.ums.pojo.body.ModifyUserBody;
import com.zumin.sudoku.ums.pojo.body.SearchUserBody;
import com.zumin.sudoku.ums.pojo.entity.SysUser;
import com.zumin.sudoku.ums.pojo.vo.UserDetailVO;
import com.zumin.sudoku.ums.service.SysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/user", tags = "用户API接口")
public class UserController {

  private final SysUserService userService;
  private final UserConvert userConvert;

  @GetMapping("/username/{username}")
  @ApiOperation(value = "根据用户名获取用户信息")
  @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
  public UserDTO getUserByUsername(@PathVariable String username) {
    SysUser user = userService.getUserWithRoleByUsername(username);
    if (user == null) {
      throw new BaseException(AuthStatusCode.USER_NOT_EXIST);
    }
    return userConvert.entityToDTO(user);
  }

  @GetMapping("/list")
  @ApiOperation("获取用户列表")
  public Page<UserDetailVO> getUserList() {
    return userService.getUserList();
  }

  @PostMapping("/modify")
  @ApiOperation("修改用户信息")
  @ApiImplicitParam(name = "modifyUserBody", value = "修改的用户信息", dataTypeClass = ModifyUserBody.class, required = true)
  public void modifyUser(@RequestBody @Valid ModifyUserBody modifyUserBody) {
    userService.modifyUser(modifyUserBody);
  }

  @PostMapping("/add")
  @ApiOperation("新增用户")
  @ApiImplicitParam(name = "addUserBody", value = "新增用户的信息", dataTypeClass = AddUserBody.class, required = true)
  public void addUser(@RequestBody @Valid AddUserBody addUserBody) {
    userService.addUser(addUserBody);
  }

  @PostMapping("/search")
  @ApiOperation("根据条件搜索用户")
  @ApiImplicitParam(name = "searchUserBody", value = "搜索用户的条件", dataTypeClass = SearchUserBody.class, required = true)
  public Page<UserDetailVO> searchUser(@RequestBody @Valid SearchUserBody searchUserBody) {
    return userService.searchUser(searchUserBody);
  }

  @GetMapping("/searchByName")
  @ApiOperation("根据名称搜索用户")
  @ApiImplicitParam(name = "username", value = "名称", dataTypeClass = String.class, required = true)
  public Page<UserDetailVO> searchUserByName(@RequestParam("name") String name) {
    return userService.searchUserByName(name);
  }
}
