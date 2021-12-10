package com.zumin.sudoku.ums.service

import com.zumin.sudoku.common.redis.ExtCacheable
import com.zumin.sudoku.common.web.domain.StatisticsDateRange
import com.zumin.sudoku.common.web.statistics.GetStatisticsDataTemplate
import com.zumin.sudoku.ums.mapper.SysUserMapper
import com.zumin.sudoku.ums.pojo.StatisticsUserDataBO
import org.springframework.stereotype.Service

/**
 * 统计用户信息的业务层类
 */
@Service
class StatisticsUserService(
  private val sysUserMapper: SysUserMapper,
) {

  /**
   * 获取在[startDate,endDate)中的用户统计信息列表
   *
   * @param dateRange 统计日期范围
   * @return 用户统计信息列表
   */
  @ExtCacheable(value = ["statisticsUserData"], ttlOfDays = 1)
  fun getStatisticsUserData(dateRange: StatisticsDateRange): List<StatisticsUserDataBO> {
    return GetStatisticsDataTemplate<StatisticsUserDataBO>(dateRange).getData { startDate, endDate ->
      val newUserTotal = sysUserMapper.countNewUserByDateBetween(startDate, endDate)
      val recentLoginUserTotal = sysUserMapper.countRecentLoginUserByDateBetween(startDate, endDate)
      StatisticsUserDataBO(newUserTotal, recentLoginUserTotal)
    }
  }

  /**
   * 获取用户总数
   *
   * @return 用户总数
   */
  @ExtCacheable(value = ["userTotal"], ttlOfDays = 1)
  fun getUserTotal(): Int {
    return sysUserMapper.selectCount(null)
  }
}