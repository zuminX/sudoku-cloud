package com.zumin.sudoku.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.admin", "com.zumin.sudoku.common"})
public class AdminBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminBootApplication.class, args);
  }

}
