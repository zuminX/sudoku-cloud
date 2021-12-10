package com.zumin.sudoku.common.web.validator

import com.zumin.sudoku.common.web.domain.LocalDateTimeRange
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证日期时间范围类的注解
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [LocalDateTimeRangeValidator::class])
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class IsLocalDateTimeRange(
  val message: String = "日期时间范围不合法",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = [],
  // 开始的日期时间是否不能为null
  val startNotNull: Boolean = false,
  // 结束的日期时间是否不能为null
  val endNotNull: Boolean = false,
)

/**
 * 日期时间范围类的验证器类
 */
class LocalDateTimeRangeValidator : ConstraintValidator<IsLocalDateTimeRange, LocalDateTimeRange?> {
  // 开始时间不能为空
  private var startTimeNotNull = false

  // 结束时间不能为空
  private var endTimeNotNull = false

  /**
   * 初始化校验器
   *
   * @param isLocalDateTimeRange 验证日期时间范围类的注解
   */
  override fun initialize(isLocalDateTimeRange: IsLocalDateTimeRange) {
    startTimeNotNull = isLocalDateTimeRange.startNotNull
    endTimeNotNull = isLocalDateTimeRange.endNotNull
  }

  /**
   * 校验指定范围的开始的日期时间是否早于结束的日期时间
   *
   * @param range   待校验的范围对象
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  override fun isValid(range: LocalDateTimeRange?, context: ConstraintValidatorContext): Boolean {
    if (range == null) {
      return !startTimeNotNull && !endTimeNotNull
    }
    val (start, end) = range
    if (startTimeNotNull && start == null || endTimeNotNull && end == null) {
      return false
    }
    return start == null || end == null || end >= start
  }
}
