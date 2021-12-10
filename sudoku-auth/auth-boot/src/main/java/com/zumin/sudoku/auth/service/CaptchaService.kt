package com.zumin.sudoku.auth.service

import cn.hutool.captcha.CaptchaUtil
import cn.hutool.core.lang.UUID
import com.zumin.sudoku.auth.CaptchaVO
import com.zumin.sudoku.auth.config.CaptchaProperties
import com.zumin.sudoku.auth.exception.CaptchaException
import com.zumin.sudoku.common.core.auth.CAPTCHA_PREFIX
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.redis.RedisUtils
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * 验证码业务层类
 */
@Service
@EnableConfigurationProperties(CaptchaProperties::class)
class CaptchaService(
  private val redisUtils: RedisUtils,
  private val captchaProperties: CaptchaProperties,
) {

  /**
   * 生成验证码
   *
   * @return 验证码对象
   */
  fun generateCaptcha(): CaptchaVO {
    val (width, height, codeNumber, lineCount, expireTime) = captchaProperties
    val captcha = CaptchaUtil.createLineCaptcha(width, height, codeNumber, lineCount)
    val uuid = UUID.fastUUID().toString()
    redisUtils.set(getCaptchaKey(uuid), captcha.code, expireTime.toLong(), TimeUnit.MINUTES)
    return CaptchaVO(uuid, captcha.imageBase64)
  }

  /**
   * 检查验证码是否正确
   *
   * @param uuid 唯一标识
   * @param code 待验证的码
   */
  fun checkCaptcha(uuid: String, code: String?) {
    val key = getCaptchaKey(uuid)
    val captcha = redisUtils.get<String>(key) ?: throw CaptchaException(AuthStatusCode.CAPTCHA_EXPIRED)
    redisUtils.delete(key)
    if (!captcha.equals(code, ignoreCase = true)) {
      throw CaptchaException(AuthStatusCode.CAPTCHA_NOT_EQUALS)
    }
  }

  /**
   * 获取验证码的key值
   *
   * @return 验证码在redis中的key值
   */
  private fun getCaptchaKey(uuid: String): String {
    return CAPTCHA_PREFIX + uuid
  }
}