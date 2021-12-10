package com.zumin.sudoku.common.web.domain

import com.zumin.sudoku.common.web.exception.FormParameterConversionException
import org.springframework.beans.PropertyValuesEditor
import java.beans.PropertyEditor

/**
 * 自定义编辑信息类
 */
class CustomEditorInfo<T>(
  // 需要的类型
  val requiredType: Class<T>,
  // 将字符串转换为需要类型对象的转换器
  convert: (String) -> T?,
) {
  val propertyEditor: PropertyEditor

  init {
    propertyEditor = object : PropertyValuesEditor() {
      override fun setAsText(text: String) {
        val data: T = convert.invoke(text) ?: throw FormParameterConversionException(text, requiredType)
        value = data
      }
    }
  }
}