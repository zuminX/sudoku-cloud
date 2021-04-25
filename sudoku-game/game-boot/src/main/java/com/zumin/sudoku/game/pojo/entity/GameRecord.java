package com.zumin.sudoku.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@ApiModel(value = "com-zumin-sudoku-game-pojo-entity-GameRecord")
@Data
@TableName(value = "game_record")
public class GameRecord {

  /**
   * 数独记录的ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "数独记录的ID")
  private Long id;

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
   * 数独难度ID
   */
  @TableField(value = "level_id")
  @ApiModelProperty(value = "数独难度ID")
  private Long levelId;
}