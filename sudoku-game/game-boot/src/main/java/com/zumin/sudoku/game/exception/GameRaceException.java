package com.zumin.sudoku.game.exception;


import com.zumin.sudoku.common.core.enums.StatusCode;
import com.zumin.sudoku.common.core.exception.BaseException;
import com.zumin.sudoku.game.enums.GameStatusCode;
import lombok.Getter;

/**
 * 数独比赛异常类
 */
@Getter
public class GameRaceException extends BaseException {

  private static final long serialVersionUID = 3794118439430220098L;

  /**
   * 数独异常类的无参构造方法
   */
  public GameRaceException() {
    super(GameStatusCode.RACE_ERROR);
  }

  /**
   * 数独异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public GameRaceException(GameStatusCode statusCode) {
    super(statusCode);
  }
}
