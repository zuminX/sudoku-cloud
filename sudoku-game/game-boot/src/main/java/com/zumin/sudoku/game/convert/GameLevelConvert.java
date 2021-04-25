package com.zumin.sudoku.game.convert;

import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.pojo.vo.GameLevelVO;
import org.mapstruct.Mapper;

/**
 * 数独等级镀锡转换器
 */
@Mapper
public interface GameLevelConvert {

  /**
   * 将数独等级对象转换为显示对象
   *
   * @param gameLevel 数独等级对象
   * @return 数独等级显示对象
   */
  GameLevelVO convert(GameLevel gameLevel);
}
