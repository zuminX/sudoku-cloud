package com.zumin.sudoku.ums.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@NoArg
@ApiModel("Oauth客户端详细信息")
@TableName("oauth_client_details")
data class OauthClientDetails(
  @ApiModelProperty("客户ID")
  @TableId(value = "client_id", type = IdType.AUTO)
  var clientId: String? = null,
  @ApiModelProperty("资源ID")
  @TableField("resource_ids")
  var resourceIds: String? = null,
  @ApiModelProperty("客户密钥")
  @TableField("client_secret")
  var clientSecret: String,
  @ApiModelProperty("授权范围")
  @TableField("scope")
  var scope: String,
  @ApiModelProperty("授权类型")
  @TableField("authorized_grant_types")
  var authorizedGrantTypes: String,
  @ApiModelProperty("服务器重定向地址")
  @TableField("web_server_redirect_uri")
  var webServerRedirectUri: String? = null,
  @ApiModelProperty("权限")
  @TableField("authorities")
  var authorities: String? = null,
  @ApiModelProperty("访问令牌有效性")
  @TableField("access_token_validity")
  var accessTokenValidity: Int,
  @ApiModelProperty("刷新令牌有效性")
  @TableField("refresh_token_validity")
  var refreshTokenValidity: Int,
  @ApiModelProperty("附加信息")
  @TableField("additional_information")
  var additionalInformation: String? = null,
  @ApiModelProperty("自动批准")
  @TableField("autoapprove")
  var autoapprove: String,
)

@NoArg
@ApiModel("系统资源")
@TableName("sys_resource")
data class SysResource(
  @ApiModelProperty("资源ID")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Long? = null,
  @ApiModelProperty("权限标识")
  @TableField("perms")
  var perms: String,
  @ApiModelProperty("请求方法类型")
  @TableField("method")
  var method: String,
  @ApiModelProperty("资源名称")
  @TableField("name")
  var name: String,
  @ApiModelProperty("拥有资源权限角色ID集合")
  @TableField(exist = false)
  var roleIds: Set<Long> = emptySet(),
)

@NoArg
@ApiModel("系统资源角色")
@TableName("sys_resource_role")
data class SysResourceRole(
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("资源角色ID")
  var id: Long? = null,
  @TableField("resource_id")
  @ApiModelProperty("资源ID")
  var resourceId: Long,
  @TableField("role_id")
  @ApiModelProperty("角色ID")
  var roleId: Long,
)

@NoArg
@ApiModel("系统角色")
@TableName("sys_role")
data class SysRole(
  @ApiModelProperty("角色ID")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Long? = null,
  @ApiModelProperty("角色名")
  @TableField("name")
  var name: String,
  @ApiModelProperty("角色昵称")
  @TableField("nickname")
  var nickname: String,
)

@NoArg
@ApiModel("系统用户")
@TableName("sys_user")
data class SysUser(
  @ApiModelProperty("用户ID")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Long? = null,
  @ApiModelProperty("用户名")
  @TableField("username")
  var username: String,
  @ApiModelProperty("密码")
  @TableField("password")
  var password: String,
  @ApiModelProperty("昵称")
  @TableField("nickname")
  var nickname: String,
  @ApiModelProperty("头像地址")
  @TableField("avatar")
  var avatar: String? = null,
  @ApiModelProperty("创建时间")
  @TableField("create_time")
  var createTime: LocalDateTime = LocalDateTime.now(),
  @ApiModelProperty("最近登录时间")
  @TableField("recent_login_time")
  var recentLoginTime: LocalDateTime = LocalDateTime.now(),
  @ApiModelProperty("是否启用")
  @TableField("enabled")
  var enabled: Int = 1,
  @ApiModelProperty("角色列表")
  @TableField(exist = false)
  var roleList: List<SysRole> = emptyList(),
)

@NoArg
@ApiModel("系统用户角色")
@TableName(value = "sys_user_role")
data class SysUserRole(
  @ApiModelProperty(value = "用户角色ID")
  @TableId(value = "id", type = IdType.AUTO)
  var id: Long? = null,
  @ApiModelProperty(value = "用户ID")
  @TableField(value = "user_id")
  var userId: Long,
  @ApiModelProperty(value = "角色ID")
  @TableField(value = "role_id")
  var roleId: Long,
)