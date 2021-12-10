package com.zumin.sudoku.auth.controller

import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.CommonResult.Companion.success
import com.zumin.sudoku.common.core.auth.JWT_EXP
import com.zumin.sudoku.common.core.auth.JWT_JTI
import com.zumin.sudoku.common.core.auth.TOKEN_BLACKLIST_PREFIX
import com.zumin.sudoku.common.redis.RedisUtils
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.utils.getJwtPayload
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.DeleteMapping
import java.util.concurrent.TimeUnit

@ComRestController(path = ["/oauth"], tags = ["注销"])
class LogoutController(private val redisUtils: RedisUtils) {

  @ApiOperation("登出")
  @DeleteMapping("/logout")
  fun logout(): CommonResult<*> {
    val jsonObject = getJwtPayload()
    // JWT唯一标识
    val jti = jsonObject.getStr(JWT_JTI)
    // JWT过期时间戳
    val exp = jsonObject.getLong(JWT_EXP)
    val currentTimeSeconds = System.currentTimeMillis() / 1000
    // token已过期，无需加入黑名单
    if (exp >= currentTimeSeconds) {
      redisUtils.set("$TOKEN_BLACKLIST_PREFIX$jti", "", exp - currentTimeSeconds, TimeUnit.SECONDS)
    }
    return success<Any>()
  }
}