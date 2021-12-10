package com.zumin.sudoku.game.service

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.game.mapper.GameRecordMapper
import com.zumin.sudoku.game.pojo.GameRecord
import com.zumin.sudoku.game.toGameRecord
import com.zumin.sudoku.game.utils.GameUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GameRecordService(
  private val gameUtils: GameUtils,
) : ServiceImpl<GameRecordMapper, GameRecord>() {

  fun countByDate(startDate: LocalDate, endDate: LocalDate): Int {
    return baseMapper.countByDateBetween(startDate, endDate)
  }

  /**
   * 插入当前游戏的数独记录
   */
  @Transactional
  fun saveNowGame() {
    val gameRecordBO = gameUtils.getGameRecord()!!
    val gameRecord = gameRecordBO.toGameRecord()
    save(gameRecord)
    gameUtils.setGameRecord(gameRecordBO.copy(id = gameRecord.id))
  }

  /**
   * 更新当前游戏的数独记录的结束时间
   */
  @Transactional
  fun updateNowGameEndTime() {
    val gameRecord = gameUtils.getGameRecord()!!
    val wrapper = KtUpdateWrapper(GameRecord())
      .set(GameRecord::endTime, gameRecord.endTime)
      .eq(GameRecord::id, gameRecord.id)
    update(wrapper)
  }
}