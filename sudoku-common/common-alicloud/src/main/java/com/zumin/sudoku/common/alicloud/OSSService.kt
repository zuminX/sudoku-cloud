package com.zumin.sudoku.common.alicloud

import cn.hutool.core.lang.UUID
import cn.hutool.json.JSONUtil
import com.aliyun.oss.OSS
import com.aliyun.oss.common.utils.BinaryUtil
import com.aliyun.oss.model.MatchMode
import com.aliyun.oss.model.PolicyConditions
import com.zumin.sudoku.common.core.utils.getFromRequest
import com.zumin.sudoku.common.core.utils.getSimpleDateStr
import com.zumin.sudoku.common.redis.RedisUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * OSS服务类
 */
@Component
@EnableConfigurationProperties(AliCloudProperties::class)
class OSSService(
  private val aliCloudProperties: AliCloudProperties,
  private val oss: OSS,
  private val redisUtils: RedisUtils,
) {
  @Value("\${system.addr}")
  private val addr: String = ""

  /**
   * 获取OSS上传凭证
   *
   * @param maxSize   文件的最大大小(以M为单位)
   * @param baseDir   存放的目录
   * @param parameter 回调参数
   * @return 上传凭证
   */
  fun policy(maxSize: Long, baseDir: String, parameter: OSSCallbackParameter<*>): OSSPolicy {
    val dir = getDateDir(baseDir)
    val postPolicy = oss.generatePostPolicy(expiration, getPolicyConditions(maxSize * 1048576, dir))
    return OSSPolicy(
      accessKeyId = aliCloudProperties.accessKeyId,
      policy = getPolicy(postPolicy),
      signature = oss.calculatePostSignature(postPolicy),
      dir = dir,
      host = host,
      callback = getCallbackData(parameter)
    )
  }

  /**
   * 从请求中解析回调结果
   *
   * @param clazz 数据类型
   * @return 上传的回调结果
   */
  fun <T> resolveCallbackData(clazz: Class<T>): OSSCallbackResult<T> {
    return OSSCallbackResult<T>(
      filePath = "$host/${"filename".getFromRequest("")}",
      size = "size".getFromRequest(""),
      mimeType = "mimeType".getFromRequest("")
    ).apply {
      val key = CALLBACK_DATA_PARAMETER_NAME.getFromRequest()
      if (key != null) {
        callbackData = redisUtils.getAndDelete(key, clazz)
      }
    }
  }

  /**
   * 获取上传策略
   *
   * @param postPolicy 策略
   * @return 编码后的上传策略
   */
  private fun getPolicy(postPolicy: String): String {
    val binaryData = postPolicy.toByteArray(StandardCharsets.UTF_8)
    return BinaryUtil.toBase64String(binaryData)
  }

  /**
   * 获取日期目录
   *
   * @param baseDir 基本目录
   * @return 日期目录
   */
  private fun getDateDir(baseDir: String): String {
    return "$baseDir/${getSimpleDateStr()}/"
  }

  /**
   * 获取凭证的过期时间
   *
   * @return 过期时间
   */
  private val expiration: Date
    get() = Date(System.currentTimeMillis() + aliCloudProperties.oss.policy.expire * 1000L)

  /**
   * 获取OSS上传主机
   *
   * @return 主机地址
   */
  private val host: String
    get() = "https://${aliCloudProperties.oss.bucketName}.${aliCloudProperties.oss.endpoint}"

  /**
   * 获取回调数据
   *
   * @param parameter 回调参数
   * @return 编码后的回调数据
   */
  private fun getCallbackData(parameter: OSSCallbackParameter<*>): String {
    val callbackData = CallbackData(
      callbackUrl = "$addr/${parameter.callbackPath}",
      callbackBody =
      "filename=\${object}&size=\${size}&mimeType=\${mimeType}&$CALLBACK_DATA_PARAMETER_NAME=${saveCallbackData(parameter)}",
      callbackBodyType = "application/x-www-form-urlencoded"
    )
    return BinaryUtil.toBase64String(JSONUtil.parse(callbackData).toString().toByteArray())
  }

  /**
   * 获取策略条件
   *
   * @param maxSize 文件的最大大小
   * @param dir     文件的目录
   * @return 策略条件
   */
  private fun getPolicyConditions(maxSize: Long, dir: String): PolicyConditions {
    return PolicyConditions().apply {
      addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize)
      addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir)
    }
  }

  private fun saveCallbackData(parameter: OSSCallbackParameter<*>): String {
    val (callbackPath, _, timeUnit, timeout) = parameter
    if (callbackPath == null) {
      return ""
    }
    val key = "$OSS_CALLBACK_DATA_PREFIX${UUID.fastUUID()}"
    redisUtils.set(key, callbackPath, timeout!!, timeUnit!!)
    return key
  }

  private data class CallbackData(
    var callbackUrl: String? = null,
    var callbackBody: String? = null,
    var callbackBodyType: String? = null,
  )
}

private const val CALLBACK_DATA_PARAMETER_NAME = "callback_key"

// OSS回调数据前缀
private const val OSS_CALLBACK_DATA_PREFIX = "oss:callback:data:"
