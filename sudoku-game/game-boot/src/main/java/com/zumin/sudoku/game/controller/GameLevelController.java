package com.zumin.sudoku.game.controller;


import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.game.pojo.bo.GameDataBO;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.pojo.vo.GameLevelVO;
import com.zumin.sudoku.game.service.GameLevelService;
import com.zumin.sudoku.game.utils.SudokuBuilder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/level", tags = "游戏难度API接口")
public class GameLevelController extends GameBaseController {

  private final GameLevelService levelService;

  @GetMapping("/sudokuLevels")
  @ApiOperation("获取数独的所有难度等级")
  public List<GameLevelVO> getSudokuLevels() {
    return levelService.listGameLevel();
  }
}
