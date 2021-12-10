package com.zumin.sudoku.common.web.log

import cn.hutool.core.util.StrUtil
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 处理日志切面类
 */
@Aspect
@Component
class LogAspect {
  /**
   * 配置织入点 处理所有带有Log注解的方法
   */
  @Pointcut("@annotation(com.zumin.sudoku.common.web.log.Log)")
  fun logPointCut() = Unit

  /**
   * 环绕处理方法
   *
   * @param point 连接点
   * @return 方法执行后的返回值
   * @throws Throwable 异常
   */
  @Around("logPointCut()")
  fun around(point: ProceedingJoinPoint): Any {
    val startTime = System.currentTimeMillis()
    return try {
      val result = point.proceed()
      handleSuccessLog(point, startTime)
      result
    } catch (throwable: Throwable) {
      handleErrorLog(point, startTime, throwable)
      throw throwable
    }
  }

  /**
   * 处理执行成功的日志
   *
   * @param joinPoint 连接点
   * @param startTime 开始时间
   */
  private fun handleSuccessLog(joinPoint: JoinPoint, startTime: Long) {
    val log = getAnnotationLog(joinPoint) ?: return
    val spendTime = computeSpendTime(startTime)
    val logContent = buildSuccessLogContent(log, joinPoint.args, spendTime)
    recordSuccessLog(logContent, spendTime)
  }

  /**
   * 处理执行失败的日志
   *
   * @param joinPoint 连接点
   * @param startTime 开始时间
   * @param throwable 异常
   */
  private fun handleErrorLog(joinPoint: JoinPoint, startTime: Long, throwable: Throwable) {
    val log = getAnnotationLog(joinPoint) ?: return
    val spendTime = computeSpendTime(startTime)
    val logContent = buildErrorLogContent(log, joinPoint.args, spendTime, throwable)
    recordErrorLog(logContent)
  }

  /**
   * 记录执行成功的日志 根据方法的执行时间，设置日志的级别
   *
   * @param logContent 日志内容
   * @param spendTime  方法执行时间
   */
  private fun recordSuccessLog(logContent: String, spendTime: Long) {
    if (spendTime < 1000) {
      logger.info(logContent)
      return
    }
    if (spendTime < 5000) {
      logger.warn(logContent)
      return
    }
    recordErrorLog(logContent)
  }

  /**
   * 记录执行失败的日志
   *
   * @param logContent 日志内容
   */
  private fun recordErrorLog(logContent: String) {
    logger.error(logContent)
  }

  /**
   * 构建执行成功的日志内容
   *
   * @param log         日志对象
   * @param paramsArray 参数数组
   * @param spendTime   方法执行时间
   * @return 日志内容
   */
  private fun buildSuccessLogContent(log: Log, paramsArray: Array<Any>, spendTime: Long): String {
    val result = StringBuilder()
    result.append(toLogString(log.value))
    result.append(toLogString(log.businessType))
    result.append("[共计用时: $spendTime ms]")
    if (log.isSaveParameterData) {
      result.append(StrUtil.sub(paramDataToLogString(paramsArray), 0, 1000))
    }
    return result.toString()
  }

  /**
   * 构建执行失败的日志内容
   *
   * @param log         日志对象
   * @param paramsArray 参数数组
   * @param spendTime   方法执行时间
   * @param throwable   异常
   * @return 日志内容
   */
  private fun buildErrorLogContent(log: Log, paramsArray: Array<Any>, spendTime: Long, throwable: Throwable): String {
    return buildSuccessLogContent(log, paramsArray, spendTime) + Arrays.toString(throwable.stackTrace)
  }

  /**
   * 计算方法执行时间
   *
   * @param startTime 开始时间
   * @return 方法执行时间
   */
  private fun computeSpendTime(startTime: Long): Long {
    return System.currentTimeMillis() - startTime
  }

  /**
   * 拼接参数数据
   */
  private fun paramDataToLogString(paramsData: Array<Any>): String {
    return paramsData.filter(this::notFilterObject).joinToString(transform = this::toLogString)
  }

  /**
   * 利用基础日志模板进行格式化字符串
   *
   * @param data 数据
   * @return 格式化后的字符串
   */
  private fun toLogString(data: Any): String {
    return "[$data]"
  }

  /**
   * 判断是否不是过滤该对象
   *
   * @param o 对象
   * @return 需要过滤返回false，否则返回true
   */
  private fun notFilterObject(o: Any): Boolean {
    return o !is MultipartFile && o !is HttpServletRequest && o !is HttpServletResponse
  }

  /**
   * 获取日志注解 如果存在就获取
   */
  private fun getAnnotationLog(joinPoint: JoinPoint): Log? {
    val methodSignature = joinPoint.signature as MethodSignature
    val method = methodSignature.method
    return method?.getAnnotation(Log::class.java)
  }
}

private val logger = KotlinLogging.logger { }