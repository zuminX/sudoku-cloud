package com.zumin.sudoku.game.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "com-zumin-sudoku-game-pojo-entity-GameLevel")
@Data
@TableName(value = "game_level")
public class GameLevel {

  /**
   * 数独难度ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "数独难度ID")
  private Long id;

  /**
   * 难度名
   */
  @TableField(value = "`name`")
  @ApiModelProperty(value = "难度名")
  private String name;

  /**
   * 最小的空缺格子数
   */
  @TableField(value = "min_empty")
  @ApiModelProperty(value = "最小的空缺格子数")
  private Integer minEmpty;

  /**
   * 最大的空缺格子数
   */
  @TableField(value = "max_empty")
  @ApiModelProperty(value = "最大的空缺格子数")
  private Integer maxEmpty;

  /**
   * 难度排序
   */
  @TableField(value = "sort")
  @ApiModelProperty(value = "难度排序")
  private Integer sort;
}