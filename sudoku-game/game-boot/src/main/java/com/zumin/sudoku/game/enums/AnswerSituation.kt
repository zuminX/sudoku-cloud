package com.zumin.sudoku.game.enums

/**
 * 用户提交填写数独的结果情况类
 */
enum class AnswerSituation(val code: Int) {
  // 与答案一致
  IDENTICAL(0),

  // 未完成
  UNFINISHED(1),

  // 错误
  ERROR(2);

  // 回答是否正确
  val isRight: Boolean
    get() = this == IDENTICAL
}