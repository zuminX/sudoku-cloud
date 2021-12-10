package com.zumin.sudoku.game.utils

private const val THRESHOLD = 324

/**
 * 数独游戏解决器
 */
class SudokuSolver(
  topic: Array<IntArray>,
  private val maxDepth: Int = 2,
) {
  private val solutions: MutableList<Array<IntArray>> = ArrayList()
  private val size = IntArray(1074)
  private val row = arrayOfNulls<DLXNode>(4146)
  private val col = arrayOfNulls<DLXNode>(1074)
  private val data = Array(9) { IntArray(9) }
  private lateinit var head: DLXNode

  // 数独题解个数
  val solutionCount: Int
    get() = solutions.size

  init {
    initDLX()
    initData(topic)
    solve(0)
  }

  /**
   * 初始化DLX节点
   */
  private fun initDLX() {
    val r = 730
    val c = 324
    head = DLXNode(r, c)
    head.R = head
    head.L = head.R
    head.D = head.L
    head.U = head.D
    for (i in 0 until c) {
      col[i] = DLXNode(r, i)
      col[i]!!.L = head
      col[i]!!.R = head.R
      col[i]!!.R.L = col[i]!!
      col[i]!!.L.R = col[i]!!.R.L
      col[i]!!.D = col[i]!!
      col[i]!!.U = col[i]!!.D
      size[i] = 0
    }
    for (i in r - 1 downTo 0) {
      row[i] = DLXNode(i, c)
      row[i]!!.U = head
      row[i]!!.D = head.D
      row[i]!!.D.U = row[i]!!
      row[i]!!.U.D = row[i]!!.D.U
      row[i]!!.R = row[i]!!
      row[i]!!.L = row[i]!!.R
    }
  }

  /**
   * 添加DLX节点
   *
   * @param r 行
   * @param c 列
   */
  private fun addNode(r: Int, c: Int) {
    val p = DLXNode(r, c)
    p.R = row[r]!!
    p.L = row[r]!!.L
    p.R.L = p
    p.L.R = p.R.L
    p.U = col[c]!!
    p.D = col[c]!!.D
    p.D.U = p
    p.U.D = p.D.U
    ++size[c]
  }

  private fun addNode(i: Int, j: Int, k: Int) {
    val r = (i * 9 + j) * 9 + k
    addNode(r, i * 9 + k - 1)
    addNode(r, 9 * 9 + j * 9 + k - 1)
    addNode(r, 2 * 9 * 9 + block(i, j) * 9 + k - 1)
    addNode(r, 3 * 9 * 9 + i * 9 + j)
  }

  private fun block(x: Int, y: Int) = x / 3 * 3 + y / 3

  private fun cover(c: Int) {
    if (c == THRESHOLD) {
      return
    }
    col[c]!!.delLR()
    var C = col[c]!!.D
    while (C !== col[c]) {
      if (C.c == THRESHOLD) {
        C = C.D
        continue
      }
      var R = C.L
      while (R !== C) {
        if (R.c == THRESHOLD) {
          R = R.L
          continue
        }
        --size[R.c]
        R.delUD()
        R = R.L
      }
      C.delLR()
      C = C.D
    }
  }

  private fun resume(c: Int) {
    if (c == THRESHOLD) {
      return
    }
    var C = col[c]!!.U
    while (C !== col[c]) {
      if (C.c == THRESHOLD) {
        C = C.U
        continue
      }
      C.resumeLR()
      var R = C.R
      while (R !== C) {
        if (R.c == THRESHOLD) {
          R = R.R
          continue
        }
        ++size[R.c]
        R.resumeUD()
        R = R.R
      }
      C = C.U
    }
    col[c]!!.resumeLR()
  }

  private fun solve(depth: Int): Boolean {
    if (head.L === head) {
      val solution = Array(9) { IntArray(9) }
      for (i in 0..8) {
        System.arraycopy(data[i], 0, solution[i], 0, 9)
      }
      solutions.add(solution)
      return solutions.size >= maxDepth
    }
    var minSize = 1 shl 30
    var c = -1
    var p = head.L
    while (p !== head) {
      if (size[p.c] < minSize) {
        minSize = size[p.c]
        c = p.c
      }
      p = p.L
    }
    cover(c)
    p = col[c]!!.D
    while (p !== col[c]) {
      p.R.L = p
      var cell = p.L
      while (cell !== p) {
        cover(cell.c)
        cell = cell.L
      }
      p.R.L = p.L
      val rr = p.r - 1
      data[rr / (9 * 9)][rr / 9 % 9] = rr % 9 + 1
      if (solve(depth + 1)) {
        return true
      }
      p.L.R = p
      cell = p.R
      while (cell !== p) {
        resume(cell.c)
        cell = cell.R
      }
      p.L.R = p.R
      p = p.D
    }
    resume(c)
    return false
  }

  private fun initData(topic: Array<IntArray>) {
    var i = 0
    while (i < 9) {
      var j = 0
      while (j < 9) {
        if (topic[i][j] > 0) {
          addNode(i, j, topic[i][j])
        } else {
          var k = 1
          while (k <= 9) {
            addNode(i, j, k)
            ++k
          }
        }
        ++j
      }
      ++i
    }
  }

  private class DLXNode(val r: Int, val c: Int) {
    lateinit var U: DLXNode
    lateinit var D: DLXNode
    lateinit var L: DLXNode
    lateinit var R: DLXNode
    fun delLR() {
      L.R = R
      R.L = L
    }

    fun delUD() {
      U.D = D
      D.U = U
    }

    fun resumeLR() {
      R.L = this
      L.R = R.L
    }

    fun resumeUD() {
      D.U = this
      U.D = D.U
    }
  }
}