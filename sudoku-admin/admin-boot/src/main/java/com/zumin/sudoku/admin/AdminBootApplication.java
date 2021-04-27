package com.zumin.sudoku.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AdminBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminBootApplication.class, args);
  }

}
