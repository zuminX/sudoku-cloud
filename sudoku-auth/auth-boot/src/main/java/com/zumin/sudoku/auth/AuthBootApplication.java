package com.zumin.sudoku.auth;

import com.zumin.sudoku.admin.feign.UserFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {UserFeign.class})
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.auth", "com.zumin.sudoku.common"})
public class AuthBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthBootApplication.class, args);
  }

}
