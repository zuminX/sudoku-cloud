package com.zumin.sudoku.game.pojo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.time.LocalDateTime

@ApiModel("数独等级显示类")
data class GameLevelVO(
  @ApiModelProperty("难度ID")
  val id: Long? = null,
  @ApiModelProperty("难度名")
  val name: String? = null,
  @ApiModelProperty("难度排序")
  val sort: Int? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = 6094658649761954674L
  }
}

@ApiModel("普通游戏记录显示类")
data class GameNormalRecordVO(
  @ApiModelProperty("用户输入的数独矩阵")
  val answer: List<List<Int>>,
  @ApiModelProperty("回答情况")
  val situation: Int? = null,
  @ApiModelProperty("数独记录")
  val gameRecord: GameRecordVO? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = -6703335263096976938L
  }
}

@ApiModel("竞赛信息显示层类")
data class GameRaceInformationVO(
  @ApiModelProperty("竞赛信息的ID")
  val id: Long? = null,
  @ApiModelProperty("竞赛的标题")
  val title: String? = null,
  @ApiModelProperty("竞赛的描述")
  val description: String? = null,
  @ApiModelProperty("开始时间")
  val startTime: LocalDateTime? = null,
  @ApiModelProperty("结束时间")
  val endTime: LocalDateTime? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = 7526373506092340922L
  }
}

@ApiModel("数独记录显示类")
data class GameRecordVO(
  @ApiModelProperty("数独矩阵字符串")
  val matrix: List<List<Int>>? = null,
  @ApiModelProperty("数独格子字符串")
  val holes: List<List<Boolean>>? = null,
  @ApiModelProperty("开始时间")
  val startTime: LocalDateTime? = null,
  @ApiModelProperty("结束时间")
  val endTime: LocalDateTime? = null,
  @ApiModelProperty("数独难度名")
  val gameLevelName: String? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = -8702582802013644392L
  }
}

@ApiModel("用户答题情况显示类")
data class UserAnswerInformationVO(
  @ApiModelProperty("结果情况")
  val situation: Int? = null,
  @ApiModelProperty("数独矩阵")
  val matrix: List<List<Int>>? = null,
  @ApiModelProperty("花费的时间(ms)")
  val spendTime: Long? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = 7015015635447903859L
  }
}

@ApiModel("用户游戏信息显示类")
data class UserGameInformationVO(
  @ApiModelProperty("提交的次数")
  val total: Int? = null,
  @ApiModelProperty("提交正确的次数")
  val correctNumber: Int? = null,
  @ApiModelProperty("平均用时")
  val averageSpendTime: Int? = null,
  @ApiModelProperty("最短用时")
  val minSpendTime: Int? = null,
  @ApiModelProperty("最长用时")
  val maxSpendTime: Int? = null,
  @ApiModelProperty("数独等级名")
  val gameLevelName: String? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = -5952265855843549321L
  }
}