package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.domain.LocalDateRange
import com.zumin.sudoku.common.web.domain.StatisticsDateRange
import com.zumin.sudoku.common.web.statistics.StatisticsDate
import com.zumin.sudoku.game.service.StatisticsGameService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import javax.validation.Valid

@ComRestController(path = ["/statistics"], tags = ["统计信息API接口"])
class StatisticsGameController(
  private val statisticsGameService: StatisticsGameService,
) : GameBaseController() {

  @PostMapping("/assignTotal")
  @ApiOperation("获取指定日期的游戏局数")
  @ApiImplicitParam(name = "dateRange", value = "统计日期范围", dataTypeClass = StatisticsDateRange::class, required = true)
  fun getAssignDateGameTotal(@RequestBody @Valid dateRange: StatisticsDateRange): List<Int> {
    return statisticsGameService.getGameTotal(dateRange)
  }

  @GetMapping("/recentTotal")
  @ApiOperation("获取最近日期的游戏局数")
  @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate::class, required = true)
  fun getRecentDateGameTotal(@RequestParam("date") date: StatisticsDate): List<Int> {
    val endDate = LocalDate.now()
    val startDate = date.minus(endDate, 7L)
    return getAssignDateGameTotal(StatisticsDateRange(LocalDateRange(startDate, endDate), date))
  }

  @ApiOperation("获取系统游戏总局数")
  @GetMapping("/total")
  fun getGameTotal(): Int {
    return statisticsGameService.getGameTotal()
  }
}