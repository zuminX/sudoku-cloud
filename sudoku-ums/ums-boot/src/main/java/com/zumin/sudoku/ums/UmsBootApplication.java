package com.zumin.sudoku.ums;

import com.zumin.sudoku.auth.feign.OAuthFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {OAuthFeign.class})
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.ums", "com.zumin.sudoku.common"})
public class UmsBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(UmsBootApplication.class, args);
  }

}
