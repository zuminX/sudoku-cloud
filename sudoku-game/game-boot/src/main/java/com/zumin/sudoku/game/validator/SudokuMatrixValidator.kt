package com.zumin.sudoku.game.validator

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * 验证数独矩阵注解
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Constraint(validatedBy = [IntegerSudokuMatrixValidator::class, BooleanSudokuMatrixValidator::class])
annotation class IsSudokuMatrix(
  val message: String = "该数组必须是一个数独矩阵",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<out Payload>> = [],
)

class BooleanSudokuMatrixValidator : ConstraintValidator<IsSudokuMatrix, ArrayList<ArrayList<Boolean?>>> {
  /**
   * 校验二维列表是否为数独矩阵
   *
   * @param value   待校验的列表
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  override fun isValid(value: ArrayList<ArrayList<Boolean?>>, context: ConstraintValidatorContext): Boolean = value.isValid()
}

class IntegerSudokuMatrixValidator : ConstraintValidator<IsSudokuMatrix, ArrayList<ArrayList<Int?>>> {
  /**
   * 校验二维列表是否为数独矩阵
   *
   * @param value   待校验的列表
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  override fun isValid(value: ArrayList<ArrayList<Int?>>, context: ConstraintValidatorContext): Boolean = value.isValid()
}

/**
 * 校验二维列表是否为数独矩阵
 *
 * @return 验证通过返回true，验证失败返回false
 */
private fun <T> ArrayList<ArrayList<T?>>.isValid(): Boolean {
  return this.size == 9 && this.none { it.size != 9 }
}