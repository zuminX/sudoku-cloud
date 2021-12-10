package com.zumin.sudoku.common.web.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.web.WebStatusCode

/**
 * 统计异常类
 */
class StatisticsException : BaseException {
  /**
   * 统计异常类的无参构造方法
   */
  constructor() : super(WebStatusCode.STATISTICS_ERROR)

  /**
   * 统计异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: WebStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = 5955311444611029160L
  }
}