package com.zumin.sudoku.admin.service;

import com.zumin.sudoku.admin.mapper.SysUserMapper;
import com.zumin.sudoku.admin.pojo.bo.StatisticsUserDataBO;
import com.zumin.sudoku.common.redis.annotation.ExtCacheable;
import com.zumin.sudoku.common.web.domain.StatisticsDateRange;
import com.zumin.sudoku.common.web.template.GetStatisticsDataTemplate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 统计用户信息的业务层类
 */
@Service
@RequiredArgsConstructor
public class StatisticsUserService {

  private final SysUserMapper sysUserMapper;

  /**
   * 获取在[startDate,endDate)中的用户统计信息列表
   *
   * @param dateRange 统计日期范围
   * @return 用户统计信息列表
   */
  @ExtCacheable(value = "statisticsUserData", ttlOfDays = 1)
  public List<StatisticsUserDataBO> getStatisticsUserData(StatisticsDateRange dateRange) {
    return new GetStatisticsDataTemplate<StatisticsUserDataBO>(dateRange).getData((firstDate, lastDate) -> {
      Integer newUserTotal = sysUserMapper.countNewUserByDateBetween(firstDate, lastDate);
      Integer recentLoginUserTotal = sysUserMapper.countRecentLoginUserByDateBetween(firstDate, lastDate);
      return new StatisticsUserDataBO(newUserTotal, recentLoginUserTotal);
    });
  }

  /**
   * 获取用户总数
   *
   * @return 用户总数
   */
  @ExtCacheable(value = "userTotal", ttlOfDays = 1)
  public Integer getUserTotal() {
    return sysUserMapper.selectCount(null);
  }
}
