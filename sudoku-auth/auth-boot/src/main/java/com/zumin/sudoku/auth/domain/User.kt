package com.zumin.sudoku.auth.domain

import com.zumin.sudoku.ums.UserDTO
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * 登录用户信息
 */
data class User(
  var id: Long? = null,
  private var username: String? = null,
  private var password: String? = null,
  var enabled: Boolean? = null,
  var clientId: String? = null,
  private var authorities: Collection<SimpleGrantedAuthority>? = null,
) : UserDetails {

  constructor(user: UserDTO) : this() {
    id = user.id
    username = user.username
    password = user.password
    enabled = user.enabled == 1
    clientId = user.clientId
    if (!user.roleIds.isNullOrEmpty()) {
      authorities = user.roleIds!!.map { SimpleGrantedAuthority(it.toString()) }
    }
  }

  override fun getAuthorities() = authorities!!

  override fun getPassword() = password!!

  override fun getUsername() = username!!

  override fun isAccountNonExpired() = true

  override fun isAccountNonLocked() = true

  override fun isCredentialsNonExpired() = true

  override fun isEnabled() = enabled!!

  companion object {
    private const val serialVersionUID = -3451116629449574759L
  }
}