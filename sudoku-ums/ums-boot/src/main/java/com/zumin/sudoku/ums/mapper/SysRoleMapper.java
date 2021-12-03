package com.zumin.sudoku.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.ums.pojo.entity.SysRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
  List<String> selectNameByUserId(@Param("id") Long id);
}