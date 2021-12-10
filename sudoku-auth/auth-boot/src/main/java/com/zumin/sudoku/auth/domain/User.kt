package com.zumin.sudoku.auth.domain

import com.zumin.sudoku.common.core.NoArg
import com.zumin.sudoku.ums.UserDTO
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * 登录用户信息
 */
@NoArg
data class User(
  var id: Long,
  private var username: String,
  private var password: String,
  var enabled: Boolean,
  var clientId: String?,
  private var authorities: Collection<SimpleGrantedAuthority>,
) : UserDetails {

  constructor(user: UserDTO) : this(user.id, user.username, user.password, user.enabled == 1, user.clientId,
    user.roleIds.map { SimpleGrantedAuthority(it.toString()) })

  override fun getAuthorities() = authorities

  override fun getPassword() = password

  override fun getUsername() = username

  override fun isAccountNonExpired() = true

  override fun isAccountNonLocked() = true

  override fun isCredentialsNonExpired() = true

  override fun isEnabled() = enabled

  companion object {
    private const val serialVersionUID = -3451116629449574759L
  }
}