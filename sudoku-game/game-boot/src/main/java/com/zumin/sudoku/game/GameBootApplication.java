package com.zumin.sudoku.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.zumin.sudoku.game", "com.zumin.sudoku.common"})
public class GameBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(GameBootApplication.class, args);
  }

}
