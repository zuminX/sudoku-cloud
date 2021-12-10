package com.zumin.sudoku.common.core.auth


/**
 * 黑名单token前缀
 */
const val TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:"

/**
 * 验证码前缀
 */
const val CAPTCHA_PREFIX = "auth:captcha:"

/**
 * Redis缓存权限规则key
 */
const val RESOURCE_ROLES_KEY = "auth:resource:roles"