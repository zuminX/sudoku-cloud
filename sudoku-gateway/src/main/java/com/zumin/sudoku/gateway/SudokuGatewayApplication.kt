package com.zumin.sudoku.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class SudokuGatewayApplication

fun main(args: Array<String>) {
  runApplication<SudokuGatewayApplication>(*args)
}