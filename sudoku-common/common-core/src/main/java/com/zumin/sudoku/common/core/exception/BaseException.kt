package com.zumin.sudoku.common.core.exception

import com.zumin.sudoku.common.core.code.StatusCode

/**
 * 基础异常类
 */
open class BaseException : RuntimeException {

  /**
   * 异常编号
   */
  val statusCode: StatusCode

  constructor(statusCode: StatusCode) {
    this.statusCode = statusCode
  }

  constructor(statusCode: StatusCode, cause: Throwable) : super(cause) {
    this.statusCode = statusCode
  }

  companion object {
    private const val serialVersionUID: Long = 5728954500514726698L
  }
}