package com.zumin.sudoku.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.admin.mapper.SysUserMapper;
import com.zumin.sudoku.admin.pojo.entity.SysUser;
import com.zumin.sudoku.admin.pojo.entity.SysUserRole;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

  private final SysUserRoleService sysUserRoleService;

  /**
   * 根据用户名查询对应的系统用户，并带上该用户对应的角色
   *
   * @param username 用户名
   * @return 系统用户
   */
  public SysUser getUserWithRoleByUsername(String username) {
    SysUser user = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    if (user == null) {
      return null;
    }
    List<Long> roleIds = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()))
        .stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
    user.setRoleIds(roleIds);
    return user;
  }

}
