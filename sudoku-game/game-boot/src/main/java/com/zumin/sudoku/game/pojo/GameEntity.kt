package com.zumin.sudoku.game.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("游戏等级")
@TableName("game_level")
class GameLevel {
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("数独难度ID")
  var id: Long? = null

  @TableField("name")
  @ApiModelProperty("难度名")
  var name: String? = null

  @TableField("min_empty")
  @ApiModelProperty("最小的空缺格子数")
  var minEmpty: Int? = null

  @TableField("max_empty")
  @ApiModelProperty("最大的空缺格子数")
  var maxEmpty: Int? = null

  @TableField("sort")
  @ApiModelProperty("难度排序")
  var sort: Int? = null
}

@ApiModel("普通游戏记录")
@TableName(value = "game_normal_record")
class GameNormalRecord {
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "游戏记录的ID")
  var id: Long? = null

  @TableField(value = "answer")
  @ApiModelProperty(value = "输入的数独矩阵")
  var answer: String? = null

  @TableField(value = "situation")
  @ApiModelProperty(value = "回答情况")
  var situation: Int? = null

  @TableField(value = "user_id")
  @ApiModelProperty(value = "用户ID")
  var userId: Long? = null

  @TableField(value = "record_id")
  @ApiModelProperty(value = "数独记录ID")
  var recordId: Long? = null
}

@ApiModel("竞赛游戏信息")
@TableName("game_race_information")
class GameRaceInformation {
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("竞赛信息的ID")
  var id: Long? = null

  @TableField("title")
  @ApiModelProperty("竞赛的标题")
  var title: String? = null

  @TableField("description")
  @ApiModelProperty("竞赛的描述")
  var description: String? = null

  @TableField("matrix")
  @ApiModelProperty("数独矩阵")
  var matrix: String? = null

  @TableField("holes")
  @ApiModelProperty("空缺的数独")
  var holes: String? = null

  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime? = null

  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime? = null

  @TableField("user_id")
  @ApiModelProperty("创建用户的ID")
  var userId: Long? = null
}

@ApiModel("竞赛记录")
@TableName("game_race_record")
class GameRaceRecord {
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("竞赛记录的ID")
  var id: Long? = null

  @TableField("answer")
  @ApiModelProperty("输入的数独矩阵")
  var answer: String? = null

  @TableField("situation")
  @ApiModelProperty("回答情况")
  var situation: Int? = null

  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime? = null

  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime? = null

  @TableField("user_id")
  @ApiModelProperty("用户ID")
  var userId: Long? = null

  @TableField("race_information_id")
  @ApiModelProperty("竞赛信息的ID")
  var raceInformationId: Long? = null
}

@ApiModel("数独记录")
@TableName("game_record")
class GameRecord {
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("数独记录的ID")
  var id: Long? = null

  @TableField("matrix")
  @ApiModelProperty("数独矩阵")
  var matrix: String? = null

  @TableField("holes")
  @ApiModelProperty("空缺的数独")
  var holes: String? = null

  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime? = null

  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime? = null

  @TableField("level_id")
  @ApiModelProperty("数独难度ID")
  var levelId: Long? = null
}