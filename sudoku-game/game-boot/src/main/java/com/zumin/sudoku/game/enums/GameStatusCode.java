package com.zumin.sudoku.game.enums;

import com.zumin.sudoku.common.core.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 游戏系統响应状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum GameStatusCode implements StatusCode {

  //统计异常
  STATISTICS_ERROR(10, "统计信息时发生错误"),
  STATISTICS_INQUIRY_DATE_INVALID(11, "查询统计信息的日期无效"),

  //数独等级异常
  SUDOKU_LEVEL_ERROR(20, "获取数独等级时发生错误"),
  SUDOKU_LEVEL_NOT_FOUND(21, "没有找到对应的数独等级"),

  //数独比赛异常
  RACE_ERROR(30, "获取数独比赛时发生错误"),
  RACE_SUDOKU_MATRIX_Illegal(31, "数独竞赛的题目矩阵不合法"),
  RACE_SUDOKU_HOLES_Illegal(32, "数独竞赛的空缺矩阵不合法"),
  RACE_DURATION_TOO_SHORT(33, "数独竞赛的时长过短"),
  ;

  /**
   * 编号
   */
  private final int number;

  /**
   * 错误信息
   */
  private final String message;
}
