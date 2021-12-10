package com.zumin.sudoku.common.web.handler

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler
import kotlin.Throws
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.alibaba.csp.sentinel.slots.block.BlockException
import org.springframework.stereotype.Component
import java.lang.Exception

/**
 * 自定义流量控制异常处理器
 */
@Component
class WebBlockExceptionHandler : BlockExceptionHandler {
  /**
   * 直接抛出流量控制异常，让GlobalExceptionHandler处理异常
   *
   * @param httpServletRequest  请求对象
   * @param httpServletResponse 响应对象
   * @param e                   流量控制异常
   * @throws Exception 异常
   */
  override fun handle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: BlockException) {
    throw e
  }
}