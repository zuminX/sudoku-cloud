package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.web.domain.CustomEditorInfo
import com.zumin.sudoku.common.web.handler.DataBindingHandler
import com.zumin.sudoku.game.pojo.GameLevel
import com.zumin.sudoku.game.rank.RankingType
import com.zumin.sudoku.game.rank.toRankingType
import com.zumin.sudoku.game.service.GameLevelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder

/**
 * 游戏模块的基础控制器类
 */
@Controller
class GameBaseController : DataBindingHandler {
  @Autowired
  private lateinit var gameLevelService: GameLevelService

  /**
   * 初始化游戏模块的绑定信息
   *
   * @param binder 绑定器对象
   */
  @InitBinder
  override fun initBinder(binder: WebDataBinder) {
    // 数独等级的自定义编辑信息对象
    val sudokuLevelEditor = CustomEditorInfo(GameLevel::class.java, gameLevelService::getById)
    // 排行类型的自定义编辑信息对象
    val rankingTypeEditor = CustomEditorInfo(RankingType::class.java, String::toRankingType)
    initBinder(binder, listOf(sudokuLevelEditor, rankingTypeEditor))
  }
}