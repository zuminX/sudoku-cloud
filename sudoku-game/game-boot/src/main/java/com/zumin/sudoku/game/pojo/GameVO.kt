package com.zumin.sudoku.game.pojo

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@NoArg
@ApiModel("数独等级显示类")
data class GameLevelVO(
  @ApiModelProperty("难度ID")
  val id: Long,
  @ApiModelProperty("难度名")
  val name: String,
  @ApiModelProperty("难度排序")
  val sort: Int,
)

@NoArg
@ApiModel("普通游戏记录显示类")
data class GameNormalRecordVO(
  @ApiModelProperty("用户输入的数独矩阵")
  val answer: List<List<Int>>,
  @ApiModelProperty("回答情况")
  val situation: Int,
  @ApiModelProperty("数独记录")
  val gameRecord: GameRecordVO,
)

@NoArg
@ApiModel("竞赛信息显示层类")
data class GameRaceInformationVO(
  @ApiModelProperty("竞赛信息的ID")
  val id: Long,
  @ApiModelProperty("竞赛的标题")
  val title: String,
  @ApiModelProperty("竞赛的描述")
  val description: String,
  @ApiModelProperty("开始时间")
  val startTime: LocalDateTime,
  @ApiModelProperty("结束时间")
  val endTime: LocalDateTime,
)

@NoArg
@ApiModel("数独记录显示类")
data class GameRecordVO(
  @ApiModelProperty("数独矩阵字符串")
  val matrix: List<List<Int>>,
  @ApiModelProperty("数独格子字符串")
  val holes: List<List<Boolean>>,
  @ApiModelProperty("开始时间")
  val startTime: LocalDateTime,
  @ApiModelProperty("结束时间")
  val endTime: LocalDateTime? = null,
  @ApiModelProperty("数独难度名")
  val gameLevelName: String? = null,
)

@NoArg
@ApiModel("用户答题情况显示类")
data class UserAnswerInformationVO(
  @ApiModelProperty("结果情况")
  val situation: Int,
  @ApiModelProperty("数独矩阵")
  val matrix: List<List<Int>>,
  @ApiModelProperty("花费的时间(ms)")
  val spendTime: Long,
)

@NoArg
@ApiModel("用户游戏信息显示类")
data class UserGameInformationVO(
  @ApiModelProperty("提交的次数")
  val total: Int,
  @ApiModelProperty("提交正确的次数")
  val correctNumber: Int,
  @ApiModelProperty("平均用时")
  val averageSpendTime: Int,
  @ApiModelProperty("最短用时")
  val minSpendTime: Int,
  @ApiModelProperty("最长用时")
  val maxSpendTime: Int,
  @ApiModelProperty("数独等级名")
  val gameLevelName: String,
)