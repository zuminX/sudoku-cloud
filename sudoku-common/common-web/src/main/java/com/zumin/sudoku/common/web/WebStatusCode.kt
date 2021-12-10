package com.zumin.sudoku.common.web

import com.zumin.sudoku.common.core.code.StatusCode

/**
 * Web响应状态编码
 */
enum class WebStatusCode(private val number: Int, private val message: String) : StatusCode {
  STATISTICS_ERROR(10, "统计信息时发生错误"),
  STATISTICS_INQUIRY_DATE_INVALID(11, "查询统计信息的日期无效");

  override fun getNumber() = number

  override fun getMessage() = message
}