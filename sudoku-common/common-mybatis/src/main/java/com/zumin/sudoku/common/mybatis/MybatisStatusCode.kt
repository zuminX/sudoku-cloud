package com.zumin.sudoku.common.mybatis

import com.zumin.sudoku.common.core.code.StatusCode

/**
 * Mybatis响应状态编码
 */
enum class MybatisStatusCode(private val number: Int, private val message: String) : StatusCode {
  PAGE_ERROR(10, "获取查询分页数据时发生错误"),
  PAGE_SIZE_ILLEGAL(11, "设置每页查询数据的条数非法");

  override fun getNumber() = number

  override fun getMessage() = message
}