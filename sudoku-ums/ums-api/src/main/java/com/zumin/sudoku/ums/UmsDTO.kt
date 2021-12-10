package com.zumin.sudoku.ums

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel

@NoArg
@ApiModel("用户传输层对象")
data class UserDTO(
  val id: Long,
  val username: String,
  val password: String,
  val enabled: Int,
  val roleIds: List<Long>,
  val clientId: String? = null,
)