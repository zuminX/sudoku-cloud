package com.zumin.sudoku.common.core.utils

import cn.hutool.core.util.RandomUtil

fun <T> List<MutableList<T>>.deepClone(): List<MutableList<T>> {
  return map { it.toMutableList() }.toList()
}

/**
 * 利用Knuth洗牌算法打乱二维数组
 */
fun <T> List<MutableList<T>>.shuffle() {
  val row = size
  val column = this[0].size
  var i = row * column - 1
  while (i-- > 0) {
    val random = RandomUtil.randomInt(0, i + 1)
    val temp = this[i / column][i % column]
    this[i / column][i % column] = this[random / column][random % column]
    this[random / column][random % column] = temp
  }
}

/**
 * 压缩二维int型数组为字符串
 *
 * @return 对应的字符串
 */
fun Array<IntArray>?.compression(): String {
  if (isNullOrEmpty()) {
    return ""
  }
  return toList().map { it.toList() }.compression()
}

/**
 * 压缩二维int型列表为字符串
 *
 * @return 对应的字符串
 */
fun List<List<Int?>>?.compression(): String {
  if (isNullOrEmpty()) {
    return ""
  }
  return flatten().joinToString(separator = "", transform = { i -> i?.toString() ?: "0" })
}

/**
 * 压缩二维boolean型数组为字符串
 *
 * @return 对应的字符串
 */
fun Array<BooleanArray>?.compression(): String {
  if (isNullOrEmpty()) {
    return ""
  }
  return toList().map { it.toList() }.compression()
}

/**
 * 压缩二维boolean型列表为字符串
 *
 * @return 对应的字符串
 */
@JvmName("compressionBoolean")
fun List<List<Boolean>>?.compression(): String {
  if (isNullOrEmpty()) {
    return ""
  }
  return flatten().joinToString(separator = "", transform = { b -> if (b) "1" else "0" })
}

