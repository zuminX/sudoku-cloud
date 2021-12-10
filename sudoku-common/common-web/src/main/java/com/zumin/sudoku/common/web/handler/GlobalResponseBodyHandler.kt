package com.zumin.sudoku.common.web.handler

import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.CommonResult.Companion.success
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * 全局响应体处理器
 */
@ControllerAdvice(basePackages = ["com.zumin.sudoku"])
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.response'])}")
class GlobalResponseBodyHandler : ResponseBodyAdvice<Any> {

  /**
   * 是否支持给定的控制器方法返回类型和所选的HttpMessageConverter类型
   *
   * @param returnType 方法参数
   * @param converterType 该方法所在的类
   * @return 返回true代表支持该方法，返回false代表不支持该方法
   */
  override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
    return true
  }

  /**
   * 修改响应对象
   *
   * @param body 原响应对象
   * @param returnType 方法参数
   * @param selectedContentType 媒体类型
   * @param selectedConverterType 所在的类
   * @param request 请求对象
   * @param response 响应对象
   * @return 经过包装的响应对象
   */
  override fun beforeBodyWrite(
    body: Any?,
    returnType: MethodParameter,
    selectedContentType: MediaType,
    selectedConverterType: Class<out HttpMessageConverter<*>>,
    request: ServerHttpRequest,
    response: ServerHttpResponse,
  ): Any? {
    if (body is CommonResult<*>) {
      return body
    }
    return success(body)
  }
}