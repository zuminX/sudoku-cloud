package com.zumin.sudoku.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.game.convert.GameLevelConvert;
import com.zumin.sudoku.game.mapper.GameLevelMapper;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.pojo.vo.GameLevelVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameLevelService extends ServiceImpl<GameLevelMapper, GameLevel> {

  private final GameLevelConvert levelConvert;

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  public List<GameLevelVO> listGameLevel() {
    return list().stream().map(levelConvert::convert).collect(Collectors.toList());
  }

  /**
   * 获取数独游戏的所有难度ID
   *
   * @return 游戏难度ID列表
   */
  public List<Long> listGameLevelId() {
    return listGameLevel().stream().map(GameLevelVO::getId).collect(Collectors.toList());
  }

}
