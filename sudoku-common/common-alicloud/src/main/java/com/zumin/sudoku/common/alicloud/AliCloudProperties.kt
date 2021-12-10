package com.zumin.sudoku.common.alicloud

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("alicloud")
data class AliCloudProperties(
  val accessKeyId: String,
  val secretKeySecret: String,
  val oss: OSSProperties,
) {
  data class OSSProperties(
    val endpoint: String,
    val bucketName: String,
    val policy: PolicyProperties,
  )

  data class PolicyProperties(
    val expire: Int = 0,
  )
}
