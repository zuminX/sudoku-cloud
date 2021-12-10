package com.zumin.sudoku.common.web.utils

import cn.hutool.core.codec.Base64
import cn.hutool.extra.spring.SpringUtil
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.zumin.sudoku.common.core.auth.*
import com.zumin.sudoku.common.core.utils.getFromRequest
import com.zumin.sudoku.common.core.utils.request
import org.springframework.security.crypto.password.PasswordEncoder
import java.nio.charset.StandardCharsets

/**
 * 对密码进行加密
 *
 * @return 加密后的密码
 */
fun String.encodePassword(): String {
  return SpringUtil.getBean(PasswordEncoder::class.java).encode(this)
}

/**
 * 判断该角色名列表是否包含管理员
 *
 * @return 包含返回true，否则返回false
 */
fun List<String>.hasAdmin(): Boolean {
  return any { it == ADMIN_NAME }
}

/**
 * 获取JWT载荷
 *
 * @return JSON对象
 */
fun getJwtPayload(): JSONObject {
  val jwtPayload = request.getHeader(JWT_PAYLOAD_KEY)
  return JSONUtil.parseObj(jwtPayload)
}

/**
 * 获取当前用户的用户ID
 *
 * @return 用户ID
 */
fun getCurrentUserId(): Long {
  return getJwtPayload().getLong(AUTH_USER_ID)
}

/**
 * 获取当前用户的用户名
 *
 * @return 用户名
 */
fun getCurrentUsername(): String {
  return getJwtPayload().getStr(AUTH_USERNAME)
}

/**
 * 获取ClientId
 *
 * @return 客户端ID
 */
fun getClientId(): String? {
  return getJwtPayload().getStr(AUTH_CLIENT_ID)
}

/**
 * 获取当前用户的角色ID
 *
 * @return 角色ID
 */
fun getRoleIds(): Set<Long> {
  return getJwtPayload().get(JWT_AUTHORITIES_KEY, Set::class.java).map { it.toString().toLong() }.toSet()
}

/**
 * 获取登录认证的客户端ID
 *
 * @return 客户端ID
 */
fun getAuthClientId(): String? {
  val clientId = AUTH_CLIENT_ID.getFromRequest()
  if (!clientId.isNullOrBlank()) {
    return clientId
  }
  // 从请求头获取
  var basic = AUTHORIZATION_KEY.getFromRequest()
  if (basic.isNullOrBlank() || !basic.startsWith(BASIC_PREFIX)) {
    return null
  }
  basic = basic.replace(BASIC_PREFIX, "")
  val basicPlainText = String(Base64.decode(basic), StandardCharsets.UTF_8)
  return basicPlainText.split(":")[0]
}