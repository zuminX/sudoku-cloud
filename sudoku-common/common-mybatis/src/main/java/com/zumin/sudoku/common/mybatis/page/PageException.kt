package com.zumin.sudoku.common.mybatis.page

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.mybatis.MybatisStatusCode

/**
 * 分页异常类
 */
class PageException(
  statusCode: MybatisStatusCode = MybatisStatusCode.PAGE_ERROR,
) : BaseException(statusCode) {

  companion object {
    private const val serialVersionUID = -1202049169478818925L
  }
}