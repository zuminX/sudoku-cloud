package com.zumin.sudoku.user;

import com.zumin.sudoku.auth.feign.AuthFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {AuthFeign.class})
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.user", "com.zumin.sudoku.common"})
public class UserBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserBootApplication.class, args);
  }

}
