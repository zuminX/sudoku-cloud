package com.zumin.sudoku.game.pojo

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.zumin.sudoku.game.enums.AnswerSituation
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.Transient
import java.time.LocalDateTime

@ApiModel("数独数据业务类")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
data class GameDataBO(
  @ApiModelProperty("数独矩阵")
  val matrix: List<MutableList<Int>> = List(9) { MutableList(9) { 0 } },
  @ApiModelProperty("题目空缺数组")
  val holes: List<MutableList<Boolean>> = List(9) { MutableList(9) { false } },
)

@ApiModel("数独记录业务类")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
data class GameRecordBO(
  @ApiModelProperty("数独记录的ID")
  val id: Long? = null,
  @ApiModelProperty("数独数据")
  val gameData: GameDataBO,
  @ApiModelProperty("开始时间")
  val startTime: LocalDateTime,
  @ApiModelProperty("结束时间")
  val endTime: LocalDateTime? = null,
  @ApiModelProperty("数独难度ID")
  val levelId: Long? = null,
  @ApiModelProperty("是否记录")
  @Transient
  val isRecord: Boolean = false,
)

@ApiModel("排行项业务类")
data class RankItemBO(
  @ApiModelProperty("数独等级ID")
  val gameLevelId: Long,
  @ApiModelProperty("排行项数据列表")
  val rankItemDataList: List<RankItemDataBO>,
)

@ApiModel("排行项数据业务类")
data class RankItemDataBO(
  @ApiModelProperty("用户名")
  val username: String,
  @ApiModelProperty("排行项数据")
  val data: Int,
)

@ApiModel("用户统计数据类")
data class StatisticsUserDataBO(
  @ApiModelProperty("新增用户总数")
  val newUserTotal: Int? = null,
  @ApiModelProperty("活跃用户总数")
  val activeUserTotal: Int? = null,
)

@ApiModel("数独格子信息业务类")
data class SudokuGridInformationBO(
  @ApiModelProperty("行数")
  val row: Int? = null,
  @ApiModelProperty("列数")
  val column: Int? = null,
  @ApiModelProperty("对应格子的数值")
  val value: Int? = null,
)

@ApiModel("用户答题情况业务类")
data class UserAnswerInformationBO(
  @ApiModelProperty("结果情况")
  val situation: AnswerSituation,
  @ApiModelProperty("数独矩阵")
  val matrix: List<List<Int>>,
  @ApiModelProperty("花费的时间(ms)")
  val spendTime: Long,
)