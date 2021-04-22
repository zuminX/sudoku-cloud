package com.zumin.sudoku.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.gateway", "com.zumin.sudoku.common"})
public class SudokuGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SudokuGatewayApplication.class, args);
  }

}