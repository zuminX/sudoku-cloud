package com.zumin.sudoku.common.web.handler

import com.alibaba.csp.sentinel.slots.block.BlockException
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.CommonResult.Companion.error
import com.zumin.sudoku.common.core.code.CommonStatusCode
import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.web.exception.FormParameterConversionException
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.Serializable
import javax.validation.ConstraintViolationException

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.exception-handler'])}")
class GlobalExceptionHandler {
  /**
   * 处理所有异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [Exception::class])
  fun exceptionHandler(e: Exception): CommonResult<Exception> {
    logger.error("[Exception]", e)
    return error(CommonStatusCode.ERROR)
  }

  /**
   * 处理基础异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [BaseException::class])
  fun baseExceptionHandler(e: BaseException): CommonResult<BaseException> {
    logger.debug("[${e.javaClass.simpleName}]", e)
    return error(e.statusCode)
  }

  /**
   * 处理流量控制异常
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [BlockException::class])
  fun baseExceptionHandler(e: BlockException): CommonResult<BlockException> {
    logger.debug("[${e.javaClass.simpleName}]", e)
    return error(CommonStatusCode.ACCESS_DENIED)
  }

  /**
   * 处理表单参数转换异常
   *
   * @param e 登录异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [FormParameterConversionException::class])
  fun formParameterConversionExceptionHandler(e: FormParameterConversionException): CommonResult<FormParameterConversionException> {
    logger.warn("[FormParameterConversionException]", e)
    return error(e.statusCode)
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [ConstraintViolationException::class])
  fun constraintViolationExceptionHandler(e: ConstraintViolationException): CommonResult<ConstraintViolationException> {
    logger.debug("[FormParameterConversionException]", e)
    return buildInvalidParamErrorCommonResult(e.constraintViolations.map { it.message }.joinToString(";"))
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [BindException::class])
  fun bindExceptionHandler(e: BindException): CommonResult<java.net.BindException> {
    logger.debug("[ParameterVerificationException]", e)
    return buildInvalidParamErrorCommonResult(e.allErrors.map { it.defaultMessage }.joinToString(";"))
  }

  /**
   * 处理参数校验异常
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = [MethodArgumentNotValidException::class])
  fun bindExceptionHandler(e: MethodArgumentNotValidException): CommonResult<MethodArgumentNotValidException> {
    logger.debug("[ParameterVerificationException]", e)
    return buildInvalidParamErrorCommonResult(e.bindingResult.allErrors.map { it.defaultMessage }.joinToString(";"))
  }

  /**
   * 构建无效的参数的返回结果
   *
   * @param errorStr 异常原因
   * @param <T>   异常类型
   * @return 经过包装的结果对象
   */
  private fun <T : Serializable?> buildInvalidParamErrorCommonResult(errorStr: String): CommonResult<T> {
    var tip: String? = null
    if (errorStr.isNotBlank()) {
      tip = "${CommonStatusCode.INVALID_REQUEST_PARAM_ERROR.getMessage()}：${errorStr}"
    }
    return error(CommonStatusCode.INVALID_REQUEST_PARAM_ERROR, tip)
  }
}

private val logger = KotlinLogging.logger { }