package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.game.pojo.GameLevelVO
import com.zumin.sudoku.game.service.GameLevelService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping

@ComRestController(path = ["/level"], tags = ["游戏难度API接口"])
class GameLevelController(
  private val levelService: GameLevelService,
) : GameBaseController() {

  @ApiOperation("获取数独的所有难度等级")
  @GetMapping("/sudokuLevels")
  fun getSudokuLevels(): List<GameLevelVO> {
    return levelService.listGameLevel()
  }
}