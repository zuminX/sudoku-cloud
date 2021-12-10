package com.zumin.sudoku.common.mybatis.page

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.util.function.Supplier

@ApiModel("分页数据类")
data class Page<T>(
  @ApiModelProperty("分页信息")
  var pageInformation: PageInformation? = null,
  @ApiModelProperty("分页数据")
  var list: List<T>? = null,
) : Serializable {

  companion object {
    private const val serialVersionUID = 4427124527401721636L
  }
}

@ApiModel("分页信息类")
data class PageInformation(
  @ApiModelProperty("总页数")
  var totalPage: Int? = null,
  @ApiModelProperty("当前页")
  var currentPage: Int? = null,
  @ApiModelProperty("每页的数量")
  var pageSize: Int? = null,
) : Serializable {

  companion object {
    private const val serialVersionUID = 8500765176094961217L
  }
}

/**
 * 分页参数
 *
 * @param <T> 源数据类型
 */
data class PageParam<T> (
  // 分页查询方法
  var queryFunc : Supplier<List<T>>,
  // 查询的页数
  var page : Int? = null,
  // 每页个数
  var pageSize : Int? = null
)
