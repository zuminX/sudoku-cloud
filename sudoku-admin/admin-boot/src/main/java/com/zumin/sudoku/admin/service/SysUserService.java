package com.zumin.sudoku.admin.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.admin.convert.UserConvert;
import com.zumin.sudoku.admin.enums.AdminStatusCode;
import com.zumin.sudoku.admin.exception.UserException;
import com.zumin.sudoku.admin.mapper.SysUserMapper;
import com.zumin.sudoku.admin.pojo.body.AddUserBody;
import com.zumin.sudoku.admin.pojo.body.ModifyUserBody;
import com.zumin.sudoku.admin.pojo.body.SearchUserBody;
import com.zumin.sudoku.admin.pojo.entity.SysRole;
import com.zumin.sudoku.admin.pojo.entity.SysUser;
import com.zumin.sudoku.admin.pojo.entity.SysUserRole;
import com.zumin.sudoku.admin.pojo.vo.UserDetailVO;
import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.mybatis.page.PageParam;
import com.zumin.sudoku.common.mybatis.utils.PageUtils;
import com.zumin.sudoku.common.web.log.BusinessType;
import com.zumin.sudoku.common.web.log.Log;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

  private final SysRoleService roleService;
  private final SysUserRoleService userRoleService;
  private final UserConvert userConvert;

  /**
   * 根据用户名查询对应的系统用户，并带上该用户对应的角色
   *
   * @param username 用户名
   * @return 系统用户
   */
  public SysUser getUserWithRoleByUsername(String username) {
    return baseMapper.selectWithRoleByUsername(username);
  }

  /**
   * 获取系统用户列表
   *
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> getUserList() {
    return PageUtils.getPage(new PageParam<>(baseMapper::selectAllWithRole), userConvert::entityToDetailVO);
  }

  /**
   * 修改用户信息
   *
   * @param modifyUserBody 修改的用户信息
   */
  @Transactional
  @Log(value = "修改用户", businessType = BusinessType.UPDATE)
  public void modifyUser(ModifyUserBody modifyUserBody) {
    checkRoleName(modifyUserBody);
    checkReUsername(modifyUserBody.getUsername(), modifyUserBody.getId());
    baseMapper.updateModifyById(modifyUserBody);
    updateRoleIdByUserId(modifyUserBody.getId(), modifyUserBody.getRoleNameList());
  }

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  @Transactional
  @Log(value = "新增用户", isSaveParameterData = false)
  public void addUser(AddUserBody addUserBody) {
    checkUsername(addUserBody.getUsername());
    SysUser user = userConvert.addBodyToEntity(addUserBody);
    baseMapper.insert(user);
    insertUserRole(user.getId(), addUserBody.getRoleNameList());
  }

  /**
   * 根据条件搜索用户
   *
   * @param searchUserBody 搜索用户的条件
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUser(SearchUserBody searchUserBody) {
    searchUserBody.setUsername(StrUtil.trim(searchUserBody.getUsername()));
    searchUserBody.setNickname(StrUtil.trim(searchUserBody.getNickname()));
    return PageUtils.getPage(new PageParam<>(() -> baseMapper.selectByConditionWithRole(searchUserBody)), userConvert::entityToDetailVO);
  }

  /**
   * 根据用户名或昵称搜索用户
   *
   * @param name 名称
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUserByName(String name) {
    return PageUtils.getPage(new PageParam<>(() -> baseMapper.selectByNameWithRole(name)), userConvert::entityToDetailVO);
  }

  /**
   * 更新用户的角色
   *
   * @param userId       用户ID
   * @param roleNameList 角色名列表
   */
  private void updateRoleIdByUserId(Long userId, List<String> roleNameList) {
    List<SysUserRole> userRoleList = roleService.list(Wrappers.lambdaQuery(SysRole.class).select(SysRole::getId).in(SysRole::getName, roleNameList))
        .stream()
        .map(role -> new SysUserRole(userId, role.getId()))
        .collect(Collectors.toList());
    userRoleService.remove(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
    userRoleService.saveBatch(userRoleList);
  }

  /**
   * 检查待修改的用户的角色名列表是否为空或管理员。若是，则抛出用户异常
   *
   * @param modifyUserBody 修改的用户对象
   */
  private void checkRoleName(ModifyUserBody modifyUserBody) {
    List<String> roleNameList = roleService.listNameByUserId(modifyUserBody.getId());
    if (CollUtil.isEmpty(roleNameList)) {
      throw new UserException(AdminStatusCode.USER_NOT_FOUND);
    }
    if (SecurityUtils.hasAdmin(roleNameList)) {
      throw new UserException(AdminStatusCode.USER_NOT_MODIFY_AUTHORITY);
    }
  }

  /**
   * 检查指定的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   */
  private void checkUsername(String username) {
    if (getUserByUsername(username) != null) {
      throw new UserException(AdminStatusCode.USER_HAS_EQUAL_NAME);
    }
  }

  /**
   * 检查重新设置的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   * @param userId   用户ID
   */
  private void checkReUsername(String username, Long userId) {
    SysUser user = getUserByUsername(username);
    if (user != null && !user.getId().equals(userId)) {
      throw new UserException(AdminStatusCode.USER_HAS_EQUAL_NAME);
    }
  }

  /**
   * 根据用户名查找对应的用户
   *
   * @param username 用户名
   * @return 用户
   */
  private SysUser getUserByUsername(String username) {
    return baseMapper.selectOne(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username));
  }

  /**
   * 插入用户角色
   *
   * @param userId       用户ID
   * @param roleNameList 角色名列表
   */
  private void insertUserRole(Long userId, List<String> roleNameList) {
    List<SysRole> roleList = roleService.list(Wrappers.lambdaQuery(SysRole.class).in(SysRole::getName, roleNameList));
    List<SysUserRole> userRoles = roleList.stream()
        .map(role -> new SysUserRole(userId, role.getId()))
        .collect(Collectors.toList());
    userRoleService.saveBatch(userRoles);
  }

}
