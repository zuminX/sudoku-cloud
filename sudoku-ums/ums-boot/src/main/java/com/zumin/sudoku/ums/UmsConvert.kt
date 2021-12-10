package com.zumin.sudoku.ums

import com.zumin.sudoku.common.web.encodePassword
import com.zumin.sudoku.ums.pojo.*

/**
 * 将用户表对应的对象转换为显示层对象
 *
 * @return 用户显示层对象
 */
fun SysUser.toUserVO(): UserVO {
  return UserVO(id!!, username, nickname, avatar, roleList)
}

/**
 * 从实体转换为传输层
 *
 * @return 传输层对象
 */
fun SysUser.toUserDTO(): UserDTO {
  return UserDTO(id!!, username, password, enabled, roleList.map { it.id!! })
}

/**
 * 将用户表对应的对象转换为用户详情显示层对象
 *
 * @return 用户详情显示层对象
 */
fun SysUser.toUserDetailVO(): UserDetailVO {
  return UserDetailVO(id!!, username, nickname, createTime, recentLoginTime, enabled, roleList)
}

/**
 * 将新增用户对象转化为用户表对应的对象
 *
 * @return 用户表对应的对象
 */
fun AddUserBody.toSysUser(): SysUser {
  return SysUser(
    username = username,
    password = password.encodePassword(),
    nickname = nickname,
    createTime = createTime,
    recentLoginTime = recentLoginTime,
    enabled = enabled
  )
}

/**
 * 将注册用户信息对象转换为用户表对应的对象 对密码进行加密
 *
 * @return 用户表对应的对象
 */
fun RegisterUserBody.toSysUser(): SysUser {
  return SysUser(
    username = username.trim(),
    password = password.encodePassword(),
    nickname = nickname
  )
}