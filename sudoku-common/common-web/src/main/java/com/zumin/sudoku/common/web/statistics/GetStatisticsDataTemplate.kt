package com.zumin.sudoku.common.web.statistics

import com.zumin.sudoku.common.web.domain.StatisticsDateRange
import java.time.LocalDate

/**
 * 获取统计数据的模板方法
 *
 * @param <T> 统计数据的类型
 */
class GetStatisticsDataTemplate<T>(private val dateRange: StatisticsDateRange) {
  /**
   * 获取统计数据
   *
   * @param callback 获取统计数据的回调方法
   * @return 统计数据列表
   */
  fun getData(callback: (startDate: LocalDate, endDate: LocalDate) -> T): List<T> {
    val statisticsDate = dateRange.statisticsDate
    val (start, end) = dateRange.dateRange
    var nowDate = statisticsDate.getFirst(start!!)
    val lastDate = statisticsDate.getFirst(end!!)
    val result = mutableListOf<T>()
    while (nowDate < lastDate) {
      val nextDate = statisticsDate.next(nowDate)
      result.add(callback.invoke(nowDate, nextDate))
      nowDate = nextDate
    }
    return result
  }
}