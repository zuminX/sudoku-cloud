package com.zumin.sudoku.ums.pojo

import com.zumin.sudoku.common.core.NoArg
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@NoArg
@ApiModel("用户统计数据类")
data class StatisticsUserDataBO(
  @ApiModelProperty("新增用户总数")
  val newUserTotal: Int,
  @ApiModelProperty("活跃用户总数")
  val activeUserTotal: Int,
)
