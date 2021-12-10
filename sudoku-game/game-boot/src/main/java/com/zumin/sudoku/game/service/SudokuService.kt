package com.zumin.sudoku.game.service

import cn.hutool.core.util.RandomUtil
import com.zumin.sudoku.common.core.utils.computeAbsDiff
import com.zumin.sudoku.game.pojo.*
import com.zumin.sudoku.game.utils.GameUtils
import com.zumin.sudoku.game.utils.generateSudokuFinal
import com.zumin.sudoku.game.utils.hideVacancyGrid
import com.zumin.sudoku.game.utils.judgeAnswerSituation
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * 数独业务层类
 */
@Service
class SudokuService(
  private val gameUtils: GameUtils,
) {

  /**
   * 生成数独题目
   *
   * @param gameLevel 数独等级
   * @param isRecord  是否记录
   * @return 数独题目
   */
  fun generateSudokuTopic(gameLevel: GameLevel, isRecord: Boolean): GameDataBO {
    val gameDataBO = generateSudokuFinal(gameLevel.minEmpty!!, gameLevel.maxEmpty!!)
    saveGameRecord(gameDataBO, gameLevel.id!!, isRecord)
    return gameDataBO.hideVacancyGrid()
  }

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  fun getHelp(userMatrix: List<List<Int?>>): SudokuGridInformationBO? {
    val matrix = gameUtils.getGameRecord()!!.gameData.matrix
    val errorGridInformationList = (0..8).map { i ->
      (0..8).filter { j ->
        val user = userMatrix[i][j]
        user == null || user != matrix[i][j]
      }.map { j -> SudokuGridInformationBO(i, j, matrix[i][j]) }
    }.flatten()
    return randomGridInformation(errorGridInformationList)
  }

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  fun checkSudokuData(userMatrix: List<List<Int?>>): UserAnswerInformationBO {
    updateEndTimeForSudokuRecord()
    val (_, gameData, startTime, endTime) = gameUtils.getGameRecord()!!
    val situation = gameData.judgeAnswerSituation(userMatrix)
    return UserAnswerInformationBO(situation, gameData.matrix, endTime!!.computeAbsDiff(startTime))
  }

  /**
   * 更新Redis中的数独记录的结束时间字段
   */
  private fun updateEndTimeForSudokuRecord() {
    val gameRecord = gameUtils.getGameRecord()!!
    gameUtils.setGameRecord(gameRecord.copy(endTime = LocalDateTime.now()))
  }

  /**
   * 随机返回一个格子信息
   *
   * @param errorGridInformationList 错误的数独格子信息列表
   * @return 数独格子信息
   */
  private fun randomGridInformation(errorGridInformationList: List<SudokuGridInformationBO>): SudokuGridInformationBO? {
    val size = errorGridInformationList.size
    if (size == 0) {
      return null
    }
    return errorGridInformationList[RandomUtil.randomInt(0, size)]
  }

  /**
   * 保存游戏记录
   *
   * @param gameDataBO    数独数据
   * @param sudokuLevelId 数独难度ID
   * @param isRecord      是否记录
   */
  private fun saveGameRecord(gameDataBO: GameDataBO, sudokuLevelId: Long, isRecord: Boolean) {
    val gameRecordBO = GameRecordBO(gameData = gameDataBO, startTime = LocalDateTime.now(), levelId = sudokuLevelId, isRecord =
    isRecord)
    gameUtils.setGameRecord(gameRecordBO)
  }
}