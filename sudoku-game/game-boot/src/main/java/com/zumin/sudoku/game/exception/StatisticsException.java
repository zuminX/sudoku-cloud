package com.zumin.sudoku.game.exception;


import com.zumin.sudoku.common.core.enums.StatusCode;
import com.zumin.sudoku.common.core.exception.BaseException;
import com.zumin.sudoku.game.enums.GameStatusCode;
import lombok.Getter;

/**
 * 统计异常类
 */
@Getter
public class StatisticsException extends BaseException {

  private static final long serialVersionUID = 5955311444611029160L;

  /**
   * 统计异常类的无参构造方法
   */
  public StatisticsException() {
    super(GameStatusCode.STATISTICS_ERROR);
  }

  /**
   * 统计异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public StatisticsException(GameStatusCode statusCode) {
    super(statusCode);
  }
}
