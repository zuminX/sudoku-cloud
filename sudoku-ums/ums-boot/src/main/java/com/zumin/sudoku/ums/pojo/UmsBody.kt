package com.zumin.sudoku.ums.pojo

import com.zumin.sudoku.common.web.domain.LocalDateTimeRange
import com.zumin.sudoku.common.web.validator.IsLocalDateTimeRange
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.*

@ApiModel("新增用户信息体类")
data class AddUserBody(
  @ApiModelProperty("用户名")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  val username: String,
  @ApiModelProperty("昵称")
  @Length(min = 4, max = 32, message = "昵称的长度为4-32位")
  val nickname: String,
  @ApiModelProperty("密码")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  val password: String,
  @ApiModelProperty("创建时间")
  @NotNull(message = "创建时间不能为空") @Past(message = "创建时间必须是过去的时间")
  val createTime: LocalDateTime,
  @ApiModelProperty("最近登录时间")
  @NotNull(message = "最近登录时间不能为空") @Past(message = "最近登录时间必须是过去的时间")
  val recentLoginTime: LocalDateTime,
  @ApiModelProperty("是否启用")
  @NotNull(message = "是否启用不能为空")
  val enabled: Int,
  @ApiModelProperty("角色名列表")
  @NotEmpty(message = "用户至少要有一个角色")
  val roleNameList: MutableList<String>,
) : Serializable {
  companion object {
    private const val serialVersionUID: Long = 7708470459434247132L
  }
}

@ApiModel("登录用户信息体类")
data class LoginBody(
  @ApiModelProperty(value = "用户名", required = true)
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "用户名只能是数字和字母的组合")
  val username: String,
  @ApiModelProperty(value = "密码", required = true)
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "密码只能是数字和字母的组合")
  val password: String,
  @ApiModelProperty(value = "验证码", required = true)
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "验证码只能是数字和字母的组合")
  val code: String,
  @ApiModelProperty(value = "唯一标识", required = true)
  @NotEmpty(message = "唯一标识不能为空")
  val uuid: String,
) : Serializable {
  companion object {
    private const val serialVersionUID = 9125412007527553474L
  }
}

@ApiModel("修改用户信息体类")
data class ModifyUserBody(
  @ApiModelProperty("用户ID")
  @NotNull(message = "用户ID不能为空") @PositiveOrZero(message = "用户ID不能为负数")
  val id: Long,
  @ApiModelProperty("用户名")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  val username: String,
  @ApiModelProperty("昵称")
  @Length(min = 4, max = 32, message = "昵称的长度为4-32位")
  val nickname: String,
  @ApiModelProperty("创建时间")
  @NotNull(message = "创建时间不能为空") @Past(message = "创建时间必须是过去的时间")
  val createTime: LocalDateTime,
  @ApiModelProperty("最近登录时间")
  @NotNull(message = "最近登录时间不能为空") @Past(message = "最近登录时间必须是过去的时间")
  val recentLoginTime: LocalDateTime,
  @ApiModelProperty("是否启用")
  @NotNull(message = "是否启用不能为空")
  val enabled: Boolean,
  @ApiModelProperty("角色名列表")
  @NotEmpty(message = "用户至少要有一个角色")
  val roleNameList: MutableList<String>,
) : Serializable {
  companion object {
    private const val serialVersionUID = -2912099437520184948L
  }
}

@ApiModel("注册用户信息体类")
data class RegisterUserBody(
  @ApiModelProperty("用户名")
  @Length(min = 4, max = 16, message = "用户名的长度为4-16位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "用户名只能是数字和字母的组合")
  val username: String,
  @ApiModelProperty("密码")
  @Length(min = 6, max = 32, message = "密码的长度为6-32位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "密码只能是数字和字母的组合")
  val password: String,
  @ApiModelProperty("用户昵称")
   @Length(min = 4, max = 16, message = "昵称的长度为4-16位")
  val nickname: String,
  @ApiModelProperty("验证码")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  @Pattern(regexp = "^[0-9a-zA-Z]+$", message = "验证码只能是数字和字母的组合")
  val code: String,
  @ApiModelProperty("唯一标识")
  @NotEmpty(message = "唯一标识不能为空")
  val uuid: String,
) : Serializable {
  companion object {
    private const val serialVersionUID = 7615402214779318417L
  }
}

@ApiModel("搜索用户信息体类")
data class SearchUserBody(
  @ApiModelProperty("用户名")
  val username: String? = null,
  @ApiModelProperty("昵称")
  val nickname: String? = null,
  @ApiModelProperty("创建时间范围")
  @IsLocalDateTimeRange
  val createTimeRange: LocalDateTimeRange? = null,
  @ApiModelProperty("最近登录时间范围")
  @IsLocalDateTimeRange
  val recentLoginTimeRange: LocalDateTimeRange? = null,
  @ApiModelProperty("是否启用")
  val enabled: Boolean? = null,
  @ApiModelProperty("角色名列表")
  val roleNameList: List<String>? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = -231977380846579412L
  }
}