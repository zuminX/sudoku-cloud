package com.zumin.sudoku.auth.exception

import com.zumin.sudoku.common.core.CommonResult.Companion.error
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.code.AuthStatusCode
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * 认证异常处理
 */
@RestControllerAdvice
class AuthExceptionHandler {

  /**
   * 处理用户名和密码异常
   *
   * @param e 无效认证异常
   * @return 返回结果
   */
  @ExceptionHandler(InvalidGrantException::class)
  fun handleInvalidGrantException(e: InvalidGrantException?): CommonResult<*> {
    return error<Any>(AuthStatusCode.USERNAME_OR_PASSWORD_ERROR)
  }

  /**
   * 处理账户异常(禁用、锁定、过期)
   *
   * @param e 内部认证异常
   * @return 返回结果
   */
  @ExceptionHandler(InternalAuthenticationServiceException::class)
  fun handleInternalAuthenticationServiceException(e: InternalAuthenticationServiceException): CommonResult<*> {
    return error<Any>(AuthStatusCode.ACCOUNT_ERROR, e.message)
  }
}