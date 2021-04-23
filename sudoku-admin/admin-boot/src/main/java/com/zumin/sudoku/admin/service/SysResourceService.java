package com.zumin.sudoku.admin.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.admin.mapper.SysResourceMapper;
import com.zumin.sudoku.admin.pojo.entity.SysResource;
import com.zumin.sudoku.common.core.auth.AuthConstants;
import com.zumin.sudoku.common.redis.utils.RedisUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SysResourceService extends ServiceImpl<SysResourceMapper, SysResource> {

  private final RedisUtils redisUtils;

  /**
   * 刷新Redis中资源角色信息
   */
  public void refreshResourceRolesCache() {
    redisUtils.delete(AuthConstants.RESOURCE_ROLES_KEY);
    List<SysResource> resources = baseMapper.listWithRoles();
    if (resources == null) {
      redisUtils.setMap(AuthConstants.RESOURCE_ROLES_KEY, new HashMap<>());
      return;
    }
    Map<String, Set<String>> resourceRolesMap = new HashMap<>();
    // 转换 roleId -> ROLE_{roleId}
    resources.forEach(resource -> {
      Set<Long> roleIds = resource.getRoleIds();
      if (CollUtil.isEmpty(roleIds)) {
        return;
      }
      Set<String> roles = roleIds.stream()
          .map(roleId -> AuthConstants.AUTHORITY_PREFIX + roleId)
          .collect(Collectors.toSet());
      resourceRolesMap.put(resource.getMethod() + "_" + resource.getPerms(), roles);
    });
    redisUtils.setMap(AuthConstants.RESOURCE_ROLES_KEY, resourceRolesMap);
  }
}
