package com.zumin.sudoku.game.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.game.enums.GameStatusCode

/**
 * 数独等级异常类
 */
class SudokuLevelException : BaseException {
  /**
   * 数独等级异常类的无参构造方法
   */
  constructor() : super(GameStatusCode.SUDOKU_LEVEL_ERROR)

  /**
   * 数独等级异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: GameStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = -5342752752493073486L
  }
}