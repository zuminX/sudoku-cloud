package com.zumin.sudoku.ums.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.ums.pojo.SysUser
import java.time.LocalDate
import com.zumin.sudoku.ums.pojo.SearchUserBody
import com.zumin.sudoku.ums.pojo.ModifyUserBody
import com.zumin.sudoku.ums.pojo.SysUserRole
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SysUserRoleMapper : BaseMapper<SysUserRole>