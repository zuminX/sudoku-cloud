package com.zumin.sudoku.ums.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.ums.pojo.SysResource
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SysResourceMapper : BaseMapper<SysResource> {
  fun listWithRoles(): List<SysResource>
}