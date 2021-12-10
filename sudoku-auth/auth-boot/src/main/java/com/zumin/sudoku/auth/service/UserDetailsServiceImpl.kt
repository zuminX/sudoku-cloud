package com.zumin.sudoku.auth.service

import com.zumin.sudoku.auth.domain.User
import com.zumin.sudoku.common.core.auth.USER_CLIENT_ID
import com.zumin.sudoku.common.core.code.AuthStatusCode
import com.zumin.sudoku.common.web.getAuthClientId
import com.zumin.sudoku.ums.UserFeign
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * 自定义用户认证和授权
 */
@Service
class UserDetailsServiceImpl(private val userFeign: UserFeign) : UserDetailsService {

  /**
   * 实现根据用户名查找对应的用户详情
   *
   * @param username 用户名
   * @return 用户详情信息
   * @throws UsernameNotFoundException 用户名不存在异常
   */
  override fun loadUserByUsername(username: String): UserDetails {
    val clientId = getAuthClientId()
    val user: User = when (clientId) {
      USER_CLIENT_ID -> {
        val userRes = userFeign.getUserByUsername(username)
        if (AuthStatusCode.USER_NOT_EXIST.getCode() == userRes.code) {
          throw UsernameNotFoundException(AuthStatusCode.USER_NOT_EXIST.getMessage())
        }
        User(userRes.data!!.copy(clientId = clientId))
      }
      else -> throw UnsupportedOperationException("不存在该平台")
    }
    checkUserStatus(user)
    return user
  }

  /**
   * 检验用户当前状态
   *
   * @param user 用户
   */
  private fun checkUserStatus(user: User) {
    when {
      !user.isEnabled -> throw DisabledException("该账户已被禁用!")
      !user.isAccountNonLocked -> throw LockedException("该账号已被锁定!")
      !user.isAccountNonExpired -> throw AccountExpiredException("该账号已过期!")
    }
  }
}