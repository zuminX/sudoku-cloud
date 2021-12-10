package com.zumin.sudoku.game.utils

import cn.hutool.core.util.RandomUtil
import com.zumin.sudoku.common.core.utils.shuffle
import com.zumin.sudoku.game.pojo.GameDataBO
import java.util.*

// 产生随机数组时的阈值
private const val MAX_CALL_RANDOM_ARRAY_TIMES = 220

/**
 * 生成数独终盘
 *
 * @param minEmpty 最小的空缺格子数
 * @param maxEmpty 最大的空缺格子数
 * @return 数独数据
 */
fun generateSudokuFinal(minEmpty: Int, maxEmpty: Int): GameDataBO {
  var sudokuData: GameDataBO
  do {
    sudokuData = GameDataBO()
    val (matrix, holes) = sudokuData
    matrix.generateMatrix()
    holes.digHolesByGameDifficulty(minEmpty, maxEmpty)
  } while (matrix.hasNotOnlyOneSolution(holes))
  return sudokuData
}

/**
 * 生成数独矩阵
 */
private fun List<MutableList<Int>>.generateMatrix() {
  //记录buildRandomArray()调用次数
  var currentTimes = 0
  var status = GenerateStatus.INIT_FIRST_ROW
  var row = 0
  var col = 0
  var tempRandomArray = mutableListOf<Int>()
  while (row != 9) {
    when (status) {
      GenerateStatus.FILL_GRID -> {
        if (currentTimes >= MAX_CALL_RANDOM_ARRAY_TIMES) {
          status = GenerateStatus.EMPTY_ALL
          continue
        }
        if (!isCandidateNmbFound(tempRandomArray, row, col)) {
          status = GenerateStatus.EMPTY_ROW
          continue
        }
        if (++col == 9) {
          row++
          status = GenerateStatus.FILL_ROW
        }
      }
      GenerateStatus.EMPTY_ROW -> {
        clearMatrixRow(row)
        status = GenerateStatus.FILL_ROW
      }
      GenerateStatus.FILL_ROW -> {
        tempRandomArray = buildRandomArray()
        currentTimes++
        col = 0
        status = GenerateStatus.FILL_GRID
      }
      GenerateStatus.INIT_FIRST_ROW -> {
        buildRandomArray().forEachIndexed { i, value -> this[0][i] = value }
        currentTimes = 1
        row = 0
        status = GenerateStatus.FILL_ROW
      }
      GenerateStatus.EMPTY_ALL -> {
        clearMatrix()
        status = GenerateStatus.INIT_FIRST_ROW
      }
    }
  }
}

/**
 * 生成数独矩阵算法的状态
 */
private enum class GenerateStatus {
  // 清空矩阵的所有数据
  EMPTY_ALL,

  // 清空矩阵的行数据
  EMPTY_ROW,

  // 填充矩阵的格子数据
  FILL_GRID,

  // 填充矩阵的行数据
  FILL_ROW,

  // 初始化矩阵的第一行数据
  INIT_FIRST_ROW
}

/**
 * 清空数独矩阵的行
 *
 * @param row    行
 */
private fun List<MutableList<Int>>.clearMatrixRow(row: Int) = this[row].fill(0)

/**
 * 清空数独矩阵
 */
private fun List<MutableList<Int>>.clearMatrix() = forEach { it.fill(0) }

/**
 * 尝试给数独矩阵的第row行第col列的数据赋值
 *
 * @param randomArray 随机数组
 * @param row         行
 * @param col         列
 * @return 能赋值返回true，不能赋值返回false
 */
private fun List<MutableList<Int>>.isCandidateNmbFound(randomArray: MutableList<Int>, row: Int, col: Int): Boolean {
  randomArray.filter { it != -1 }.forEachIndexed { i, random ->
    this[row][col] = random
    if (noConflict(row, col)) {
      randomArray[i] = -1;
      return true
    }
  }
  return false
}

/**
 * 判断该行、该列的数在数独矩阵中是否存在冲突
 *
 * @param row    行
 * @param col    列
 * @return 存在冲突返回false，不存在冲突返回true
 */
private fun List<List<Int>>.noConflict(row: Int, col: Int): Boolean {
  return this[row].checkRowIsOnly(col, col) && checkColumnIsOnly(row, col, row) && checkBlockIsOnly(row, col)
}

/**
 * 返回一个1-9的随机排列数组
 *
 * @return 打乱的数组
 */
private fun buildRandomArray(): MutableList<Int> {
  return (1..9).toMutableList().apply { shuffle() }
}

/**
 * 根据空缺格子数生成对应题目的空缺数组
 *
 * @param minEmpty 最小的空缺格子数
 * @param maxEmpty 最大的空缺格子数
 */
private fun List<MutableList<Boolean>>.digHolesByGameDifficulty(minEmpty: Int, maxEmpty: Int) {
  for (i in RandomUtil.randomInt(minEmpty, maxEmpty + 1) - 1 downTo 0) {
    this[i / 9][i % 9] = true
  }
  shuffle()
}
