package com.zumin.sudoku.common.alicloud.config;

import com.zumin.sudoku.common.core.template.EnvironmentProcessorTemplate;

/**
 * 加载阿里云相关的配置文件
 */
public class AliCloudEnvironmentProcessor extends EnvironmentProcessorTemplate {

  /**
   * 获取配置文件名
   *
   * @return 配置文件名数组
   */
  @Override
  public String[] getProfiles() {
    return new String[]{
        "alicloud.yml",
    };
  }

}