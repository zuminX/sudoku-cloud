package com.zumin.sudoku.common.core.utils

import cn.hutool.http.HttpStatus
import cn.hutool.json.JSONUtil
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse

// 获取请求属性
private val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes

// 获取请求对象
val request = requestAttributes.request

// 获取响应对象
val response = requestAttributes.response

/**
 * 获取String参数
 *
 * @param defaultValue 默认值
 * @return 参数
 */
fun String.getFromRequest(): String? {
  return request.getParameter(this)
}

/**
 * 获取String参数
 *
 * @param defaultValue 默认值
 * @return 参数
 */
fun String.getFromRequest(defaultValue: String): String {
  return getFromRequest() ?: defaultValue
}

/**
 * 获取Integer参数
 *
 * @return 参数
 */
fun String.getFromRequestToInt(): Int? {
  return getFromRequest()?.toInt()
}

/**
 * 获取Integer参数
 *
 * @param defaultValue 默认值
 * @return 参数
 */
fun String.getFromRequestToInt(defaultValue: Int): Int {
  return getFromRequestToInt() ?: defaultValue
}

/**
 * 将数据转换为JSON数据响应给客户端
 *
 * @param data     数据
 */
fun HttpServletResponse.returnJsonData(data: Any?) {
  status = HttpStatus.HTTP_OK
  setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
  setHeader("Access-Control-Allow-Origin", "*")
  setHeader("Cache-Control", "no-cache")
  writer.write(JSONUtil.toJsonStr(data))
  writer.flush()
  writer.close()
}


