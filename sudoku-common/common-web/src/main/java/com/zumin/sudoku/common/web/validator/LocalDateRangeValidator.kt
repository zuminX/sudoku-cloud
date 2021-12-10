package com.zumin.sudoku.common.web.validator

import com.zumin.sudoku.common.web.domain.LocalDateRange
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证日期范围类的注解
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [LocalDateRangeValidator::class])
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class IsLocalDateRange(
  val message: String = "日期范围不合法",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = [],
  // 开始日期是否不能为null
  val startNotNull: Boolean = false,
  // 结束日期是否不能为null
  val endNotNull: Boolean = false,
)

/**
 * 日期范围类的验证器类
 */
class LocalDateRangeValidator : ConstraintValidator<IsLocalDateRange, LocalDateRange> {
  // 开始日期不能为空
  private var startDateNotNull = false

  // 结束日期不能为空
  private var endDateNotNull = false

  /**
   * 初始化校验器
   *
   * @param isLocalDateRange 验证日期范围类的注解
   */
  override fun initialize(isLocalDateRange: IsLocalDateRange) {
    startDateNotNull = isLocalDateRange.startNotNull
    endDateNotNull = isLocalDateRange.endNotNull
  }

  /**
   * 校验指定范围的开始的日期是否早于结束的日期
   *
   * @param range   待校验的范围对象
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  override fun isValid(range: LocalDateRange?, context: ConstraintValidatorContext): Boolean {
    if (range == null) {
      return !startDateNotNull && !endDateNotNull
    }
    val (start, end) = range
    if (startDateNotNull && start == null || endDateNotNull && end == null) {
      return false
    }
    return start == null || end == null || end >= start
  }
}
