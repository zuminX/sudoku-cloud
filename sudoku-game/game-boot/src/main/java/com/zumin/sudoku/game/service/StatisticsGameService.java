package com.zumin.sudoku.game.service;

import com.zumin.sudoku.common.redis.annotation.ExtCacheable;
import com.zumin.sudoku.common.web.domain.StatisticsDateRange;
import com.zumin.sudoku.common.web.template.GetStatisticsDataTemplate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 统计游戏信息的业务层类
 */
@Service
@RequiredArgsConstructor
public class StatisticsGameService {

  private final GameRecordService gameRecordService;

  /**
   * 获取在[startDate,endDate)中的游戏局数
   *
   * @param dateRange 统计日期范围
   * @return 游戏局数
   */
  @ExtCacheable(value = "statisticsGameData", ttlOfDays = 1)
  public List<Integer> getGameTotal(StatisticsDateRange dateRange) {
    return new GetStatisticsDataTemplate<Integer>(dateRange).getData(gameRecordService::countByDate);
  }

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  @ExtCacheable(value = "gameTotal", ttlOfDays = 1)
  public Integer getGameTotal() {
    return gameRecordService.count();
  }
}
