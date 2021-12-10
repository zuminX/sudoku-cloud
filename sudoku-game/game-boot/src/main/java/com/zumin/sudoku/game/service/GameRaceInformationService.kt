package com.zumin.sudoku.game.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.common.web.getCurrentUserId
import com.zumin.sudoku.game.enums.GameStatusCode
import com.zumin.sudoku.game.exception.GameRaceException
import com.zumin.sudoku.game.mapper.GameRaceInformationMapper
import com.zumin.sudoku.game.pojo.GameRaceInformation
import com.zumin.sudoku.game.pojo.GameRaceInformationBody
import com.zumin.sudoku.game.pojo.GameRaceInformationVO
import com.zumin.sudoku.game.toGameRaceInformation
import com.zumin.sudoku.game.utils.checkSudokuValidity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime

// 比赛的最短时间
private val MINIMUM_RACE_DURATION = Duration.ofMinutes(5)

@Service
class GameRaceInformationService : ServiceImpl<GameRaceInformationMapper, GameRaceInformation>() {

  /**
   * 新增公开的竞赛
   *
   * @param gameRaceInformationBody 竞赛内容信息对象
   */
  @Transactional
  fun addPublicRace(gameRaceInformationBody: GameRaceInformationBody) {
    if (!gameRaceInformationBody.matrix.checkSudokuValidity()) {
      throw GameRaceException(GameStatusCode.RACE_SUDOKU_MATRIX_Illegal)
    }
    val (start, end) = gameRaceInformationBody.raceTimeRange
    if (start!!.plus(MINIMUM_RACE_DURATION) > end || LocalDateTime.now().plus(MINIMUM_RACE_DURATION) > end) {
      throw GameRaceException(GameStatusCode.RACE_DURATION_TOO_SHORT)
    }
    save(gameRaceInformationBody.toGameRaceInformation(getCurrentUserId()!!))
  }

  /**
   * 获取公开的数独游戏竞赛信息
   *
   * @return 竞赛信息显示层对象
   */
  fun listPublicRace(): List<GameRaceInformationVO> {
    return baseMapper.selectAllByEndTimeBefore(LocalDateTime.now().plusDays(1L))
  }
}