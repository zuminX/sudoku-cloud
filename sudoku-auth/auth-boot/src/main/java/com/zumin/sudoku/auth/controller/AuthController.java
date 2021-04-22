package com.zumin.sudoku.auth.controller;

import com.zumin.sudoku.auth.pojo.dto.OAuth2TokenDTO;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@ComRestController(path = "/oauth", tags = "认证中心")
public class AuthController {

  private final TokenEndpoint tokenEndpoint;

  @ApiOperation(value = "OAuth2认证", notes = "login")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
      @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
      @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
      @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
      @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
      @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),
      @ApiImplicitParam(name = "code", value = "小程序授权code"),
      @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据"),
      @ApiImplicitParam(name = "iv", value = "加密算法的初始向量"),
  })
  @PostMapping("/token")
  public CommonResult<OAuth2TokenDTO> postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters
  ) throws HttpRequestMethodNotSupportedException {
    OAuth2AccessToken oAuth2AccessToken;
    String clientId = SecurityUtils.getAuthClientId();
    switch (clientId) {
      case AuthConstants.WEAPP_CLIENT_ID:  // 微信认证
        throw new UnsupportedOperationException("暂不支持微信");
      default:
        oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        break;
    }
    OAuth2TokenDTO auth2TokenDTO = OAuth2TokenDTO.builder()
        .token(oAuth2AccessToken.getValue())
        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
        .expiresIn(oAuth2AccessToken.getExpiresIn())
        .build();
    return CommonResult.success(auth2TokenDTO);
  }


}
