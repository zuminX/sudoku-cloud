package com.zumin.sudoku.common.web.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zumin.sudoku.common.core.utils.ServletUtils;
import com.zumin.sudoku.common.core.constant.AuthConstants;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
public class SecurityUtils {

  public static JSONObject getJwtPayload() {
    String jwtPayload = ServletUtils.getRequest().getHeader(AuthConstants.JWT_PAYLOAD_KEY);
    return JSONUtil.parseObj(jwtPayload);
  }

  public static Long getUserId() {
    return getJwtPayload().getLong(AuthConstants.USER_ID_KEY);
  }


  public static String getUsername() {
    return getJwtPayload().getStr(AuthConstants.USER_NAME_KEY);
  }

  /**
   * 获取JWT的载体中的clientId
   *
   * @return
   */
  public static String getClientId() {
    return getJwtPayload().getStr(AuthConstants.CLIENT_ID_KEY);
  }

  /**
   * 获取登录认证的客户端ID
   * <p>
   * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret） 方式一：client_id、client_secret放在请求路径中 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic
   * Y2xpZW50OnNlY3JldA== 明文等于 client:secret
   *
   * @return
   */
  @SneakyThrows
  public static String getAuthClientId() {
    String clientId;

    HttpServletRequest request = ServletUtils.getRequest();

    // 从请求路径中获取
    clientId = request.getParameter(AuthConstants.CLIENT_ID_KEY);
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


  public static List<Long> getRoleIds() {
    List<String> list = getJwtPayload().get(AuthConstants.JWT_AUTHORITIES_KEY, List.class);
    return list.stream().map(Long::valueOf).collect(Collectors.toList());
  }
}
