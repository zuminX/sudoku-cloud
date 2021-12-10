package com.zumin.sudoku.ums.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.auth.OAuth2TokenDTO
import com.zumin.sudoku.auth.OAuthFeign
import com.zumin.sudoku.common.core.CommonResult
import com.zumin.sudoku.common.core.auth.*
import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.mybatis.page.PageParam
import com.zumin.sudoku.common.mybatis.page.getPageData
import com.zumin.sudoku.common.web.log.BusinessType
import com.zumin.sudoku.common.web.log.Log
import com.zumin.sudoku.common.web.utils.hasAdmin
import com.zumin.sudoku.ums.UmsStatusCode
import com.zumin.sudoku.ums.exception.UserException
import com.zumin.sudoku.ums.mapper.SysUserMapper
import com.zumin.sudoku.ums.pojo.*
import com.zumin.sudoku.ums.toSysUser
import com.zumin.sudoku.ums.toUserDetailVO
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@EnableConfigurationProperties(AuthProperties::class)
class SysUserService(
  private val oAuthFeign: OAuthFeign,
  private val roleService: SysRoleService,
  private val userRoleService: SysUserRoleService,
  private val authProperties: AuthProperties,
) : ServiceImpl<SysUserMapper, SysUser>() {

  /**
   * 根据用户名查询对应的系统用户，并带上该用户对应的角色
   *
   * @param username 用户名
   * @return 系统用户
   */
  fun getUserWithRoleByUsername(username: String): SysUser? {
    return baseMapper.selectWithRoleByUsername(username)
  }

  /**
   * 获取系统用户列表
   *
   * @return 用户详情的分页信息
   */
  fun getUserList(): Page<UserDetailVO> {
    return PageParam(baseMapper::selectAllWithRole).getPageData(SysUser::toUserDetailVO)
  }

  /**
   * 修改用户信息
   *
   * @param modifyUserBody 修改的用户信息
   */
  @Transactional
  @Log(value = "修改用户", businessType = BusinessType.UPDATE)
  fun modifyUser(modifyUserBody: ModifyUserBody) {
    /**
     * 检查待修改的用户的角色名列表是否为空或管理员。若是，则抛出用户异常
     */
    fun checkRoleName() {
      val roleNameList = roleService.listNameByUserId(modifyUserBody.id)
      if (roleNameList.isEmpty()) {
        throw UserException(UmsStatusCode.USER_NOT_FOUND)
      }
      if (roleNameList.hasAdmin()) {
        throw UserException(UmsStatusCode.USER_NOT_MODIFY_AUTHORITY)
      }
    }
    checkRoleName()
    /**
     * 检查重新设置的用户名在数据库中是否已经存在
     */
    fun checkReUsername() {
      val user = baseMapper.selectOne(KtQueryWrapper(SysUser()).eq(SysUser::username, modifyUserBody.username))
      if (user != null && user.id != modifyUserBody.id) {
        throw UserException(UmsStatusCode.USER_HAS_EQUAL_NAME)
      }
    }
    checkReUsername()
    baseMapper.updateModifyById(modifyUserBody)
    /**
     * 更新用户的角色
     */
    fun updateRoleIdByUserId() {
      val queryWrapper = KtQueryWrapper(SysRole()).select(SysRole::id).`in`(SysRole::name, modifyUserBody.roleNameList)
      val userRoleList = roleService.list(queryWrapper).map { SysUserRole(modifyUserBody.id, it.id!!) }
      userRoleService.remove(KtQueryWrapper(SysUserRole()).eq(SysUserRole::userId, modifyUserBody.id))
      userRoleService.saveBatch(userRoleList)
    }
    updateRoleIdByUserId()
  }

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  @Transactional
  @Log(value = "新增用户", isSaveParameterData = false)
  fun addUser(addUserBody: AddUserBody) {
    checkUsername(addUserBody.username)
    val user = addUserBody.toSysUser()
    baseMapper.insert(user)
    /**
     * 插入用户角色
     */
    fun insertUserRole() {
      val userRoles = roleService.list(KtQueryWrapper(SysRole()).`in`(SysRole::name, addUserBody.roleNameList))
        .map { SysUserRole(user.id!!, it.id!!) }
      userRoleService.saveBatch(userRoles)
    }
    insertUserRole()
  }

  /**
   * 注册用户
   *
   * @param registerUserBody 注册用户对象
   * @return 用户显示层对象
   */
  @Transactional
  @Log(value = "注册用户", isSaveParameterData = false)
  fun registerUser(registerUserBody: RegisterUserBody): SysUser {
    checkUsername(registerUserBody.username.trim())
    val user = registerUserBody.toSysUser()
    baseMapper.insert(user)
    /**
     * 插入用户角色
     *
     * @return 角色列表
     */
    fun insertUserRole(): List<SysRole> {
      val roleList = roleService.list(KtQueryWrapper(SysRole()).`in`(SysRole::name, USER_ROLE_NAME))
      val userRoles = roleList.map { SysUserRole(user.id!!, it.id!!) }
      userRoleService.saveBatch(userRoles)
      return roleList
    }
    user.roleList = insertUserRole()
    return user
  }

  /**
   * 检查指定的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   */
  private fun checkUsername(username: String) {
    if (baseMapper.selectOne(KtQueryWrapper(SysUser()).eq(SysUser::username, username)) != null) {
      throw UserException(UmsStatusCode.USER_HAS_EQUAL_NAME)
    }
  }

  /**
   * 根据条件搜索用户
   *
   * @param searchUserBody 搜索用户的条件
   * @return 用户详情的分页信息
   */
  fun searchUser(searchUserBody: SearchUserBody): Page<UserDetailVO> {
    val userBody = searchUserBody.copy(username = searchUserBody.username?.trim(), nickname = searchUserBody.nickname?.trim())
    return PageParam({ baseMapper.selectByConditionWithRole(userBody) }).getPageData(SysUser::toUserDetailVO)
  }

  /**
   * 根据用户名或昵称搜索用户
   *
   * @param name 名称
   * @return 用户详情的分页信息
   */
  fun searchUserByName(name: String): Page<UserDetailVO> {
    return PageParam({ baseMapper.selectByNameWithRole(name) }).getPageData(SysUser::toUserDetailVO)
  }

  /**
   * 发送登录请求到统一认证服务中
   *
   * @param loginBody 登录信息
   * @return 登录的Token信息
   */
  fun login(loginBody: LoginBody): CommonResult<OAuth2TokenDTO> {
    val (username, password, code, uuid) = loginBody
    val params = mapOf(
      AUTH_CLIENT_ID to USER_CLIENT_ID,
      AUTH_CLIENT_SECRET to authProperties.clientSecret,
      AUTH_GRANT_TYPE to AuthGrantType.PASSWORD.type,
      AUTH_USERNAME to username,
      AUTH_PASSWORD to password,
      AUTH_CAPTCHA_UUID to uuid,
      AUTH_CAPTCHA_CODE to code)
    return oAuthFeign.postAccessToken(params)
  }

  /**
   * 更新头像地址
   *
   * @param avatarPath 头像地址
   * @param userId     用户ID
   */
  fun updateAvatar(avatarPath : String, userId : Long) {
    update(KtUpdateWrapper(SysUser()).set(SysUser::avatar, avatarPath).eq(SysUser::id, userId))
  }

  /**
   * 根据用户ID查询对应的系统用户，并带上该用户对应的角色
   *
   * @param userId 用户ID
   * @return 系统用户
   */
  fun getUserWithRoleById(userId: Long): SysUser {
    return baseMapper.selectWithRoleById(userId)
  }

}