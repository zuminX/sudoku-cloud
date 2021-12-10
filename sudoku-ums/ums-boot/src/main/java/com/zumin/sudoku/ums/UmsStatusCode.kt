package com.zumin.sudoku.ums

import com.zumin.sudoku.common.core.code.StatusCode

/**
 * 用户系統响应状态编码
 */
enum class UmsStatusCode(private val number: Int, private val message: String) : StatusCode {
  USER_ERROR(10, "服务器在处理用户时发生了错误"),
  USER_NOT_FOUND(11, "未找到指定的用户"),
  USER_HAS_EQUAL_NAME(12, "存在相同用户名的用户"),
  USER_NOT_AUTHORITY(13, "没有权限访问该地址"),
  USER_REPEAT_PASSWORD_ERROR(14, "重复密码与密码不一致"),
  USER_NOT_ENABLED(15, "该用户处于禁用状态"),
  USER_NOT_MODIFY_AUTHORITY(16, "没有修改的权限"),
  USER_NOT_LOGIN(17, "用户未登陆");

  override fun getNumber() = number

  override fun getMessage() = message
}