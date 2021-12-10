package com.zumin.sudoku.gateway.config

import mu.KotlinLogging
import org.springframework.cloud.gateway.config.GatewayProperties
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.support.NameUtils
import org.springframework.cloud.gateway.support.NameUtils.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import springfox.documentation.swagger.web.SwaggerResource
import springfox.documentation.swagger.web.SwaggerResourcesProvider

@Primary
@Component
class SwaggerResourceConfig(
  private val routeLocator: RouteLocator,
  private val gatewayProperties: GatewayProperties,
) : SwaggerResourcesProvider {

  override fun get(): List<SwaggerResource> {
    val routes = mutableListOf<String>()
    routeLocator.routes.subscribe { routes.add(it.id) }
    return gatewayProperties.routes
      .filter { routes.contains(it.id) }
      .map { route ->
        route.predicates
          .filter { it.name.equals("Path", ignoreCase = true) }
          .map { toSwaggerResource(route.id, it.args["${GENERATED_NAME_PREFIX}0"]!!.replace("**", "v2/api-docs")) }
      }
      .flatten()
  }

  private fun toSwaggerResource(name: String, location: String): SwaggerResource {
    logger.info("name:{},location:{}", name, location)
    return SwaggerResource().apply {
      this.name = name
      this.location = location
      swaggerVersion = "2.0"
    }
  }
}

private val logger = KotlinLogging.logger { }