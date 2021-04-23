package com.zumin.sudoku.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_user_role")
public class SysUserRole {

  /**
   * 用户角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "用户角色ID")
  private Long id;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  @ApiModelProperty(value = "用户ID")
  private Long userId;

  /**
   * 角色ID
   */
  @TableField(value = "role_id")
  @ApiModelProperty(value = "角色ID")
  private Long roleId;

  public SysUserRole(Long userId, Long roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }
}