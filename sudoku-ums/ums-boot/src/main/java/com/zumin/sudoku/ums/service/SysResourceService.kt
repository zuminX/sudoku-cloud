package com.zumin.sudoku.ums.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.common.core.auth.AUTHORITY_PREFIX
import com.zumin.sudoku.common.core.auth.RESOURCE_ROLES_KEY
import com.zumin.sudoku.common.redis.RedisUtils
import com.zumin.sudoku.ums.mapper.SysResourceMapper
import com.zumin.sudoku.ums.pojo.SysResource
import org.springframework.stereotype.Service

@Service
class SysResourceService(
  private val redisUtils: RedisUtils,
) : ServiceImpl<SysResourceMapper, SysResource>() {

  /**
   * 刷新Redis中资源角色信息
   */
  fun refreshResourceRolesCache() {
    redisUtils.delete(RESOURCE_ROLES_KEY)
    val resources = baseMapper.listWithRoles()
    if (resources.isEmpty()) {
      redisUtils.setMap(RESOURCE_ROLES_KEY, emptyMap<String, String>())
      return
    }
    val resourceRolesMap = resources
      .filterNot { it.roleIds.isEmpty() }
      .associate {
        val roles = it.roleIds.map { roleId -> "$AUTHORITY_PREFIX$roleId" }.toSet()
        "${it.method}_${it.perms}" to roles
      }
    redisUtils.setMap(RESOURCE_ROLES_KEY, resourceRolesMap)
  }
}