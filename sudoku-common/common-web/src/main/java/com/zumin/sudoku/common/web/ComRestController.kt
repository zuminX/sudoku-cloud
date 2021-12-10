package com.zumin.sudoku.common.web

import io.swagger.annotations.Api
import org.springframework.core.annotation.AliasFor
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.annotation.Inherited

/**
 * 整合Controller层的注解
 */
@Api
@Validated
@RequestMapping
@RestController
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ComRestController(
  @get:AliasFor(annotation = RequestMapping::class)
  val path: Array<String> = [],
  @get:AliasFor(annotation = Api::class)
  val tags: Array<String> = [""]
)