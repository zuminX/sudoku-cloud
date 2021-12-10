package com.zumin.sudoku.common.mybatis.page

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.function.Supplier

@NoArg
@ApiModel("分页数据类")
data class Page<T>(
  @ApiModelProperty("分页信息")
  var pageInformation: PageInformation,
  @ApiModelProperty("分页数据")
  var list: List<T>? = null,
)

@NoArg
@ApiModel("分页信息类")
data class PageInformation(
  @ApiModelProperty("总页数")
  var totalPage: Int,
  @ApiModelProperty("当前页")
  var currentPage: Int,
  @ApiModelProperty("每页的数量")
  var pageSize: Int,
)

/**
 * 分页参数
 *
 * @param <T> 源数据类型
 */
@NoArg
data class PageParam<T>(
  // 分页查询方法
  var queryFunc: Supplier<List<T>>,
  // 查询的页数
  var page: Int? = null,
  // 每页个数
  var pageSize: Int? = null,
)
