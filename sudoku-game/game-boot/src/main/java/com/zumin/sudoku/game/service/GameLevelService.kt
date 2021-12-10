package com.zumin.sudoku.game.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.game.mapper.GameLevelMapper
import com.zumin.sudoku.game.pojo.GameLevel
import com.zumin.sudoku.game.pojo.GameLevelVO
import com.zumin.sudoku.game.toGameLevelVO
import org.springframework.stereotype.Service

@Service
class GameLevelService : ServiceImpl<GameLevelMapper, GameLevel>() {

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  fun listGameLevel(): List<GameLevelVO> {
    return list().map(GameLevel::toGameLevelVO)
  }

  /**
   * 获取数独游戏的所有难度ID
   *
   * @return 游戏难度ID列表
   */
  fun listGameLevelId(): List<Long> {
    return listGameLevel().map { it.id }
  }

}