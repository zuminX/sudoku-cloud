package com.zumin.sudoku.ums.pojo

import com.zumin.sudoku.ums.pojo.SysRole
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.time.LocalDateTime

@ApiModel("用户详细信息显示层类")
data class UserDetailVO(
  @ApiModelProperty("用户ID")
  val id: Long? = null,
  @ApiModelProperty("用户名")
  val username: String? = null,
  @ApiModelProperty("昵称")
  val nickname: String? = null,
  @ApiModelProperty("创建时间")
  val createTime: LocalDateTime? = null,
  @ApiModelProperty("最近登录时间")
  val recentLoginTime: LocalDateTime? = null,
  @ApiModelProperty("是否启用")
  val enabled: Int? = null,
  @ApiModelProperty("角色列表")
  val roleList: List<SysRole>? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = 3927503255291517116L
  }
}

@ApiModel("用户显示类")
data class UserVO(
  @ApiModelProperty("用户ID")
  val id: Long? = null,
  @ApiModelProperty("用户名")
  val username: String? = null,
  @ApiModelProperty("昵称")
  val nickname: String? = null,
  @ApiModelProperty("头像地址")
  val avatar: String? = null,
  @ApiModelProperty("角色列表")
  val roleList: List<SysRole>? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = -3781968958583675403L
  }
}