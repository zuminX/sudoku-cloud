package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.game.pojo.GameLevel
import com.zumin.sudoku.game.pojo.RankItemDataBO
import com.zumin.sudoku.game.rank.RankingType
import com.zumin.sudoku.game.rank.getAllRankingTypeName
import com.zumin.sudoku.game.service.GameRankService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.hibernate.validator.constraints.Range
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@ComRestController(path = ["/rank"], tags = ["游戏排行API接口"])
class GameRankController(
  private val gameRankService: GameRankService,
) : GameBaseController() {

  @GetMapping("/leaderboard")
  @ApiOperation("获取数独游戏排行榜")
  @ApiImplicitParams(
    ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType::class, required = true),
    ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long::class, required = true),
    ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Int::class, required = true),
    ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Int::class, required = true))
  fun getLeaderboard(
    @RequestParam rankingName: RankingType, @RequestParam sudokuLevelName: GameLevel,
    @RequestParam page: Int, @RequestParam @Range(min = 1, max = 20, message = "每页显示的条数在1-20个之间") pageSize: Int,
  ): Page<RankItemDataBO> {
    return gameRankService.getRanking(rankingName, sudokuLevelName, page, pageSize)
  }

  @ApiOperation("获取排行类型名")
  @GetMapping("/rankingName")
  fun getRankingName(): List<String> {
    return getAllRankingTypeName()
  }

  @GetMapping("/rank")
  @ApiOperation("获取当前用户指定排行的排名")
  @ApiImplicitParams(
    ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType::class, required = true),
    ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long::class, required = true))
  fun getCurrentUserRank(@RequestParam rankingName: RankingType, @RequestParam level: GameLevel): Long? {
    return gameRankService.getCurrentUserRank(rankingName, level)
  }
}