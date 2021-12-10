package com.zumin.sudoku.game.service

import com.zumin.sudoku.common.redis.ExtCacheable
import com.zumin.sudoku.common.web.domain.StatisticsDateRange
import com.zumin.sudoku.common.web.statistics.GetStatisticsDataTemplate
import org.springframework.stereotype.Service

/**
 * 统计游戏信息的业务层类
 */
@Service
class StatisticsGameService(
  private val gameRecordService: GameRecordService,
) {

  /**
   * 获取在[startDate,endDate)中的游戏局数
   *
   * @param dateRange 统计日期范围
   * @return 游戏局数
   */
  @ExtCacheable(value = ["statisticsGameData"], ttlOfDays = 1)
  fun getGameTotal(dateRange: StatisticsDateRange): List<Int> {
    return GetStatisticsDataTemplate<Int>(dateRange).getData(gameRecordService::countByDate)
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @ExtCacheable(value = ["gameTotal"], ttlOfDays = 1)
  fun getGameTotal(): Int {
    return gameRecordService.count()
  }
}