package com.zumin.sudoku.ums

/**
 * 用户传输层对象
 */
data class UserDTO(
  val id: Long? = null,
  val username: String? = null,
  val password: String? = null,
  val enabled: Int? = null,
  val roleIds: List<Long>? = null,
  val clientId: String? = null,
)