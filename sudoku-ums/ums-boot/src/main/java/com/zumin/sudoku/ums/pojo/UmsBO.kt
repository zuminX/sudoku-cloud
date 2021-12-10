package com.zumin.sudoku.ums.pojo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("用户统计数据类")
data class StatisticsUserDataBO(
  @ApiModelProperty("新增用户总数")
  val newUserTotal: Int? = null,
  @ApiModelProperty("活跃用户总数")
  val activeUserTotal: Int? = null,
) : Serializable {

  companion object {
    private const val serialVersionUID = 1527556172753113742L
  }
}