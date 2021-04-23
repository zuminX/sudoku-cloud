package com.zumin.sudoku.admin.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.admin.mapper.SysRoleMapper;
import com.zumin.sudoku.admin.pojo.entity.SysRole;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

  public List<String> listNameByUserId(Long id) {
    return baseMapper.selectNameByUserId(id);
  }
}
