package com.zumin.sudoku.admin.feign;

import com.zumin.sudoku.admin.pojo.dto.UserDTO;
import com.zumin.sudoku.common.core.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sudoku-admin")
public interface UserFeign {

  @GetMapping("/user/username/{username}")
  CommonResult<UserDTO> getUserByUsername(@PathVariable String username);
}
