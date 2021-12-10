package com.zumin.sudoku.game.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.game.enums.GameStatusCode

/**
 * 数独比赛异常类
 */
class GameRaceException : BaseException {
  /**
   * 数独异常类的无参构造方法
   */
  constructor() : super(GameStatusCode.RACE_ERROR)

  /**
   * 数独异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: GameStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = 3794118439430220098L
  }
}