package com.zumin.sudoku.ums.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.ums.pojo.ModifyUserBody
import com.zumin.sudoku.ums.pojo.SearchUserBody
import com.zumin.sudoku.ums.pojo.SysUser
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDate

@Mapper
interface SysUserMapper : BaseMapper<SysUser> {
  fun selectWithRoleById(@Param("userId") userId: Long): SysUser

  /**
   * 统计在[startCreateTime,endCreateTime)天中创建的用户数
   *
   * @param startDate 起始创建时间
   * @param endDate   终止创建时间
   * @return 用户数
   */
  fun countNewUserByDateBetween(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): Int

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startDate 起始登录时间
   * @param endDate   终止登录时间
   * @return 用户数
   */
  fun countRecentLoginUserByDateBetween(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): Int

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  fun selectWithRoleByUsername(@Param("username") username: String): SysUser

  /**
   * 查找系统所有用户，且用户带有角色信息
   *
   * @return 用户列表
   */
  fun selectAllWithRole(): List<SysUser>

  /**
   * 查找系统符合条件的用户，且用户带有角色信息
   *
   * @return 用户列表
   */
  fun selectByConditionWithRole(@Param("user") user: SearchUserBody): List<SysUser>

  /**
   * 查找系统中用户名或昵称中包含指定名称的用户给，且用户带有角色信息
   *
   * @param name 名称
   * @return 用户列表
   */
  fun selectByNameWithRole(@Param("name") name: String): List<SysUser>

  /**
   * 根据ID更新用户
   *
   * @param user 用户修改对象
   * @return 更新的行数
   */
  fun updateModifyById(@Param("user") user: ModifyUserBody): Int
}