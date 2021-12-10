package com.zumin.sudoku.common.mybatis.page

import com.github.pagehelper.PageInfo

/**
 * 将分页详情对象转换为分页数据对象
 *
 * @return 分页数据对象
 */
fun <T : Any?> PageInfo<T>.toPage(): Page<T> {
  return Page(
    pageInformation = toPageInformation(),
    list = list
  )
}

/**
 * 将分页详情对象转换为分页信息对象
 *
 * @return 分页信息对象
 */
private fun PageInfo<*>.toPageInformation(): PageInformation {
  return PageInformation(
    totalPage = pages,
    currentPage = pageNum,
    pageSize = pageSize
  )
}