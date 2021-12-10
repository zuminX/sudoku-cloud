package com.zumin.sudoku.gateway.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import springfox.documentation.swagger.web.*

@RestController
class SwaggerHandler(
  private val swaggerResources: SwaggerResourcesProvider,
  //TODO 可能存在问题
  private val securityConfiguration: SecurityConfiguration? = null,
  private val uiConfiguration: UiConfiguration? = null,
) {

  @GetMapping("/swagger-resources/configuration/security")
  fun securityConfiguration(): Mono<ResponseEntity<SecurityConfiguration>> {
    return Mono.just(ResponseEntity(securityConfiguration ?: SecurityConfigurationBuilder.builder().build(), HttpStatus.OK))
  }

  @GetMapping("/swagger-resources/configuration/ui")
  fun uiConfiguration(): Mono<ResponseEntity<UiConfiguration>> {
    return Mono.just(ResponseEntity(uiConfiguration ?: UiConfigurationBuilder.builder().build(), HttpStatus.OK))
  }

  @GetMapping("/swagger-resources")
  fun swaggerResources(): Mono<ResponseEntity<*>> {
    return Mono.just(ResponseEntity(
      swaggerResources.get(), HttpStatus.OK))
  }
}