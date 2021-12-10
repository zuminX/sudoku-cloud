package com.zumin.sudoku.common.web.statistics

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@ApiModel("统计日期类")
enum class StatisticsDate(
  @field:ApiModelProperty("统计日期名称")
  val dateName: String,
) : LocalDateOperation {
  DAILY("日") {
    /**
     * 返回日期加上指定天的副本
     *
     * @param date 日期
     * @param amountToAdd 天数
     * @return 给定日期增加指定天数后的日期
     */
    override fun plus(date: LocalDate, amountToAdd: Long): LocalDate = date.plusDays(amountToAdd)

    /**
     * 获取该日的第一天
     *
     * @param date 日期
     * @return 该日的第一天
     */
    override fun getFirst(date: LocalDate) = date
  },
  EACH_MONTH("月") {
    /**
     * 返回日期加上指定月的副本
     *
     * @param date 日期
     * @param amountToAdd 月数
     * @return 给定日期增加指定月数后的日期
     */
    override fun plus(date: LocalDate, amountToAdd: Long): LocalDate = date.plusMonths(amountToAdd)

    /**
     * 获取该月的第一天
     *
     * @param date 日期
     * @return 该月的第一天
     */
    override fun getFirst(date: LocalDate): LocalDate = date.with(TemporalAdjusters.firstDayOfMonth())
  },
  EACH_QUARTER("季度") {
    /**
     * 返回日期加上指定季度的副本
     *
     * @param date 日期
     * @param amountToAdd 季度数
     * @return 给定日期增加指定季度数后的日期
     */
    override fun plus(date: LocalDate, amountToAdd: Long): LocalDate = date.plusMonths(4 * amountToAdd)

    /**
     * 获取该季度的第一天
     *
     * @param date 日期
     * @return 该季度的第一天
     */
    override fun getFirst(date: LocalDate): LocalDate = LocalDate.of(date.year, date.month.firstMonthOfQuarter(), 1)
  },
  EACH_YEAR("年") {
    /**
     * 返回日期加上指定年的副本
     *
     * @param date 日期
     * @param amountToAdd 年数
     * @return 给定日期增加指定年数后的日期
     */
    override fun plus(date: LocalDate, amountToAdd: Long): LocalDate = date.plusYears(amountToAdd)

    /**
     * 获取该年的第一天
     *
     * @param date 日期
     * @return 该年的第一天
     */
    override fun getFirst(date: LocalDate): LocalDate = date.with(TemporalAdjusters.firstDayOfYear())
  };
}

/**
 * 根据名字查找对应的统计日期对象
 *
 * @return 若存在该名字的统计日期对象，则将其返回。否则返回null
 */
fun String.toStatisticsDate(): StatisticsDate? {
  return StatisticsDate.values().firstOrNull { it.dateName == this }
}

/**
 * LocalDate操作的接口
 */
interface LocalDateOperation {
  /**
   * 返回日期加上增加量的副本
   *
   * @param date        日期
   * @param amountToAdd 增加量
   * @return 给定日期增加给定增量后的日期
   */
  fun plus(date: LocalDate, amountToAdd: Long): LocalDate

  /**
   * 获取该日期的第一天
   *
   * @param date 日期
   * @return 该日期的第一天
   */
  fun getFirst(date: LocalDate): LocalDate

  /**
   * 获取该日期的下一个日期
   *
   * @param date 日期
   * @return 给定日期的下一个日期
   */
  fun next(date: LocalDate) = plus(date, 1L)

  /**
   * 返回日期减去减少量的副本
   *
   * @param date             日期
   * @param amountToSubtract 减少量
   * @return 给定日期减去给定减量后的日期
   */
  fun minus(date: LocalDate, amountToSubtract: Long) = plus(date, -amountToSubtract)
}