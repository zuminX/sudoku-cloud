package com.zumin.sudoku.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.auth.feign.AuthFeign;
import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import com.zumin.sudoku.common.core.constant.AuthParamName;
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
    params.put(AuthParamName.CLIENT_ID, AuthConstants.USER_CLIENT_ID);
    params.put(AuthParamName.CLIENT_SECRET, clientSecret);
    params.put(AuthParamName.GRANT_TYPE, "password");
    params.put(AuthParamName.USERNAME, loginBody.getUsername());
    params.put(AuthParamName.PASSWORD, loginBody.getPassword());
    params.put(AuthParamName.CAPTCHA_UUID, loginBody.getUuid());
    params.put(AuthParamName.CAPTCHA_CODE, loginBody.getCode());
    return authFeign.postAccessToken(params);
  }
}
