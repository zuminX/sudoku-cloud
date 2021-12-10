package com.zumin.sudoku.gateway

import cn.hutool.json.JSONUtil
import com.zumin.sudoku.common.core.CommonResult.Companion.error
import com.zumin.sudoku.common.core.code.StatusCode
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

/**
 * 响应错误信息
 *
 * @param statusCode 状态码
 * @return 响应
 */
fun ServerHttpResponse.writeFailed(statusCode: StatusCode): Mono<Void> {
  this.statusCode = HttpStatus.OK
  headers[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON_VALUE
  headers["Access-Control-Allow-Origin"] = "*"
  headers["Cache-Control"] = "no-cache"
  val body = JSONUtil.toJsonStr(error<Any>(statusCode))
  val buffer = bufferFactory().wrap(body.toByteArray(StandardCharsets.UTF_8))
  return writeWith(Mono.just(buffer)).doOnError { DataBufferUtils.release(buffer) }
}