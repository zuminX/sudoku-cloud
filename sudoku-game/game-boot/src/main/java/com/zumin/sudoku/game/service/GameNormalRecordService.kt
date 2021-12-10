package com.zumin.sudoku.game.service

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.common.core.utils.compression
import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.mybatis.page.PageParam
import com.zumin.sudoku.common.mybatis.page.getPageData
import com.zumin.sudoku.common.web.utils.getCurrentUserId
import com.zumin.sudoku.game.enums.AnswerSituation
import com.zumin.sudoku.game.mapper.GameNormalRecordMapper
import com.zumin.sudoku.game.pojo.*
import com.zumin.sudoku.game.toGameNormalRecordVO
import com.zumin.sudoku.game.utils.GameUtils
import com.zumin.sudoku.game.utils.hideVacancyGrid
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameNormalRecordService(
  private val gameUtils: GameUtils,
) : ServiceImpl<GameNormalRecordMapper, GameNormalRecord>() {

  /**
   * 更新游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  fun updateNowGame(inputMatrix: List<List<Int?>>, answerSituation: AnswerSituation) {
    val wrapper = KtUpdateWrapper(GameNormalRecord())
      .set(GameNormalRecord::answer, inputMatrix.compression())
      .set(GameNormalRecord::situation, answerSituation.code)
      .eq(GameNormalRecord::recordId, gameUtils.getGameRecord()!!.id)
    update(wrapper)
  }

  /**
   * 插入当前游戏的游戏记录
   */
  @Transactional
  fun saveNowGame() {
    val gameNormalRecord = GameNormalRecord().apply {
      val gameRecord = gameUtils.getGameRecord()!!
      userId = getCurrentUserId()
      answer = gameRecord.gameData.hideVacancyGrid().matrix.compression()
      recordId = gameRecord.id
    }
    save(gameNormalRecord)
  }

  fun listAverageSpendTimeRankData(limitNumber: Long): List<RankItemBO> {
    return baseMapper.selectAverageSpendTimeRankLimit(limitNumber)
  }

  fun listMinSpendTimeRankData(limitNumber: Long): List<RankItemBO> {
    return baseMapper.selectMinSpendTimeRankLimit(limitNumber)
  }

  fun listCorrectNumberRankData(limitNumber: Long): List<RankItemBO> {
    return baseMapper.selectCorrectNumberRankLimit(limitNumber)
  }

  /**
   * 根据用户ID，获取其游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息列表
   */
  fun listUserGameInformation(userId: Long = getCurrentUserId()): List<UserGameInformationVO> {
    return baseMapper.selectGameInformationByUserId(userId)
  }

  /**
   * 获取历史游戏记录
   *
   * @return 游戏记录的分页信息
   */
  fun getHistoryGameRecord(): Page<GameNormalRecordVO> {
    val userId = getCurrentUserId()
    if (!gameUtils.isRecord()) {
      return getHistoryGameRecordById(userId)
    }
    //防止显示当前正在进行的游戏
    val nowGameRecordId = gameUtils.getGameRecord()!!.id
    return PageParam({ baseMapper.selectByUserIdOrderByStartTimeDescIgnoreGameRecord(userId, nowGameRecordId!!) })
      .getPageData(GameNormalRecordResultForHistory::toGameNormalRecordVO)
  }

  /**
   * 根据用户ID，获取其历史游戏记录
   *
   * @param userId 用户ID
   * @return 普通游戏记录的分页信息
   */
  fun getHistoryGameRecordById(userId: Long): Page<GameNormalRecordVO> {
    return PageParam({ baseMapper.selectByUserIdOrderByStartTimeDesc(userId) })
      .getPageData(GameNormalRecordResultForHistory::toGameNormalRecordVO)
  }

}