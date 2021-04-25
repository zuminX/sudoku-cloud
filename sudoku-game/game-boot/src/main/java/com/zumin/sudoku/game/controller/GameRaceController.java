package com.zumin.sudoku.game.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.game.pojo.bo.GameDataBO;
import com.zumin.sudoku.game.pojo.body.GameRaceInformationBody;
import com.zumin.sudoku.game.pojo.vo.GameRaceInformationVO;
import com.zumin.sudoku.game.service.GameRaceInformationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/gameRace", tags = "数独游戏竞赛API接口")
public class GameRaceController extends GameBaseController {

  private final GameRaceInformationService raceInformationService;

  @PostMapping("/publishPublicRace")
  @ApiOperation("发布公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceInformationBody", value = "竞赛内容信息体类", dataTypeClass = GameRaceInformationBody.class, required = true)
  public void publishPublicRace(@Valid @RequestBody GameRaceInformationBody gameRaceInformationBody) {
    raceInformationService.addPublicRace(gameRaceInformationBody);
  }

  @GetMapping("/publicRaceList")
  @ApiOperation("获取公开的数独游戏竞赛")
  public List<GameRaceInformationVO> getPublicRaceList() {
    return raceInformationService.listPublicRace();
  }

  @PostMapping("/joinPublicRace")
  @ApiOperation("参加公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceId", value = "竞赛ID", dataTypeClass = Long.class, required = true)
  public GameDataBO joinPublicRace(@RequestParam Long raceId) {
    //TODO 待完成
    return null;
  }
}
