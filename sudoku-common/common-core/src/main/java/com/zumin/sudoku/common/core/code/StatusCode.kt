package com.zumin.sudoku.common.core.code

interface StatusCode {

  fun getNumber(): Int

  fun getMessage(): String?

  fun getCode() = "${this.javaClass.simpleName.removeSuffix("StatusCode")}-${getNumber()}"
}