package com.zumin.sudoku.common.web.handler

import com.zumin.sudoku.common.web.domain.CustomEditorInfo
import com.zumin.sudoku.common.web.enums.StatisticsDate
import com.zumin.sudoku.common.web.enums.toStatisticsDate
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder

/**
 * 全局数据绑定处理器类
 */
@ControllerAdvice(basePackages = ["com.zumin.sudoku"])
@ConditionalOnExpression("#{!'false'.equals(environment['common.web.data-binding'])}")
class GlobalDataBindingHandler : DataBindingHandler {
  /**
   * 初始化空的绑定信息
   *
   * @param binder 绑定器对象
   */
  @InitBinder
  override fun initBinder(binder: WebDataBinder) {
    initBinder(binder, listOf(statisticsDateEditor))
  }

  /**
   * 获取统计日期的自定义编辑信息对象
   *
   * @return 统计日期的自定义编辑信息对象
   */
  private val statisticsDateEditor: CustomEditorInfo<StatisticsDate>
    get() = CustomEditorInfo(StatisticsDate::class.java, String::toStatisticsDate)
}