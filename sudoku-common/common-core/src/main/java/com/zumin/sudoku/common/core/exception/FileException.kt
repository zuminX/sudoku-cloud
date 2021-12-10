package com.zumin.sudoku.common.core.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.core.code.CommonStatusCode

/**
 * 文件异常类
 */
class FileException : BaseException {
  constructor() : super(CommonStatusCode.FILE_ERROR)
  constructor(commonStatusCode: CommonStatusCode) : super(commonStatusCode)
  constructor(cause: Throwable) : super(CommonStatusCode.FILE_ERROR, cause)

  companion object {
    private const val serialVersionUID = -658002836273149602L
  }
}