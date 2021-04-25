package com.zumin.sudoku.game.controller;


import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.rank.RankingType;
import com.zumin.sudoku.game.pojo.bo.RankItemDataBO;
import com.zumin.sudoku.game.service.GameRankService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/rank", tags = "数独游戏排行API接口")
public class GameRankController extends GameBaseController {

  private final GameRankService gameRankService;

  @GetMapping("/leaderboard")
  @ApiOperation("获取数独游戏排行榜")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType.class, required = true),
      @ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long.class, required = true),
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<RankItemDataBO> getLeaderboard(@RequestParam RankingType rankingName, @RequestParam GameLevel level,
      @RequestParam Integer page, @RequestParam @Range(min = 1, max = 20, message = "每页显示的条数在1-20个之间") Integer pageSize) {
    return gameRankService.getRanking(rankingName, level, page, pageSize);
  }

  @GetMapping("/rankingName")
  @ApiOperation("获取排行类型名")
  public List<String> getRankingName() {
    return RankingType.getAllRankingTypeName();
  }

  @GetMapping("/rank")
  @ApiOperation("获取当前用户指定排行的排名")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType.class, required = true),
      @ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long.class, required = true),
  })
  public Long getCurrentUserRank(@RequestParam RankingType rankingName, @RequestParam GameLevel level) {
    return gameRankService.getCurrentUserRank(rankingName, level);
  }
}
