package com.zumin.sudoku.game.pojo.result;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询历史数独记录的结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRecordResultForHistory implements Serializable {

  private static final long serialVersionUID = 8933147804131852727L;

  /**
   * 数独矩阵字符串
   */
  private String matrix;

  /**
   * 空缺数独格子字符串
   */
  private String holes;

  /**
   * 开始时间
   */
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  private LocalDateTime endTime;

  /**
   * 数独难度名
   */
  private String gameLevelName;
}
