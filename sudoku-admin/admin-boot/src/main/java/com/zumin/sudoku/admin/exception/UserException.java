package com.zumin.sudoku.admin.exception;


import com.zumin.sudoku.admin.enums.AdminStatusCode;
import com.zumin.sudoku.common.core.exception.BaseException;
import lombok.Getter;

/**
 * 用户异常类
 */
@Getter
public class UserException extends BaseException {

  private static final long serialVersionUID = -799932046646436382L;

  /**
   * 用户异常类的无参构造方法
   */
  public UserException() {
    super(AdminStatusCode.USER_ERROR);
  }

  /**
   * 用户异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public UserException(AdminStatusCode statusCode) {
    super(statusCode);
  }

}
