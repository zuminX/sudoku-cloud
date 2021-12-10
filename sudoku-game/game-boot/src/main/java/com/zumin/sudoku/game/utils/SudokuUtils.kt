package com.zumin.sudoku.game.utils

/**
 * 检查数独数据的合法性
 * 依次检查每个单元格的数字是否在1~9之间，及该数字在每行每列每块是否唯一
 *
 * @return 合法返回true，非法返回false
 */
fun List<List<Int>>.checkSudokuValidity(): Boolean {
  return (0 .. 8).none { i ->
    (0 .. 8).any { j -> this[i][j] < 1 || this[i][j] > 9 || !isOnly(i, j) }
  }
}

/**
 * 判断给定的数独题目是否只有唯一解
 *
 * @param holes  题目空缺数组
 * @return 若解只有一个则返回true，若存在多解或无解则返回false
 */
fun List<List<Int>>.hasOnlyOneSolution(holes: List<List<Boolean>>): Boolean {
  val topic = setVacancyGridToZero(holes).map { it.toIntArray() }.toTypedArray()
  return SudokuSolver(topic).solutionCount == 1
}

/**
 * 返回数给定独矩阵的副本，并将其空缺的格子的值设置为零
 *
 * @param holes  题目空缺数组
 * @return 空缺的格子为零的数独矩阵
 */
fun List<List<Int>>.setVacancyGridToZero(holes: List<List<Boolean>>): List<MutableList<Int>> {
  val cloneMatrix = map { it.toMutableList() }
  holes.forEachIndexed { i, rowList ->
    rowList.forEachIndexed { j, value ->
      if (value) {
        cloneMatrix[i][j] = 0
      }
    }
  }
  return cloneMatrix
}

/**
 * 判断给定的数独题目是否存在多解或无解
 *
 * @param holes  题目空缺数组
 * @return 若存在多解或无解则返回true，若解只有一个则返回false
 */
fun List<List<Int>>.hasNotOnlyOneSolution(holes: List<List<Boolean>>): Boolean {
  return !hasOnlyOneSolution(holes)
}

/**
 * 检查该数字在该数独中的行、列、块是否唯一
 *
 * @param i      行
 * @param j      列
 * @return 唯一返回true, 不唯一返回false
 */
fun List<List<Int>>.isOnly(i: Int, j: Int): Boolean {
  return this[i].checkRowIsOnly(j, 9) && checkColumnIsOnly(i, j, 9) && checkBlockIsOnly(i, j)
}

/**
 * 检查matrix[j]在该行中是否唯一
 *
 * @param j      列
 * @param size   检查个数
 * @return 唯一返回true, 不唯一返回false
 */
fun List<Int>.checkRowIsOnly(j: Int, size: Int): Boolean {
  return (0 until size).filter { it != j }.none { this[it] == this[j] }
}

/**
 * 检查matrix[i][j]在该列中是否唯一
 *
 * @param i      行
 * @param j      列
 * @param size   检查个数
 * @return 唯一返回true, 不唯一返回false
 */
fun List<List<Int>>.checkColumnIsOnly(i: Int, j: Int, size: Int): Boolean {
  return (0 until size).filter { it != i }.none { this[it][j] == this[i][j] }
}

/**
 * 检查matrix[i][j]在该区块中是否唯一
 *
 * @param i      行
 * @param j      列
 * @return 唯一返回true, 不唯一返回false
 */
fun List<List<Int>>.checkBlockIsOnly(i: Int, j: Int): Boolean {
  return ((i / 3) * 3 until ((i / 3) + 1) * 3).filter { it != i }.none { row ->
    ((j / 3) * 3 until ((j / 3) + 1) * 3).filter { it != j }.any { col -> this[row][col] == this[i][j] }
  }
}

/**
 * 将字符串还原为数独矩阵数组
 *
 * @return 数独矩阵数组
 */
fun String.unzipToMatrix(): List<List<Int>> {
  val result = List(9) { MutableList(9) { 0 } }
  forEachIndexed { i, c -> result[i / 9][i % 9] = c - '0' }
  return result
}

/**
 * 将字符串还原为题目空缺数组
 *
 * @return 题目空缺数组
 */
fun String.unzipToHoles(): List<List<Boolean>> {
  val result = List(9) { MutableList(9) { false } }
  forEachIndexed { i, c -> result[i / 9][i % 9] = c == '1' }
  return result
}

/**
 * 判断指定位置是否为空缺格子
 *
 * @param i     行
 * @param j     列
 * @return 是空缺格子返回true，否则返回false
 */
fun List<List<Boolean>>.isHole(i: Int, j: Int) = this[i][j]

/**
 * 判断指定位置是否不为空缺格子
 *
 * @param i     行
 * @param j     列
 * @return 不是空缺格子返回true，否则返回false
 */
fun List<List<Boolean>>.isNotHole(i: Int, j: Int) = !isHole(i, j)
