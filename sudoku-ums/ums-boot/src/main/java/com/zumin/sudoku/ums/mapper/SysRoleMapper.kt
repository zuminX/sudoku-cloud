package com.zumin.sudoku.ums.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.ums.pojo.SysRole
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface SysRoleMapper : BaseMapper<SysRole> {
  fun selectNameByUserId(@Param("id") id: Long): List<String>
}