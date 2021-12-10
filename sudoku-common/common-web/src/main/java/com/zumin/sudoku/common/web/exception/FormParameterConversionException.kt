package com.zumin.sudoku.common.web.exception

import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.core.code.CommonStatusCode
import com.zumin.sudoku.common.web.WebStatusCode

/**
 * 表单参数转换异常类
 */
class FormParameterConversionException : BaseException {
  /**
   * 转化字符串
   */
  var convertString: String? = null
    private set

  /**
   * 目标Class对象
   */
  var targetClass: Class<*>? = null
    private set

  /**
   * 表单参数转换异常类的无参构造方法
   */
  constructor() : super(CommonStatusCode.FORM_PARAMETER_CONVERSION_ERROR)

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param convertString 进行转换的字符串
   * @param targetClass   转换的Class对象
   */
  constructor(convertString: String, targetClass: Class<*>) : super(CommonStatusCode.FORM_PARAMETER_CONVERSION_ERROR) {
    this.convertString = convertString
    this.targetClass = targetClass
  }

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  constructor(statusCode: WebStatusCode) : super(statusCode)

  companion object {
    private const val serialVersionUID = 1166533932002631826L
  }
}