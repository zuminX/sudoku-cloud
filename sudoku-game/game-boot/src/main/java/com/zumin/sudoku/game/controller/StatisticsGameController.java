package com.zumin.sudoku.game.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.common.web.domain.LocalDateRange;
import com.zumin.sudoku.common.web.domain.StatisticsDateRange;
import com.zumin.sudoku.common.web.enums.StatisticsDate;
import com.zumin.sudoku.game.service.StatisticsGameService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/statistics", tags ="统计信息API接口" )
public class StatisticsGameController extends GameBaseController {

  private final StatisticsGameService statisticsGameService;

  @PostMapping("/assignTotal")
  @ApiOperation("获取指定日期的游戏局数")
  @ApiImplicitParam(name = "dateRange", value = "统计日期范围", dataTypeClass = StatisticsDateRange.class, required = true)
  public List<Integer> getAssignDateGameTotal(@RequestBody @Valid StatisticsDateRange dateRange) {
    return statisticsGameService.getGameTotal(dateRange);
  }

  @GetMapping("/recentTotal")
  @ApiOperation("获取最近日期的游戏局数")
  @ApiImplicitParam(name = "date", value = "统计日期类", dataTypeClass = StatisticsDate.class, required = true)
  public List<Integer> getRecentDateGameTotal(@RequestParam("date") StatisticsDate date) {
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = date.minus(endDate, 7L);
    return getAssignDateGameTotal(new StatisticsDateRange(new LocalDateRange(startDate, endDate), date));
  }

  @GetMapping("/total")
  @ApiOperation("获取系统游戏总局数")
  public Integer getGameTotal() {
    return statisticsGameService.getGameTotal();
  }
}
