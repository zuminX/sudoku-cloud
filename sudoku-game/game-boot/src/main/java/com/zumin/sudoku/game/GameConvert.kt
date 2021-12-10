package com.zumin.sudoku.game

import com.zumin.sudoku.common.core.utils.compression
import com.zumin.sudoku.game.pojo.*
import com.zumin.sudoku.game.utils.unzipToHoles
import com.zumin.sudoku.game.utils.unzipToMatrix


/**
 * 将数独等级对象转换为显示对象
 *
 * @return 数独等级显示对象
 */
fun GameLevel.toGameLevelVO(): GameLevelVO {
  return GameLevelVO(id, name, sort)
}

/**
 * 将查询历史普通游戏记录的结果对象转换为普通游戏记录显示类对象
 *
 * @return 普通游戏记录显示类对象
 */
fun GameNormalRecordResultForHistory.toGameNormalRecordVO(): GameNormalRecordVO {
  return GameNormalRecordVO(answer.unzipToMatrix(), situation, gameRecordResult.toGameRecordVO())
}

/**
 * 将竞赛内容信息对象转换为竞赛信息表对应的实体类对象
 *
 * @param userId          创建者ID
 * @return 竞赛信息表对应的实体类对象
 */
fun GameRaceInformationBody.toGameRaceInformation(userId: Long? = null): GameRaceInformation {
  val result = GameRaceInformation()
  result.title = title
  result.description = description
  result.matrix = matrix.compression()
  result.holes = holes.compression()
  result.startTime = raceTimeRange.start
  result.endTime = raceTimeRange.end
  result.userId = userId
  return result
}

/**
 * 将数独记录业务层对象转换为对应实体类对象
 * 将数独数据中的数独矩阵和题目空缺数组转换为字符串
 *
 * @return 数独记录表对应的实体类对象
 */
fun GameRecordBO.toGameRecord(): GameRecord {
  val result = GameRecord()
  result.id = id
  result.matrix = gameData?.matrix.compression()
  result.holes = gameData?.holes.compression()
  result.startTime = startTime
  result.endTime = endTime
  result.levelId = levelId
  return result
}

/**
 * 将竞赛信息对象转换为数独记录对象
 *
 * @return 数独记录对象
 */
fun GameRaceInformationBody.toGameRecord(): GameRecord {
  val result = GameRecord()
  result.matrix = matrix.compression()
  result.holes = holes.compression()
  result.startTime = raceTimeRange.start
  result.endTime = raceTimeRange.end
  result.levelId = levelId
  return result
}

/**
 * 将查询历史数独记录的结果对象转换为数独记录显示对象
 *
 * @return 数独记录显示对象
 */
fun GameRecordResultForHistory.toGameRecordVO(): GameRecordVO {
  return GameRecordVO(matrix.unzipToMatrix(), holes.unzipToHoles(), startTime, endTime, gameLevelName)
}

/**
 * 将用户答题情况业务层对象转换为显示层对象
 *
 * @return 用户答题情况显示层对象
 */
fun UserAnswerInformationBO.toUserAnswerInformationVO() : UserAnswerInformationVO {
  return UserAnswerInformationVO(situation.code, matrix, spendTime)
}




















