package com.zumin.sudoku.common.mybatis.page

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.zumin.sudoku.common.core.utils.getFromRequestToInt
import com.zumin.sudoku.common.mybatis.MybatisStatusCode

// 最大分页条数
private const val MAX_PAGE_SIZE = 20

// 每页显示条数的参数名称
private const val PAGE_SIZE_NAME = "pageSize"

// 默认查询的页数
private const val DEFAULT_PAGE = 1

// 默认每页个数
private const val DEFAULT_PAGE_SIZE = 5

// 当前查询页的参数名称
private const val PAGE_NAME = "page"

/**
 * 获取分页数据
 *
 * @param <T>       源数据类型
 * @return 分页数据
 */
fun <T> PageParam<T>.getPageData(): Page<T> {
  val pageTemplateMethod = PageTemplateMethod(this)
  return pageTemplateMethod.getPage { PageInfo.of(it) }
}

/**
 * 获取分页数据，并利用转换器转化为指定类型
 *
 * @param converter 源数据转为目标数据的转换器
 * @param <T>       源数据类型
 * @param <V>       目标数据类型
 * @return 分页数据
 */
fun <T, V> PageParam<T>.getPageData(converter: (T) -> V): Page<V> {
  return PageTemplateMethod(this).getPage { list ->
    val targetList = list.map(converter)
    PageInfo.of(list).apply { setList(targetList as List<T>) }
  } as Page<V>
}

/**
 * 获取分页数据的模板方法
 *
 * @param <T> 分页数据的类型
 */
private class PageTemplateMethod<T>(private val pageParam: PageParam<T>) {
  /**
   * 获取分页数据
   *
   * @param callBack 获取分页详情对象的回调方法
   * @return 分页数据
   */
  fun getPage(callBack: (List<T>) -> PageInfo<T>): Page<T> {
    PageHelper.startPage<Any>(page, pageSize)
    val queryList = pageParam.queryFunc.get()
    return callBack.invoke(queryList).toPage()
  }

  /**
   * 获取查询页数
   *
   * @return 查询页数
   */
  private val page = pageParam.page ?: PAGE_NAME.getFromRequestToInt(DEFAULT_PAGE)

  /**
   * 获取每页个数
   *
   * @return 每页个数
   */
  private val pageSize: Int
    get() {
      val pageSize = pageParam.pageSize ?: PAGE_SIZE_NAME.getFromRequestToInt(DEFAULT_PAGE_SIZE)
      if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
        throw PageException(MybatisStatusCode.PAGE_SIZE_ILLEGAL)
      }
      return pageSize
    }
}
