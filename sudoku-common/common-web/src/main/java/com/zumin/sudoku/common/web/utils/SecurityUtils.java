package com.zumin.sudoku.common.web.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import com.zumin.sudoku.common.core.constant.AuthParamName;
import com.zumin.sudoku.common.core.utils.ServletUtils;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class SecurityUtils {

  /**
   * 获取JWT载荷
   *
   * @return JSON对象
   */
  public JSONObject getJwtPayload() {
    String jwtPayload = ServletUtils.getRequest().getHeader(AuthConstants.JWT_PAYLOAD_KEY);
    return JSONUtil.parseObj(jwtPayload);
  }

  /**
   * 获取当前用户的用户ID
   *
   * @return 用户ID
   */
  public Long getUserId() {
    return getJwtPayload().getLong(AuthParamName.USER_ID);
  }

  /**
   * 获取当前用户的用户名
   *
   * @return 用户名
   */
  public String getUsername() {
    return getJwtPayload().getStr(AuthParamName.USERNAME);
  }

  /**
   * 获取ClientId
   *
   * @return 客户端ID
   */
  public String getClientId() {
    return getJwtPayload().getStr(AuthParamName.CLIENT_ID);
  }

  /**
   * 获取当前用户的角色ID
   *
   * @return 角色ID
   */
  public Set<Long> getRoleIds() {
    Set<String> set = getJwtPayload().get(AuthConstants.JWT_AUTHORITIES_KEY, Set.class);
    return set.stream().map(Long::valueOf).collect(Collectors.toSet());
  }

  /**
   * 获取登录认证的客户端ID
   *
   * @return 客户端ID
   */
  @SneakyThrows
  public String getAuthClientId() {
    HttpServletRequest request = ServletUtils.getRequest();
    // 从请求路径中获取
    String clientId = request.getParameter(AuthParamName.CLIENT_ID);
    if (StrUtil.isNotBlank(clientId)) {
      return clientId;
    }
    // 从请求头获取
    String basic = request.getHeader(AuthConstants.AUTHORIZATION_KEY);
    if (StrUtil.isNotBlank(basic) && basic.startsWith(AuthConstants.BASIC_PREFIX)) {
      basic = basic.replace(AuthConstants.BASIC_PREFIX, Strings.EMPTY);
      String basicPlainText = new String(Base64.decode(basic), StandardCharsets.UTF_8);
      clientId = basicPlainText.split(":")[0]; //client:secret
    }
    return clientId;
  }

}
