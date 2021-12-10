package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.domain.LocalDateRange
import com.zumin.sudoku.common.web.domain.StatisticsDateRange
import com.zumin.sudoku.common.web.statistics.StatisticsDate
import com.zumin.sudoku.ums.pojo.StatisticsUserDataBO
import com.zumin.sudoku.ums.service.StatisticsUserService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import javax.validation.Valid

@ComRestController(path = ["statistics"], tags = ["统计信息API接口"])
class StatisticsUserController(
  private val statisticsUserService: StatisticsUserService,
) {

  @PostMapping("/user/assignDate")
  @ApiOperation("获取指定日期的用户统计数据")
  @ApiImplicitParam(name = "dateRange", value = "统计日期范围", dataTypeClass = StatisticsDateRange::class, required = true)
  fun getAssignDateUserData(@RequestBody @Valid dateRange: StatisticsDateRange): List<StatisticsUserDataBO> {
    return statisticsUserService.getStatisticsUserData(dateRange)
  }

  @GetMapping("/user/recentDate")
  @ApiOperation("获取最近日期的用户统计数据")
  @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate::class, required = true)
  fun getRecentDateUserData(@RequestParam date: StatisticsDate): List<StatisticsUserDataBO> {
    val endDate = LocalDate.now()
    val startDate = date.minus(endDate, 7L)
    return getAssignDateUserData(StatisticsDateRange(LocalDateRange(startDate, endDate), date))
  }

  @ApiOperation("获取系统的用户总数")
  @GetMapping("/user/total")
  fun getUserTotal(): Int {
    return statisticsUserService.getUserTotal()
  }
}