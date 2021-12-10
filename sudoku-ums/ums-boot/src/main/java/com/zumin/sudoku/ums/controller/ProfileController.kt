package com.zumin.sudoku.ums.controller

import com.zumin.sudoku.common.alicloud.OSSCallbackParameter
import com.zumin.sudoku.common.alicloud.OSSPolicy
import com.zumin.sudoku.common.alicloud.OSSService
import com.zumin.sudoku.common.core.code.CommonStatusCode
import com.zumin.sudoku.common.core.exception.BaseException
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.common.web.utils.getCurrentUserId
import com.zumin.sudoku.ums.config.ProfileProperties
import com.zumin.sudoku.ums.service.SysUserService
import io.swagger.annotations.ApiOperation
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.web.bind.annotation.PostMapping

@EnableConfigurationProperties(ProfileProperties::class)
@ComRestController(path = ["/profile"], tags = ["用户档案API接口"])
class ProfileController(
  private val userService: SysUserService,
  private val ossService: OSSService,
  private val profileProperties: ProfileProperties,
) {

  @ApiOperation("获取上传头像的凭证")
  @PostMapping("/uploadAvatarPolicy")
  fun getUploadAvatarPolicy(): OSSPolicy {
    val (dir, maxSize) = profileProperties.avatar
    return ossService.policy(maxSize, dir, OSSCallbackParameter("profile/updateAvatarCallback", getCurrentUserId()))
  }

  @PostMapping("/updateAvatarCallback")
  @ApiOperation("更新头像的回调接口")
  fun updateAvatar() {
    val (callbackData, filePath) = ossService.resolveCallbackData(Long::class.java)
    val userId = callbackData ?: throw BaseException(CommonStatusCode.UNAUTHORIZED_ACCESS)
    userService.updateAvatar(filePath!!, userId)
  }
}