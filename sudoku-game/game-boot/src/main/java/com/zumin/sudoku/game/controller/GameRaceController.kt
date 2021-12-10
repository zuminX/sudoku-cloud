package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.game.pojo.GameDataBO
import com.zumin.sudoku.game.pojo.GameRaceInformationBody
import com.zumin.sudoku.game.pojo.GameRaceInformationVO
import com.zumin.sudoku.game.service.GameRaceInformationService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid

@ComRestController(path = ["/race"], tags = ["游戏竞赛API接口"])
class GameRaceController(
  private val raceInformationService: GameRaceInformationService,
) : GameBaseController() {

  @PostMapping("/publishPublicRace")
  @ApiOperation("发布公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceInformationBody", value = "竞赛内容信息体类",
    dataTypeClass = GameRaceInformationBody::class, required = true)
  fun publishPublicRace(@RequestBody @Valid gameRaceInformationBody: GameRaceInformationBody) {
    raceInformationService.addPublicRace(gameRaceInformationBody)
  }

  @ApiOperation("获取公开的数独游戏竞赛")
  @GetMapping("/publicRaceList")
  fun getPublicRaceList(): List<GameRaceInformationVO> {
    return raceInformationService.listPublicRace()
  }

  @PostMapping("/joinPublicRace")
  @ApiOperation("参加公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceId", value = "竞赛ID", dataTypeClass = Long::class, required = true)
  fun joinPublicRace(@RequestParam raceId: Long): GameDataBO {
    //TODO 待完成
    return TODO()
  }
}