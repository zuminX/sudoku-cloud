package com.zumin.sudoku.ums.pojo

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@NoArg
@ApiModel("用户详细信息显示层类")
data class UserDetailVO(
  @ApiModelProperty("用户ID")
  val id: Long,
  @ApiModelProperty("用户名")
  val username: String,
  @ApiModelProperty("昵称")
  val nickname: String,
  @ApiModelProperty("创建时间")
  val createTime: LocalDateTime,
  @ApiModelProperty("最近登录时间")
  val recentLoginTime: LocalDateTime,
  @ApiModelProperty("是否启用")
  val enabled: Int,
  @ApiModelProperty("角色列表")
  val roleList: List<SysRole>,
)

@NoArg
@ApiModel("用户显示类")
data class UserVO(
  @ApiModelProperty("用户ID")
  val id: Long,
  @ApiModelProperty("用户名")
  val username: String,
  @ApiModelProperty("昵称")
  val nickname: String,
  @ApiModelProperty("头像地址")
  val avatar: String? = null,
  @ApiModelProperty("角色列表")
  val roleList: List<SysRole>,
)
