package com.zumin.sudoku.common.redis

import org.springframework.cache.annotation.Cacheable
import org.springframework.core.annotation.AliasFor
import java.lang.annotation.Inherited
import java.time.Duration

/**
 * 扩展Cacheable注解，使其具有过期时间属性
 */
@Cacheable
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS,
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER)
annotation class ExtCacheable(
  // 以秒为单位的过期时间，默认为永不过期
  val ttlOfSeconds: Int = 0,
  // 以分钟为单位的过期时间，默认为永不过期
  val ttlOfMinutes: Int = 0,
  // 以小时为单位的过期时间，默认为永不过期
  val ttlOfHours: Int = 0,
  // 以天为单位的过期时间，默认为永不过期
  val ttlOfDays: Int = 0,
  @get:AliasFor(annotation = Cacheable::class) val value: Array<String> = [],
  @get:AliasFor(annotation = Cacheable::class) val cacheNames: Array<String> = [],
  @get:AliasFor(annotation = Cacheable::class) val keyGenerator: String = "simpleKG",
)

/**
 * 获取extCacheable的TTL
 *
 * @return 以Duration形式的TTL，若未设置则返回null
 */
fun ExtCacheable.getTtl() : Duration? {
  var duration = Duration.ZERO.plusSeconds(ttlOfSeconds.toLong())
  duration = duration.plusMillis(ttlOfMinutes.toLong())
  duration = duration.plusHours(ttlOfHours.toLong())
  duration = duration.plusDays(ttlOfDays.toLong())
  return if (duration > Duration.ZERO) duration else null
}

/**
 * 获取extCacheable的名称，若有多个则返回第一个
 *
 * @return extCacheable的名称，若未设置则返回null
 */
fun ExtCacheable.getName() : String? {
  return value.firstOrNull()
}