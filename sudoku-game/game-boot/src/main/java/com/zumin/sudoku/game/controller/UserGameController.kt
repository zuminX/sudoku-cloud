package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.game.pojo.GameNormalRecordVO
import com.zumin.sudoku.game.pojo.UserGameInformationVO
import com.zumin.sudoku.game.service.GameNormalRecordService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ComRestController(path = ["/user"], tags = ["用户游戏信息API接口"])
class UserGameController(
  private val normalRecordService: GameNormalRecordService,
) : GameBaseController() {

  @ApiOperation("获取用户游戏信息")
  @GetMapping("/gameInformation")
  fun getUserGameInformation(): List<UserGameInformationVO> {
    return normalRecordService.listUserGameInformation()
  }

  @ApiOperation("获取历史游戏记录")
  @GetMapping("/historyGameRecord")
  fun getHistoryGameRecord(): Page<GameNormalRecordVO> {
    return normalRecordService.getHistoryGameRecord()
  }

  @GetMapping("/gameInformationById")
  @ApiOperation("根据用户ID，获取其游戏信息")
  @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Long::class, required = true)
  fun getUserGameInformationById(@RequestParam("userId") userId: Long): List<UserGameInformationVO> {
    return normalRecordService.listUserGameInformation(userId)
  }

  @GetMapping("/historyGameRecordById")
  @ApiOperation("根据用户ID，获取其历史游戏记录")
  @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Long::class, required = true)
  fun getHistoryGameRecordById(@RequestParam("userId") userId: Long): Page<GameNormalRecordVO> {
    return normalRecordService.getHistoryGameRecordById(userId)
  }
}