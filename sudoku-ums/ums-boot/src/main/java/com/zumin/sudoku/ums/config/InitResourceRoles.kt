package com.zumin.sudoku.ums.config

import com.zumin.sudoku.ums.service.SysResourceService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * 初始化资源角色信息
 */
@Component
class InitResourceRoles(
  private val sysResourceService: SysResourceService,
) : CommandLineRunner {
  /**
   * 容器启动完成时加载角色权限规则至Redis缓存
   *
   * @param args 参数
   */
  override fun run(vararg args: String) {
    sysResourceService.refreshResourceRolesCache()
  }
}