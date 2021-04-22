package com.zumin.sudoku.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value = "com-zumin-sudoku-game-pojo-entity-GameRaceRecord")
@Data
@TableName(value = "game_race_record")
public class GameRaceRecord {

  /**
   * 竞赛记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "竞赛记录的ID")
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
   * 用户ID
   */
  @TableField(value = "user_id")
  @ApiModelProperty(value = "用户ID")
  private Long userId;

  /**
   * 竞赛信息的ID
   */
  @TableField(value = "race_information_id")
  @ApiModelProperty(value = "竞赛信息的ID")
  private Long raceInformationId;
}