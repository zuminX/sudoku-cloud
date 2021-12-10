package com.zumin.sudoku.ums

import com.zumin.sudoku.auth.OAuthFeign
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = [OAuthFeign::class])
@SpringBootApplication
class UmsBootApplication

fun main(args: Array<String>) {
  runApplication<UmsBootApplication>(*args)
}
