package com.zumin.sudoku.game.exception;

import com.zumin.sudoku.common.core.exception.BaseException;
import com.zumin.sudoku.game.enums.GameStatusCode;
import java.util.function.Supplier;
import lombok.Getter;

/**
 * 数独等级异常类
 */
@Getter
public class SudokuLevelException extends BaseException {

  private static final long serialVersionUID = -5342752752493073486L;

  /**
   * 数独等级异常类的无参构造方法
   */
  public SudokuLevelException() {
    super(GameStatusCode.SUDOKU_LEVEL_ERROR);
  }

  /**
   * 数独等级异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public SudokuLevelException(GameStatusCode statusCode) {
    super(statusCode);
  }

  /**
   * 返回数独等级异常类的提供者
   *
   * @param statusCode 状态编码
   * @return 数独等级异常类的提供者
   */
  public static Supplier<SudokuLevelException> supplier(GameStatusCode statusCode) {
    return () -> new SudokuLevelException(statusCode);
  }
}
