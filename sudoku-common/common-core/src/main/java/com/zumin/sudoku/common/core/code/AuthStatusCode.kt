package com.zumin.sudoku.common.core.code

/**
 * 权限状态编码
 */
enum class AuthStatusCode(private val number: Int, private val message: String) : StatusCode {
  USERNAME_OR_PASSWORD_ERROR(10, "用户名或密码错误"),
  INPUT_PASSWORD_EXCEED_LIMIT(11, "用户输入密码次数超限"),
  USER_NOT_EXIST(12, "用户不存在"),
  ACCOUNT_ERROR(13, "账户异常"),

  TOKEN_INVALID_OR_EXPIRED(20, "token无效或已过期"),
  TOKEN_ACCESS_FORBIDDEN(21, "token已被禁止访问"),

  CLIENT_AUTHENTICATION_FAILED(30, "客户端认证失败"),
  ACCESS_UNAUTHORIZED(31, "访问未授权"),

  CAPTCHA_ERROR(50, "生成验证码时发生错误"),
  CAPTCHA_EXPIRED(51, "验证码过期，请重新生成验证码"),
  CAPTCHA_NOT_EQUALS(52, "验证码错误，请重新生成验证码");

  override fun getNumber() = number

  override fun getMessage() = message
}