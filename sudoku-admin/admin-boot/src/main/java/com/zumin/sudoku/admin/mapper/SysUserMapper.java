package com.zumin.sudoku.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.admin.pojo.entity.SysUser;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

  /**
   * 统计在[startCreateTime,endCreateTime)天中创建的用户数
   *
   * @param startDate 起始创建时间
   * @param endDate   终止创建时间
   * @return 用户数
   */
  Integer countNewUserByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startDate 起始登录时间
   * @param endDate   终止登录时间
   * @return 用户数
   */
  Integer countRecentLoginUserByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  SysUser selectWithRoleByUsername(@Param("username") String username);
}