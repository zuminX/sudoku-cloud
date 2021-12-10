package com.zumin.sudoku.auth.service

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import org.springframework.security.oauth2.provider.ClientDetails
import javax.sql.DataSource

/**
 * 实现获取客户端服务器的详情
 */
class JdbcClientDetailsServiceImpl(dataSource: DataSource?) : JdbcClientDetailsService(dataSource) {

  /**
   * 从数据库中加载客户端信息
   *
   * @param clientId 客户端ID
   * @return 客户端详情
   */
  override fun loadClientByClientId(clientId: String): ClientDetails {
    return super.loadClientByClientId(clientId)
  }
}