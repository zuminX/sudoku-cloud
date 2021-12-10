package com.zumin.sudoku.ums

import com.zumin.sudoku.common.core.CommonResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "sudoku-ums", contextId = "ums")
interface UserFeign {

  @GetMapping("/user/username/{username}")
  fun getUserByUsername(@PathVariable username: String): CommonResult<UserDTO>
}