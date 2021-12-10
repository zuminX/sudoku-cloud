package com.zumin.sudoku.common.redis

import org.springframework.data.redis.core.BoundSetOperations
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations.TypedTuple
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * Redis工具类
 */
@Component
class RedisUtils(private val redisTemplate: RedisTemplate<String, Any>) {

  /**
   * 判断Redis中是否有该缓存键
   *
   * @param key 缓存的键值
   * @return 若存在则返回true，否则返回false
   */
  fun has(key: String?): Boolean {
    return !key.isNullOrBlank() && redisTemplate.hasKey(key)
  }

  /**
   * 缓存对象
   *
   * @param key   缓存的键值
   * @param value 缓存的值
   */
  fun <T> set(key: String, value: T) {
    if (isKeyValueNull<T>(key, value)) {
      return
    }
    redisTemplate.opsForValue().set(key, value!!)
  }

  /**
   * 缓存对象，并设置其的过期时间
   *
   * @param key      缓存的键值
   * @param value    缓存的值
   * @param timeout  时间
   * @param timeUnit 时间颗粒度
   */
  fun <T> set(key: String, value: T, timeout: Long, timeUnit: TimeUnit) {
    if (isKeyValueNull<T>(key, value)) {
      return
    }
    redisTemplate.opsForValue().set(key, value!!, timeout, timeUnit)
  }

  /**
   * 获得缓存对象
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> get(key: String): T? {
    return get(key, Any::class.java) as T?
  }

  /**
   * 获得缓存对象
   *
   * @param key   缓存键值
   * @param clazz 数据类型
   * @return 缓存键值对应的数据
   */
  fun <T> get(key: String?, clazz: Class<T>): T? {
    if (key.isNullOrBlank()) {
      return null
    }
    val result = redisTemplate.opsForValue()[key]
    if (clazz == Long::class.java && result is Int) {
      return result.toString().toLong() as T
    }
    return result as T?
  }

  /**
   * 删除对象
   *
   * @param key 缓存键值
   */
  fun delete(key: String?) {
    if (key.isNullOrBlank()) {
      return
    }
    redisTemplate.delete(key)
  }

  /**
   * 获得缓存对象，并删除该缓存键
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getAndDelete(key: String): T {
    return getAndDelete(key, Any::class.java) as T
  }

  /**
   * 获得缓存对象，并删除该缓存键
   *
   * @param key   缓存键值
   * @param clazz 数据类型
   * @return 缓存键值对应的数据
   */
  fun <T> getAndDelete(key: String, clazz: Class<T>): T? {
    val result: T? = get(key, clazz)
    delete(key)
    return result
  }

  /**
   * 删除集合
   *
   * @param collection 集合对象
   */
  fun delete(collection: Collection<String>) {
    redisTemplate.delete(collection)
  }

  /**
   * 缓存列表数据
   *
   * @param key      缓存的键值
   * @param dataList 待缓存的List数据
   */
  fun setList(key: String, vararg dataList: Any) {
    if (isKeyCollectionNull(key, dataList.toList())) {
      return
    }
    redisTemplate.opsForList().leftPushAll(key, dataList)
  }

  /**
   * 获得缓存的列表对象
   *
   * @param key 缓存的键值
   * @return 缓存键值对应的数据
   */
  fun <T> getList(key: String?): List<T?> {
    if (key.isNullOrBlank()) {
      return emptyList()
    }
    val listOperation = redisTemplate.opsForList() as ListOperations<String, T>
    val size = listOperation.size(key) ?: return listOf()
    return (0 until size).map { listOperation.index(key, it) }
  }

  /**
   * 缓存集合数据
   *
   * @param key     缓存键值
   * @param dataSet 缓存的数据
   */
  fun setSet(key: String, dataSet: Set<Any>) {
    if (isKeyCollectionNull(key, dataSet)) {
      return
    }
    val setOperation = redisTemplate.boundSetOps(key)
    dataSet.forEach { setOperation.add(it) }
  }

  /**
   * 获得缓存的集合数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getSet(key: String?): Set<T>? {
    if (key.isNullOrBlank()) {
      return emptySet()
    }
    val operation = redisTemplate.boundSetOps(key) as BoundSetOperations<String, T>
    return operation.members()
  }

  /**
   * 缓存排序集合数据
   *
   * @param key    缓存键值
   * @param tuples 值-分数对集合
   */
  fun setZSet(key: String?, tuples: Set<TypedTuple<*>?>?) {
    if (key.isNullOrBlank() || tuples == null) {
      return
    }
    delete(key)
    redisTemplate.boundZSetOps(key).add(tuples as Set<TypedTuple<Any>>)
  }

  /**
   * 向排序集合增加数据
   *
   * @param key   缓存键值
   * @param value 值
   * @param score 分数
   */
  fun addZSet(key: String, value: Any, score: Double) {
    if (isKeyValueNull(key, value)) {
      return
    }
    redisTemplate.boundZSetOps(key).add(value, score)
  }

