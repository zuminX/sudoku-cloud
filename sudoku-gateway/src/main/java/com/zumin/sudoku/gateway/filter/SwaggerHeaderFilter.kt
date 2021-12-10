package com.zumin.sudoku.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

private const val HEADER_NAME = "X-Forwarded-Prefix"
private const val URI = "/v2/api-docs"

@Component
class SwaggerHeaderFilter : AbstractGatewayFilterFactory<Any>() {

  override fun apply(config: Any): GatewayFilter {
    return GatewayFilter { exchange, chain ->
      val request = exchange.request
      val path = request.uri.path
      if (!path.endsWith(URI, ignoreCase = true)) {
        return@GatewayFilter chain.filter(exchange)
      }
      val basePath = path.substring(0, path.lastIndexOf(URI))
      val newRequest = request.mutate().header(HEADER_NAME, basePath).build()
      val newExchange = exchange.mutate().request(newRequest).build()
      return@GatewayFilter chain.filter(newExchange)
    }
  }
}
