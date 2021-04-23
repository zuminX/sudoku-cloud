package com.zumin.sudoku.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.ums.pojo.entity.SysResource;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {

  List<SysResource> listWithRoles();
}