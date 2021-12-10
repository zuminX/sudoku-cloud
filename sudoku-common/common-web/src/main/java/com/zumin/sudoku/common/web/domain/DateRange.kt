package com.zumin.sudoku.common.web.domain

import com.zumin.sudoku.common.core.NoArg
import com.zumin.sudoku.common.web.statistics.StatisticsDate
import com.zumin.sudoku.common.web.validator.IsLocalDateRange
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.LocalDateTime

@ApiModel("日期范围类")
data class LocalDateRange(
  @ApiModelProperty("开始日期")
  var start: LocalDate? = null,
  @ApiModelProperty("结束日期")
  var end: LocalDate? = null,
)

@ApiModel("日期时间的范围类")
data class LocalDateTimeRange(
  @ApiModelProperty("开始的日期时间")
  var start: LocalDateTime? = null,
  @ApiModelProperty("结束的日期时间")
  var end: LocalDateTime? = null,
)

@NoArg
@ApiModel("统计日期范围类")
data class StatisticsDateRange(
  @ApiModelProperty(value = "日期范围", required = true)
  @IsLocalDateRange(startNotNull = true, endNotNull = true)
  var dateRange: LocalDateRange,
  @ApiModelProperty(value = "统计日期", required = true)
  var statisticsDate: StatisticsDate,
)