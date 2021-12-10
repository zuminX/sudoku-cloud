package com.zumin.sudoku.common.web.handler

import com.zumin.sudoku.common.web.domain.CustomEditorInfo
import org.springframework.web.bind.WebDataBinder
import java.util.function.Consumer

/**
 * 数据绑定处理器接口
 */
interface DataBindingHandler {
  /**
   * 初始化空绑定信息
   *
   * @param binder 绑定器对象
   */
  fun initBinder(binder: WebDataBinder)

  /**
   * 初始化绑定信息
   *
   * @param binder               绑定器对象
   * @param customEditorInfoList 自定义编辑信息对象列表
   */
  fun initBinder(binder: WebDataBinder, customEditorInfoList: List<CustomEditorInfo<*>>) {
    customEditorInfoList.forEach { binder.registerCustomEditor(it.requiredType, it.propertyEditor) }
  }
}