package com.zumin.sudoku.common.core.code

enum class CommonStatusCode(private val number: Int, private val message: String) : StatusCode {

  OK(0, "成功请求"),
  ERROR(1, "服务端发生了未知错误"),
  UNAUTHORIZED_ACCESS(2, "非法访问"),
  ACCESS_DENIED(3, "访问流量过大"),

  INVALID_REQUEST_PARAM_ERROR(10, "请求参数不合法"),
  FORM_PARAMETER_CONVERSION_ERROR(11, "请求参数不合法"),
  REQUEST_VALIDATION_FAILED(12, "请求数据格式验证失败"),

  RESOURCE_NOT_FOUND(20, "未找到指定的资源"),

  FILE_ERROR(30, "处理文件时发生错误"),
  FILE_TYPE_ILLEGAL(31, "文件类型非法"),
  FILE_EXPECT_IMAGE(32, "文件类型非法，期望类型为图像类型"),
  FILE_EXPECT_MEDIA(33, "文件类型非法，期望类型为媒体类型"),
  FILE_EMPTY_UPLOAD(34, "上传的文件不能为空");

  override fun getNumber() = number

  override fun getMessage() = message
}