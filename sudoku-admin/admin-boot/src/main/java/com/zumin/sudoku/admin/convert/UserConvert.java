package com.zumin.sudoku.admin.convert;

import com.zumin.sudoku.admin.pojo.dto.UserDTO;
import com.zumin.sudoku.admin.pojo.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户类转换器
 */
@Mapper
public interface UserConvert {

  /**
   * 从实体转换为传输层
   *
   * @param sysUser 系统用户
   * @return 传输层对象
   */
  @Mapping(target = "clientId", ignore = true)
  UserDTO entityToDTO(SysUser sysUser);
}
