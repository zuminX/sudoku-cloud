package com.zumin.sudoku.auth.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.core.code.StatusCode

/**
 * 验证码异常类
 */
class CaptchaException : BaseException {
  /**
   * 验证码异常类的无参构造方法
   */
  constructor() : super(AuthStatusCode.CAPTCHA_ERROR)

  /**
   * 验证码异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: StatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = -2676690140146545943L
  }
}