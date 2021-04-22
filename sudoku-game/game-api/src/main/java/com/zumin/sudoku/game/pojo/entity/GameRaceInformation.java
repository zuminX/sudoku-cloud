package com.zumin.sudoku.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value = "com-zumin-sudoku-game-pojo-entity-GameRaceInformation")
@Data
@TableName(value = "game_race_information")
public class GameRaceInformation {

  /**
   * 竞赛信息的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "竞赛信息的ID")
  private Long id;

  /**
   * 竞赛的标题
   */
  @TableField(value = "title")
  @ApiModelProperty(value = "竞赛的标题")
  private String title;

  /**
   * 竞赛的描述
   */
  @TableField(value = "description")
  @ApiModelProperty(value = "竞赛的描述")
  private String description;

  /**
   * 数独矩阵
   */
  @TableField(value = "matrix")
  @ApiModelProperty(value = "数独矩阵")
  private String matrix;

  /**
   * 空缺的数独
   */
  @TableField(value = "holes")
  @ApiModelProperty(value = "空缺的数独")
  private String holes;

  /**
   * 开始时间
   */
  @TableField(value = "start_time")
  @ApiModelProperty(value = "开始时间")
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  @TableField(value = "end_time")
  @ApiModelProperty(value = "结束时间")
  private LocalDateTime endTime;

  /**
   * 创建用户的ID
   */
  @TableField(value = "user_id")
  @ApiModelProperty(value = "创建用户的ID")
  private Long userId;
}