  /**
   * 向排序集合增加数据
   *
   * @param key   缓存键值
   * @param tuple 值-分数对
   */
  fun addZSet(key: String, tuple: TypedTuple<*>) {
    if (isKeyValueNull(key, tuple)) {
      return
    }
    redisTemplate.boundZSetOps(key).add(setOf(tuple as TypedTuple<Any>))
  }

  /**
   * 获得排序集合数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getZSet(key: String): Set<T>? {
    return getZSetByRange(key, 0, -1)
  }

  /**
   * 获得下标为[start,end]的排序集合数据
   *
   * @param key   缓存键值
   * @param start 起始下标
   * @param end   结束下标
   * @return 缓存键值对应的数据
   */
  fun <T> getZSetByRange(key: String?, start: Long, end: Long): Set<T>? {
    if (key.isNullOrBlank()) {
      return emptySet()
    }
    return redisTemplate.boundZSetOps(key).range(start, end) as Set<T>?
  }

  /**
   * 获得下标为[start,end]的排序集合数据
   *
   * @param key   缓存键值
   * @param start 起始下标
   * @param end   结束下标
   * @return 缓存键值对应的数据
   */
  fun <T> getZSetByRangeWithScores(key: String?, start: Long, end: Long): Set<TypedTuple<T>>? {
    if (key.isNullOrBlank()) {
      return emptySet()
    }
    return redisTemplate.boundZSetOps(key).rangeWithScores(start, end) as Set<TypedTuple<T>>?
  }

  /**
   * 获得排序集合中指定value的排名
   *
   * @param key   缓存键值
   * @param value 值
   * @return 排名
   */
  fun getZSetRank(key: String?, value: Any): Long? {
    if (key.isNullOrBlank()) {
      return null
    }
    return redisTemplate.boundZSetOps(key).rank(value)
  }

  /**
   * 获得排序集合的大小
   *
   * @param key 缓存键值
   * @return 集合的大小
   */
  fun getZSetSize(key: String?): Long? {
    if (key.isNullOrBlank()) {
      return null
    }
    return redisTemplate.boundZSetOps(key).size()
  }

  /**
   * 删除[start,end]中排序集合的数据
   *
   * @param key   缓存键值
   * @param start 起始下标
   * @param end   结束下标
   */
  fun removeZSetByRange(key: String?, start: Long, end: Long) {
    if (key.isNullOrBlank()) {
      return
    }
    redisTemplate.boundZSetOps(key).removeRange(start, end)
  }

  /**
   * 删除排序集合中值在[min,max]中的数据
   *
   * @param key 缓存键值
   * @param min 最小值
   * @param max 最大值
   */
  fun removeZSetByScoreRange(key: String?, min: Double, max: Double) {
    if (key.isNullOrBlank()) {
      return
    }
    redisTemplate.boundZSetOps(key).removeRangeByScore(min, max)
  }

  /**
   * 缓存Map数据
   *
   * @param key     缓存键值
   * @param dataMap 缓存的数据
   */
  fun <T, V> setMap(key: String?, dataMap: Map<T, V>?) {
    if (key.isNullOrBlank() || dataMap == null) {
      return
    }
    redisTemplate.opsForHash<T, V>().putAll(key, dataMap)
  }

  /**
   * 向缓存Map中增加一条数据
   *
   * @param redisKey 缓存键值
   * @param mapKey   缓存的数据的键
   * @param mapValue 缓存的数据的值
   */
  fun <T, V> addMap(redisKey: String?, mapKey: T?, mapValue: V?) {
    if (redisKey.isNullOrBlank() || mapKey == null || mapValue == null) {
      return
    }
    redisTemplate.opsForHash<T, V>().put(redisKey, mapKey, mapValue)
  }

  fun <T, V> removeMap(redisKey: String?, vararg mapKeys: Any?) {
    if (redisKey.isNullOrBlank()) {
      return
    }
    redisTemplate.opsForHash<T, V>().delete(redisKey, *mapKeys)
  }

  /**
   * 获得缓存的Map数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getMap(key: String?): Map<String, T> {
    if (key.isNullOrBlank()) {
      return emptyMap()
    }
    return redisTemplate.opsForHash<String, T>().entries(key)
  }

  /**
   * 获得缓存的基本对象列表
   *
   * @param pattern 字符串前缀
   * @return 对象列表
   */
  fun keys(pattern: String): Collection<String> {
    return redisTemplate.keys(pattern)
  }

  /**
   * 判断键值和数据是否为空
   *
   * @param key   键值
   * @param value 数据
   * @param <T>   数据类型
   * @return 有一为空返回true，否则返回false
  </T> */
  private fun <T> isKeyValueNull(key: String?, value: T?): Boolean {
    return key.isNullOrBlank() || value == null
  }

  /**
   * 判断键值和集合数据是否为空
   *
   * @param key        键值
   * @param collection 集合数据
   * @return 有一为空返回true，否则返回false
   */
  private fun isKeyCollectionNull(key: String?, collection: Collection<*>?): Boolean {
    return key.isNullOrBlank() || collection.isNullOrEmpty()
  }
}