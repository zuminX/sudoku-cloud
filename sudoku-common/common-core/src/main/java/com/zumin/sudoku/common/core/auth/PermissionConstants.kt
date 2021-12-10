package com.zumin.sudoku.common.core.auth

/**
 * 管理员名
 */
const val ADMIN_NAME = "ROLE_ADMIN"

/**
 * 用户名
 */
const val USER_NAME = "ROLE_USER"

/**
 * 用户角色名
 */
val USER_ROLE_NAME = listOf(USER_NAME)

/**
 * 管理员角色名
 */
val ADMIN_ROLE_NAME = listOf(ADMIN_NAME, USER_NAME)
