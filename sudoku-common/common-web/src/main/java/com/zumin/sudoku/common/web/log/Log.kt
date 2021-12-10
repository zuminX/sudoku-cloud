package com.zumin.sudoku.common.web.log

/**
 * 自定义操作日志记录注解
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Log(
  /**
   * 日志标题
   */
  val value: String = "",
  /**
   * 功能
   */
  val businessType: BusinessType = BusinessType.INSERT,
  /**
   * 是否保存参数数据
   */
  val isSaveParameterData: Boolean = true,
)

/**
 * 业务操作类型
 */
enum class BusinessType {
  OTHER,
  INSERT,
  UPDATE,
  DELETE,
  SELECT,
  SAVE
}