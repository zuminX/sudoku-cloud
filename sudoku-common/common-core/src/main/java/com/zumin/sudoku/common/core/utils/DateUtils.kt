package com.zumin.sudoku.common.core.utils

import cn.hutool.core.date.DateUtil
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.abs

/**
 * +8的时区
 */
private val ZONE = ZoneOffset.of("+8")

/**
 * 简单的日期字符串，即格式为年月日
 *
 * @return 日期字符串
 */
fun getSimpleDateStr(): String {
  return DateUtil.format(LocalDateTime.now(), "yyyyMMdd")
}

/**
 * 获取两个日期时间的绝对差值
 *
 * @param dateTime 日期时间
 * @return 以ms为单位的差值
 */
fun LocalDateTime.computeAbsDiff(dateTime: LocalDateTime): Long {
  return abs(computeDiff(dateTime))
}

/**
 * 获取两个日期时间的差值
 *
 * @param dateTime 日期时间
 * @return 以ms为单位的差值
 */
fun LocalDateTime.computeDiff(dateTime: LocalDateTime): Long {
  return toTimestamp() - dateTime.toTimestamp()
}

/**
 * 将本地日期时间转换时间戳
 *
 * @return 对应的时间戳
 */
fun LocalDateTime.toTimestamp(): Long {
  return toInstant(ZONE).toEpochMilli()
}