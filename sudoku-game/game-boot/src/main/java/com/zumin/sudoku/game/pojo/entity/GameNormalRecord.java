package com.zumin.sudoku.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "game_normal_record")
public class GameNormalRecord {

  /**
   * 游戏记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "游戏记录的ID")
  private Long id;

  /**
   * 输入的数独矩阵
   */
  @TableField(value = "answer")
  @ApiModelProperty(value = "输入的数独矩阵")
  private String answer;

  /**
   * 回答情况
   */
  @TableField(value = "situation")
  @ApiModelProperty(value = "回答情况")
  private Integer situation;

  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  @ApiModelProperty(value = "用户ID")
  private Long userId;

  /**
   * 数独记录ID
   */
  @TableField(value = "record_id")
  @ApiModelProperty(value = "数独记录ID")
  private Long recordId;
}