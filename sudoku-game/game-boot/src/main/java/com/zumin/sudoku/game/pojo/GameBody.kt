package com.zumin.sudoku.game.pojo

import com.zumin.sudoku.common.web.domain.LocalDateTimeRange
import com.zumin.sudoku.common.web.validator.IsLocalDateTimeRange
import com.zumin.sudoku.game.validator.IsSudokuMatrix
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length

@ApiModel("竞赛内容信息体类")
data class GameRaceInformationBody(
  @ApiModelProperty("数独矩阵")
  @IsSudokuMatrix
  val matrix: List<List<Int>>,
  @ApiModelProperty("题目空缺数组")
  @IsSudokuMatrix
  val holes: List<List<Boolean>>,
  @ApiModelProperty("竞赛的标题")
  @Length(min = 4, max = 64, message = "竞赛标题需在4-64个字符之间")
  val title: String,
  @ApiModelProperty("竞赛的描述")
  @Length(max = 512, message = "竞赛描述不能超过512个字符")
  val description: String,
  @ApiModelProperty("竞赛时间范围")
  @IsLocalDateTimeRange(startNotNull = true, endNotNull = true)
  val raceTimeRange: LocalDateTimeRange,
  @ApiModelProperty("数独难度ID")
  val levelId: Long? = null,
)