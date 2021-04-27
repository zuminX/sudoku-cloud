package com.zumin.sudoku.ums.controller;

import com.zumin.sudoku.common.alicloud.pojo.OSSCallbackParameter;
import com.zumin.sudoku.common.alicloud.pojo.OSSCallbackResult;
import com.zumin.sudoku.common.alicloud.pojo.OSSPolicy;
import com.zumin.sudoku.common.alicloud.service.OSSService;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.ums.config.ProfileConfig;
import com.zumin.sudoku.ums.config.ProfileConfig.AvatarProperties;
import com.zumin.sudoku.ums.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@ComRestController(path = "/profile", tags = "用户档案API接口")
public class ProfileController {

  private final SysUserService userService;

  private final OSSService ossService;

  private final ProfileConfig profileConfig;

  @PostMapping("/uploadAvatarPolicy")
  @ApiOperation("获取上传头像的凭证")
  public OSSPolicy getUploadAvatarPolicy() {
    AvatarProperties avatar = profileConfig.getAvatar();
    return ossService.policy(avatar.getMaxSize(), avatar.getDir(),
        OSSCallbackParameter.builder().callbackPath("profile/updateAvatarCallback").data(SecurityUtils.getUserId()).timeout(10).timeUnit(TimeUnit.MINUTES).build());
  }

  @PostMapping("/updateAvatarCallback")
  @ApiOperation("更新头像的回调接口")
  public void updateAvatar() {
    OSSCallbackResult<Long> oosCallback = ossService.resolveCallbackData();
    String avatarPath = oosCallback.getFilePath();
    userService.updateAvatar(avatarPath, oosCallback.getCallbackData());
  }
}
