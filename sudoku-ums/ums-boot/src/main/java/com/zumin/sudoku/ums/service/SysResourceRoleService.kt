package com.zumin.sudoku.ums.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.ums.mapper.SysResourceRoleMapper
import com.zumin.sudoku.ums.pojo.SysResourceRole
import org.springframework.stereotype.Service

@Service
class SysResourceRoleService : ServiceImpl<SysResourceRoleMapper, SysResourceRole>()