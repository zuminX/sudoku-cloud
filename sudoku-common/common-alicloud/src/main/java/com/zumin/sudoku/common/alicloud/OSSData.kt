package com.zumin.sudoku.common.alicloud

import io.swagger.annotations.ApiModelProperty
import java.util.concurrent.TimeUnit

/**
 * 获取OSS上传文件授权返回结果
 */
data class OSSPolicy(
  @ApiModelProperty("访问身份验证中用到用户标识")
  var accessKeyId: String? = null,
  @ApiModelProperty("用户表单上传的策略,经过base64编码过的字符串")
  var policy: String? = null,
  @ApiModelProperty("对policy签名后的字符串")
  var signature: String? = null,
  @ApiModelProperty("上传文件夹路径前缀")
  var dir: String? = null,
  @ApiModelProperty("oss对外服务的访问域名")
  var host: String? = null,
  @ApiModelProperty("上传成功后的回调设置")
  var callback: String? = null,
)

/**
 * oss上传文件的回调参数
 */
data class OSSCallbackParameter<T>(
  // 回调路径
  var callbackPath: String? = null,
  // 回调数据
  var data: T? = null,
  // 回调数据的过期时间单位
  val timeUnit: TimeUnit? = TimeUnit.MINUTES,
  // 回调数据的过期时间
  var timeout: Long? = 10,
)

/**
 * oss上传文件的回调结果
 */
data class OSSCallbackResult<T>(
  @ApiModelProperty("回调数据")
  var callbackData: T? = null,
  @ApiModelProperty("文件地址")
  var filePath: String? = null,
  @ApiModelProperty("文件大小")
  var size: String? = null,
  @ApiModelProperty("文件的mimeType")
  var mimeType: String? = null,
)