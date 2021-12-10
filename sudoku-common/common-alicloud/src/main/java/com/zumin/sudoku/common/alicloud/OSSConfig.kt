package com.zumin.sudoku.common.alicloud

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * OSS配置类
 */
@Configuration
@EnableConfigurationProperties(AliCloudProperties::class)
class OSSConfig(val aliCloudProperties: AliCloudProperties) {

  /**
   * 实例化一个OSS对象到容器中
   *
   * @return OSS对象
   */
  @Bean
  fun oss(): OSS {
    val (accessKeyId, secretKeySecret, oss) = aliCloudProperties
    return OSSClientBuilder().build(oss.endpoint, accessKeyId, secretKeySecret)
  }
}