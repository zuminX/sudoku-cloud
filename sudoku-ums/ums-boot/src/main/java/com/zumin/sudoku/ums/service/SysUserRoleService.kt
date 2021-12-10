package com.zumin.sudoku.ums.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.ums.mapper.SysUserRoleMapper
import com.zumin.sudoku.ums.pojo.SysUserRole
import org.springframework.stereotype.Service

@Service
class SysUserRoleService : ServiceImpl<SysUserRoleMapper, SysUserRole>()