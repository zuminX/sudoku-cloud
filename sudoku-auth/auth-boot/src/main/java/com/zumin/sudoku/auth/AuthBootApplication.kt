package com.zumin.sudoku.auth

import com.zumin.sudoku.ums.UserFeign
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = [UserFeign::class])
class AuthBootApplication

fun main(args: Array<String>) {
  runApplication<AuthBootApplication>(*args)
}
