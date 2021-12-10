package com.zumin.sudoku.monitor

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
class SudokuMonitorApplication

fun main(args: Array<String>) {
  runApplication<SudokuMonitorApplication>(*args)
}