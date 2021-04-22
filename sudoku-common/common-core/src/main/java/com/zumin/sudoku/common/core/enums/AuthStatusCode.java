package com.zumin.sudoku.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 权限状态编码
 */
@Getter
@ToString
@AllArgsConstructor
public enum AuthStatusCode implements StatusCode {

  USERNAME_OR_PASSWORD_ERROR(10, "用户名或密码错误"),
  INPUT_PASSWORD_EXCEED_LIMIT(11, "用户输入密码次数超限"),
  USER_NOT_EXIST(12, "用户不存在"),
  ACCOUNT_ERROR(13, "账户异常"),

  TOKEN_INVALID_OR_EXPIRED(20, "token无效或已过期"),
  TOKEN_ACCESS_FORBIDDEN(21, "token已被禁止访问"),

  CLIENT_AUTHENTICATION_FAILED(30, "客户端认证失败"),
  ACCESS_UNAUTHORIZED(31, "访问未授权"),
  ;

  /**
   * 编号
   */
  private final int number;

  /**
   * 错误信息
   */
  private final String message;
}
