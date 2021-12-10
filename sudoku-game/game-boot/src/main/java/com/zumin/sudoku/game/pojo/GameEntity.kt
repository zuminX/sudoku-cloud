package com.zumin.sudoku.game.pojo

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@NoArg
@ApiModel("游戏等级")
@TableName("game_level")
data class GameLevel(
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("数独难度ID")
  var id: Long? = null,
  @TableField("name")
  @ApiModelProperty("难度名")
  var name: String,
  @TableField("min_empty")
  @ApiModelProperty("最小的空缺格子数")
  var minEmpty: Int,
  @TableField("max_empty")
  @ApiModelProperty("最大的空缺格子数")
  var maxEmpty: Int,
  @TableField("sort")
  @ApiModelProperty("难度排序")
  var sort: Int = 0,
)

@NoArg
@ApiModel("普通游戏记录")
@TableName(value = "game_normal_record")
data class GameNormalRecord (
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "游戏记录的ID")
  var id: Long? = null,
  @TableField(value = "answer")
  @ApiModelProperty(value = "输入的数独矩阵")
  var answer: String? = null,
  @TableField(value = "situation")
  @ApiModelProperty(value = "回答情况")
  var situation: Int = 0,
  @TableField(value = "user_id")
  @ApiModelProperty(value = "用户ID")
  var userId: Long,
  @TableField(value = "record_id")
  @ApiModelProperty(value = "数独记录ID")
  var recordId: Long,
)

@NoArg
@ApiModel("竞赛游戏信息")
@TableName("game_race_information")
data class GameRaceInformation (
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("竞赛信息的ID")
  var id: Long? = null,
  @TableField("title")
  @ApiModelProperty("竞赛的标题")
  var title: String,
  @TableField("description")
  @ApiModelProperty("竞赛的描述")
  var description: String? = null,
  @TableField("matrix")
  @ApiModelProperty("数独矩阵")
  var matrix: String,
  @TableField("holes")
  @ApiModelProperty("空缺的数独")
  var holes: String,
  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime = LocalDateTime.now(),
  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime,
  @TableField("user_id")
  @ApiModelProperty("创建用户的ID")
  var userId: Long,
)

@NoArg
@ApiModel("竞赛记录")
@TableName("game_race_record")
data class GameRaceRecord (
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("竞赛记录的ID")
  var id: Long? = null,
  @TableField("answer")
  @ApiModelProperty("输入的数独矩阵")
  var answer: String? = null,
  @TableField("situation")
  @ApiModelProperty("回答情况")
  var situation: Int = 1,
  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime = LocalDateTime.now(),
  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime? = null,
  @TableField("user_id")
  @ApiModelProperty("用户ID")
  var userId: Long,
  @TableField("race_information_id")
  @ApiModelProperty("竞赛信息的ID")
  var raceInformationId: Long,
)

@NoArg
@ApiModel("数独记录")
@TableName("game_record")
data class GameRecord (
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty("数独记录的ID")
  var id: Long? = null,
  @TableField("matrix")
  @ApiModelProperty("数独矩阵")
  var matrix: String,
  @TableField("holes")
  @ApiModelProperty("空缺的数独")
  var holes: String,
  @TableField("start_time")
  @ApiModelProperty("开始时间")
  var startTime: LocalDateTime = LocalDateTime.now(),
  @TableField("end_time")
  @ApiModelProperty("结束时间")
  var endTime: LocalDateTime? = null,
  @TableField("level_id")
  @ApiModelProperty("数独难度ID")
  var levelId: Long? = null,
)