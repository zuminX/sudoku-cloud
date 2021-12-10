package com.zumin.sudoku.ums.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.ums.mapper.SysRoleMapper
import com.zumin.sudoku.ums.pojo.SysRole
import org.springframework.stereotype.Service

@Service
class SysRoleService : ServiceImpl<SysRoleMapper, SysRole>() {

  fun listNameByUserId(id: Long): List<String> {
    return baseMapper.selectNameByUserId(id)
  }
}