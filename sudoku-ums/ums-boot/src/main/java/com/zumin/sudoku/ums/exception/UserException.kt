package com.zumin.sudoku.ums.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.ums.UmsStatusCode

/**
 * 用户异常类
 */
class UserException : BaseException {
  /**
   * 用户异常类的无参构造方法
   */
  constructor() : super(UmsStatusCode.USER_ERROR)

  /**
   * 用户异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: UmsStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = -799932046646436382L
  }
}