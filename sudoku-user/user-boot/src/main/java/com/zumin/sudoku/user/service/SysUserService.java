package com.zumin.sudoku.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.auth.feign.AuthFeign;
import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.user.mapper.SysUserMapper;
import com.zumin.sudoku.user.pojo.body.LoginBody;
import com.zumin.sudoku.user.pojo.entity.SysUser;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

  private final AuthFeign authFeign;

  @Value("${auth.client-secret}")
  private String clientSecret;

  /**
   * 发送登录请求到统一认证服务中
   *
   * @param loginBody 登录信息
   * @return 登录的Token信息
   */
  public CommonResult<OAuth2TokenDTO> login(LoginBody loginBody) {
    Map<String, String> params = new HashMap<>();
    params.put("client_id", AuthConstants.USER_CLIENT_ID);
    params.put("client_secret", clientSecret);
    params.put("grant_type", "password");
    params.put("username", loginBody.getUsername());
    params.put("password", loginBody.getPassword());
    return authFeign.postAccessToken(params);
  }
}
