package com.zumin.sudoku.common.core

import com.zumin.sudoku.common.core.code.CommonStatusCode
import com.zumin.sudoku.common.core.code.StatusCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("公共结果类")
class CommonResult<T> : Serializable {
  @ApiModelProperty("是否成功")
  var success = false

  @ApiModelProperty("状态码")
  var code: String? = null

  @ApiModelProperty("消息")
  var message: String? = null

  @ApiModelProperty("数据")
  var data: T? = null

  companion object {
    private const val serialVersionUID = 8729965664919809866L

    /**
     * 请求出错
     *
     * @param statusCode 状态编码对象
     * @param <T>        数据类型
     * @return 经过包装的响应对象
     */
    @JvmStatic
    fun <T> error(statusCode: StatusCode): CommonResult<T> {
      return CommonResult<T>().apply {
        success = false
        code = statusCode.getCode()
        message = statusCode.getMessage()
      }
    }

    /**
     * 请求出错
     *
     * @param statusCode 状态编码对象
     * @param <T>        数据类型
     * @param message    消息
     * @return 经过包装的响应对象
     */
    @JvmStatic
    fun <T> error(statusCode: StatusCode, message: String?): CommonResult<T> {
      return error<T>(statusCode).apply { this.message = message }
    }

    /**
     * 请求成功
     *
     * @param <T> 数据类型
     * @return 经过包装的响应对象
     */
    @JvmStatic
    fun <T> success(): CommonResult<T> {
      return CommonResult<T>().apply {
        success = true
        code = CommonStatusCode.OK.getCode()
      }
    }

    /**
     * 请求成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 经过包装的响应对象
     */
    @JvmStatic
    fun <T> success(data: T): CommonResult<T> {
      return success<T>().apply { this.data = data }
    }

    /**
     * 请求成功
     *
     * @param data    数据
     * @param <T>     数据类型
     * @param message 消息
     * @return 经过包装的响应对象
     */
    @JvmStatic
    fun <T> success(data: T, message: String?): CommonResult<T> {
      return success<T>(data).apply { this.message = message }
    }
  }
}