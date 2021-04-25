package com.zumin.sudoku.game.controller;

import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.game.pojo.vo.GameNormalRecordVO;
import com.zumin.sudoku.game.pojo.vo.UserGameInformationVO;
import com.zumin.sudoku.game.service.GameNormalRecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/user", tags = "用户游戏信息API接口")
public class UserGameController extends GameBaseController {

  private final GameNormalRecordService normalRecordService;

  @GetMapping("/gameInformation")
  @ApiOperation("获取用户游戏信息")
  public List<UserGameInformationVO> getUserGameInformation() {
    return normalRecordService.listUserGameInformation();
  }

  @GetMapping("/historyGameRecord")
  @ApiOperation("获取历史游戏记录")
  public Page<GameNormalRecordVO> getHistoryGameRecord() {
    return normalRecordService.getHistoryGameRecord();
  }

  @GetMapping("/gameInformationById")
  @ApiOperation("根据用户ID，获取其游戏信息")
  @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Long.class, required = true)
  public List<UserGameInformationVO> getUserGameInformationById(@RequestParam("userId") Long userId) {
    return normalRecordService.listUserGameInformation(userId);
  }

  @GetMapping("/historyGameRecordById")
  @ApiOperation("根据用户ID，获取其历史游戏记录")
  @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Long.class, required = true)
  public Page<GameNormalRecordVO> getHistoryGameRecordById(@RequestParam("userId") Long userId) {
    return normalRecordService.getHistoryGameRecordById(userId);
  }

}

