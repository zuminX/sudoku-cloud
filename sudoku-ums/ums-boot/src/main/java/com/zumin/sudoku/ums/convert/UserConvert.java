package com.zumin.sudoku.ums.convert;


import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.ums.dto.UserDTO;
import com.zumin.sudoku.ums.pojo.body.AddUserBody;
import com.zumin.sudoku.ums.pojo.entity.SysRole;
import com.zumin.sudoku.ums.pojo.entity.SysUser;
import com.zumin.sudoku.ums.pojo.vo.UserDetailVO;
import com.zumin.sudoku.ums.pojo.vo.UserVO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = SecurityUtils.class)
public interface UserConvert {

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  UserVO convert(SysUser user);

  /**
   * 从实体转换为传输层
   *
   * @param user 系统用户
   * @return 传输层对象
   */
  @Mapping(target = "roleIds", source = "roleList")
  @Mapping(target = "clientId", ignore = true)
  UserDTO entityToDTO(SysUser user);

  /**
   * 将用户表对应的对象转换为用户详情显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户详情显示层对象
   */
  UserDetailVO entityToDetailVO(SysUser user);

  /**
   * 将新增用户对象转化为用户表对应的对象
   *
   * @param addUserBody 新增用户对象
   * @return 用户表对应的对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "avatar", ignore = true)
  @Mapping(target = "roleList", ignore = true)
  @Mapping(target = "password", expression = "java(SecurityUtils.encodePassword(addUserBody.getPassword()))")
  SysUser addBodyToEntity(AddUserBody addUserBody);

  /**
   * 将角色列表转换为角色ID列表
   *
   * @param roleList 角色列表
   * @return 角色ID列表
   */
  default List<Long> roleListToRoleIdList(List<SysRole> roleList) {
    return roleList.stream().map(SysRole::getId).collect(Collectors.toList());
  }
}
