package com.zumin.sudoku.game.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.game.enums.GameStatusCode

/**
 * 统计异常类
 */
class StatisticsException : BaseException {
  /**
   * 统计异常类的无参构造方法
   */
  constructor() : super(GameStatusCode.STATISTICS_ERROR)

  /**
   * 统计异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: GameStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = 5955311444611029160L
  }
}