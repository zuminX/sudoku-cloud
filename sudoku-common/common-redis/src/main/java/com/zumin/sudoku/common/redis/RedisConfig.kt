package com.zumin.sudoku.common.redis

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.zumin.sudoku.common.core.utils.CoreUtils
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.lang.reflect.Method
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

/**
 * Redis配置类
 */
@Configuration
class RedisConfig(private val coreUtils: CoreUtils) {
  /**
   * 设置Redis模板
   *
   * @param redisConnectionFactory redis连接工厂
   * @return Redis模板对象
   */
  @Bean
  fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
    val stringSerializer = StringRedisSerializer()
    return RedisTemplate<String, Any>().apply {
      setConnectionFactory(redisConnectionFactory)
      keySerializer = stringSerializer
      valueSerializer = objectJackson2JsonRedisSerializer
      hashKeySerializer = stringSerializer
      hashValueSerializer = objectJackson2JsonRedisSerializer
      afterPropertiesSet()
    }
  }

  /**
   * 缓存管理器
   *
   * @param redisConnectionFactory Redis连接工厂
   * @return 缓存管理器对象
   */
  @Bean
  fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
    return RedisCacheManager(
      RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
      getRedisCacheConfigurationWithTtl(Duration.ZERO),
      redisCacheConfigurationMap
    )
  }

  /**
   * 简单的Key生成方法
   *
   * @return Key生成器对象
   */
  @Bean
  fun simpleKG(): KeyGenerator {
    return getKeyGenerator { it.joinToString("-") }
  }

  /**
   * 对LocalDateTime进行特殊处理的Key生成方法
   *
   * @return Key生成器对象
   */
  @Bean
  fun localDateTimeKG(): KeyGenerator {
    return getKeyGenerator { arr ->
      arr.map { if (it is LocalDateTime) it.toLocalDate() else it }.joinToString("-")
    }
  }

  /**
   * 获取Redis缓存配置，设置指定Key值的过期时间
   *
   * @return 缓存配置对象
   */
  private val redisCacheConfigurationMap: Map<String, RedisCacheConfiguration>
    get() {
      val result: MutableMap<String, RedisCacheConfiguration> = HashMap()
      coreUtils.projectClasses
        .map { it.methods.toList() }
        .flatten()
        .mapNotNull { AnnotationUtils.getAnnotation(it, ExtCacheable::class.java) }
        .forEach {
          val name = it.getName()
          val ttl = it.getTtl()
          if (name != null && ttl != null) {
            result[name] = getRedisCacheConfigurationWithTtl(ttl)
          }
        }
      return result
    }

  /**
   * 使用Ttl获取Redis缓存配置
   *
   * @param ttl 过期时间
   * @return Redis缓存配置
   */
  private fun getRedisCacheConfigurationWithTtl(ttl: Duration): RedisCacheConfiguration {
    return RedisCacheConfiguration
      .defaultCacheConfig()
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(objectJackson2JsonRedisSerializer))
      .entryTtl(ttl)
  }

  /**
   * 获取Redis序列化器
   *
   * @return Jackson2JsonRedis序列化器对象
   */
  private val objectJackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<Any>
    get() {
      val om = ObjectMapper().apply {
        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
          ObjectMapper.DefaultTyping.NON_FINAL,
          JsonTypeInfo.As.PROPERTY)
        //解决jackson2无法反序列化LocalDateTime的问题
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        registerModule(JavaTimeModule())
        registerKotlinModule()
      }
      return Jackson2JsonRedisSerializer(Any::class.java).apply { setObjectMapper(om) }
    }

  /**
   * Key生成器的模板方法
   *
   * @param 将参数转化为字符串的方法
   * @return Key生成器对象
   */
  private fun getKeyGenerator(parameterToString: (parameter: Array<Any>) -> String): KeyGenerator {
    return KeyGenerator { o: Any, method: Method, objects: Array<Any> ->
      ("${o.javaClass.simpleName}.${method.name}[${parameterToString(objects)}]")
    }
  }
}