package com.zumin.sudoku.common.core.utils

import cn.hutool.core.util.ClassUtil
import com.zumin.sudoku.common.core.CommonCoreProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 核心工具类
 */
@Component
@EnableConfigurationProperties(CommonCoreProperties::class)
class CoreUtils(
  private val commonCoreProperties: CommonCoreProperties,
) {
  /**
   * 获取项目的所有Class
   *
   * @return Class集合
   */
  val projectClasses: Set<Class<*>>
    get() = ClassUtil.scanPackage(commonCoreProperties.projectPackage)
}