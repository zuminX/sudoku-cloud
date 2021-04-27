package com.zumin.sudoku.common.alicloud.pojo;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * oss上传文件的回调参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OSSCallbackParameter<T> {

  /**
   * 回调路径
   */
  private String callbackPath;

  /**
   * 回调数据
   */
  private T data;

  /**
   * 回调数据的过期时间单位
   */
  private TimeUnit timeUnit;

  /**
   * 回调数据的过期时间
   */
  private long timeout;
}
