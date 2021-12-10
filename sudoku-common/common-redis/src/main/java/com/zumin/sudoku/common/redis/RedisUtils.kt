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
  fun has(key: String): Boolean {
    return redisTemplate.hasKey(key)
  }

  /**
   * 缓存对象
   *
   * @param key   缓存的键值
   * @param value 缓存的值
   */
  fun <T> set(key: String, value: T) {
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
    redisTemplate.opsForValue().set(key, value!!, timeout, timeUnit)
  }

  /**
   * 获得缓存对象
   *
   * @param key   缓存键值
   * @param clazz 数据类型
   * @return 缓存键值对应的数据
   */
  fun <T> get(key: String, clazz: Class<*> = Any::class.java): T? {
    val result = redisTemplate.opsForValue().get(key)
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
  fun delete(key: String) {
    redisTemplate.delete(key)
  }

  /**
   * 获得缓存对象，并删除该缓存键
   *
   * @param key   缓存键值
   * @param clazz 数据类型
   * @return 缓存键值对应的数据
   */
  fun <T> getAndDelete(key: String, clazz: Class<*> = Any::class.java): T? {
    val result = get<T>(key, clazz)
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
    redisTemplate.opsForList().leftPushAll(key, dataList)
  }

  /**
   * 获得缓存的列表对象
   *
   * @param key 缓存的键值
   * @return 缓存键值对应的数据
   */
  fun <T> getList(key: String): List<T?> {
    val listOperation = redisTemplate.opsForList() as ListOperations<String, T>
    val size = listOperation.size(key)!!
    if (size == 0L) {
      return emptyList()
    }
    return (0 until size).map { listOperation.index(key, it) }
  }

  /**
   * 缓存集合数据
   *
   * @param key     缓存键值
   * @param dataSet 缓存的数据
   */
  fun setSet(key: String, dataSet: Set<Any>) {
    val setOperation = redisTemplate.boundSetOps(key)
    dataSet.forEach { setOperation.add(it) }
  }

  /**
   * 获得缓存的集合数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getSet(key: String): Set<T> {
    val operation = redisTemplate.boundSetOps(key) as BoundSetOperations<String, T>
    return operation.members() as Set<T>
  }

  /**
   * 缓存排序集合数据
   *
   * @param key    缓存键值
   * @param tuples 值-分数对集合
   */
  fun setZSet(key: String, tuples: Set<TypedTuple<*>>) {
    delete(key)
    redisTemplate.boundZSetOps(key).add(tuples as MutableSet<TypedTuple<Any>>)
  }

  /**
   * 向排序集合增加数据
   *
   * @param key   缓存键值
   * @param value 值
   * @param score 分数
   */
  fun addZSet(key: String, value: Any, score: Double) {
    redisTemplate.boundZSetOps(key).add(value, score)
  }

  /**
   * 向排序集合增加数据
   *
   * @param key   缓存键值
   * @param tuple 值-分数对
   */
  fun addZSet(key: String, tuple: TypedTuple<*>) {
    redisTemplate.boundZSetOps(key).add(setOf(tuple) as MutableSet<TypedTuple<Any>>)
  }

  /**
   * 获得排序集合数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getZSet(key: String): Set<T?> {
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
  fun <T> getZSetByRange(key: String, start: Long, end: Long): Set<T> {
    return redisTemplate.boundZSetOps(key).range(start, end) as Set<T>
  }

  /**
   * 获得下标为[start,end]的排序集合数据
   *
   * @param key   缓存键值
   * @param start 起始下标
   * @param end   结束下标
   * @return 缓存键值对应的数据
   */
  fun <T> getZSetByRangeWithScores(key: String, start: Long, end: Long): Set<TypedTuple<T>> {
    return redisTemplate.boundZSetOps(key).rangeWithScores(start, end) as Set<TypedTuple<T>>
  }

  /**
   * 获得排序集合中指定value的排名
   *
   * @param key   缓存键值
   * @param value 值
   * @return 排名
   */
  fun getZSetRank(key: String, value: Any): Long? {
    return redisTemplate.boundZSetOps(key).rank(value)
  }

  /**
   * 获得排序集合的大小
   *
   * @param key 缓存键值
   * @return 集合的大小
   */
  fun getZSetSize(key: String): Long {
    return redisTemplate.boundZSetOps(key).size()!!
  }

  /**
   * 删除[start,end]中排序集合的数据
   *
   * @param key   缓存键值
   * @param start 起始下标
   * @param end   结束下标
   */
  fun removeZSetByRange(key: String, start: Long, end: Long) {
    redisTemplate.boundZSetOps(key).removeRange(start, end)
  }

  /**
   * 删除排序集合中值在[min,max]中的数据
   *
   * @param key 缓存键值
   * @param min 最小值
   * @param max 最大值
   */
  fun removeZSetByScoreRange(key: String, min: Double, max: Double) {
    redisTemplate.boundZSetOps(key).removeRangeByScore(min, max)
  }

  /**
   * 缓存Map数据
   *
   * @param key     缓存键值
   * @param dataMap 缓存的数据
   */
  fun <T, V> setMap(key: String, dataMap: Map<T, V>) {
    redisTemplate.opsForHash<T, V>().putAll(key, dataMap)
  }

  /**
   * 向缓存Map中增加一条数据
   *
   * @param redisKey 缓存键值
   * @param mapKey   缓存的数据的键
   * @param mapValue 缓存的数据的值
   */
  fun <T, V> addMap(redisKey: String, mapKey: T, mapValue: V) {
    redisTemplate.opsForHash<T, V>().put(redisKey, mapKey, mapValue)
  }

  fun <T, V> removeMap(redisKey: String, vararg mapKeys: Any) {
    redisTemplate.opsForHash<T, V>().delete(redisKey, *mapKeys)
  }

  /**
   * 获得缓存的Map数据
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  fun <T> getMap(key: String): Map<String, T> {
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
}