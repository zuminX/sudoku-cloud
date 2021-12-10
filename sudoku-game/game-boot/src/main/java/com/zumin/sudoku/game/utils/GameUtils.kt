package com.zumin.sudoku.game.utils

import com.zumin.sudoku.common.core.utils.deepClone
import com.zumin.sudoku.common.redis.RedisUtils
import com.zumin.sudoku.common.web.utils.getCurrentUserId
import com.zumin.sudoku.game.enums.AnswerSituation
import com.zumin.sudoku.game.pojo.GameDataBO
import com.zumin.sudoku.game.pojo.GameRecordBO
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

// 每局数独游戏最长存在时间(h)
private const val GAME_MAX_TIME = 2

// 游戏记录key值的前缀
private const val GAME_RECORD_PREFIX = "game_record:"

/**
 * 游戏工具类
 */
@Component
class GameUtils(private val redisUtils: RedisUtils) {

  /**
   * 从redis中获取当前数独记录
   *
   * @return 数独记录
   */
  fun getGameRecord(): GameRecordBO? {
    return redisUtils.get<GameRecordBO>(gameRecordKey)
  }

  /**
   * 保存数独记录至redis中
   *
   * @param sudokuRecord 数独记录
   */
  fun setGameRecord(sudokuRecord: GameRecordBO) {
    redisUtils.set(gameRecordKey, sudokuRecord, GAME_MAX_TIME.toLong(), TimeUnit.HOURS)
  }

  /**
   * 移除数独记录
   */
  fun removeGameRecord() {
    redisUtils.delete(gameRecordKey)
  }

  /**
   * 判断当前数独记录是否为记录模式
   *
   * @return 若是记录模式返回true，若不是记录模式或当前不存在记录则返回false
   */
  fun isRecord(): Boolean {
    val sudokuRecord = getGameRecord()
    return sudokuRecord != null && sudokuRecord.isRecord
  }

  /**
   * 获取数独记录的key值
   *
   * @return 数独记录在redis中的key值
   */
  private val gameRecordKey: String
    get() {
      return "$GAME_RECORD_PREFIX${getCurrentUserId()}"
    }
}

/**
 * 判断答题状态
 *
 * @param userMatrix 用户的数独矩阵数据
 * @return 用户答题状态
 */
fun GameDataBO.judgeAnswerSituation(userMatrix: List<List<Int?>>): AnswerSituation {
  val (matrix, holes) = this
  for (i in 0..8) {
    for (j in 0..8) {
      if (holes.isNotHole(i, j)) {
        continue
      }
      val userValue = userMatrix[i][j]
      if (userValue == null || userValue != matrix[i][j]) {
        return AnswerSituation.ERROR
      }
    }
  }
  return AnswerSituation.IDENTICAL
}

/**
 * 隐藏空缺的格子信息
 *
 * @return 隐藏后的数独数据对象
 */
fun GameDataBO.hideVacancyGrid(): GameDataBO {
  return GameDataBO(matrix.setVacancyGridToZero(holes), holes.deepClone())
}