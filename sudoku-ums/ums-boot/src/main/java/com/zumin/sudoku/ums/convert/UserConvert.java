package com.zumin.sudoku.ums.convert;

import com.zumin.sudoku.ums.pojo.entity.SysUser;
import com.zumin.sudoku.ums.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserConvert {

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  UserVO convert(SysUser user);
}
