package com.zumin.sudoku.ums.feign;

import com.zumin.sudoku.common.core.result.CommonResult;
import com.zumin.sudoku.ums.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sudoku-ums", contextId = "ums")
public interface UserFeign {

  @GetMapping("/user/username/{username}")
  CommonResult<UserDTO> getUserByUsername(@PathVariable String username);
}
