package com.zumin.sudoku.game.pojo

import com.zumin.sudoku.common.core.NoArg
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 查询历史普通游戏记录的结果
 */
@NoArg
data class GameNormalRecordResultForHistory(
  // 输入的数独矩阵
  val answer: String,
  // 回答情况
  val situation: Int,
  // 查询历史数独记录的结果
  val gameRecordResult: GameRecordResultForHistory,
) : Serializable {
  companion object {
    private const val serialVersionUID = -4010493074200361619L
  }
}

/**
 * 查询历史数独记录的结果
 */
@NoArg
data class GameRecordResultForHistory(
  // 数独矩阵字符串
  val matrix: String,
  // 空缺数独格子字符串
  val holes: String,
  // 开始时间
  val startTime: LocalDateTime? = null,
  // 结束时间
  val endTime: LocalDateTime? = null,
  // 数独难度名
  val gameLevelName: String? = null,
) : Serializable {
  companion object {
    private const val serialVersionUID = 8933147804131852727L
  }
}