package com.zumin.sudoku.game.convert;

import com.zumin.sudoku.game.pojo.result.GameNormalRecordResultForHistory;
import com.zumin.sudoku.game.pojo.vo.GameNormalRecordVO;
import com.zumin.sudoku.game.utils.SudokuUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 普通游戏记录对象转换器
 */
@Mapper(imports = SudokuUtils.class, uses = GameRecordConvert.class)
public interface GameNormalRecordConvert {

  /**
   * 将查询历史普通游戏记录的结果对象转换为普通游戏记录显示类对象
   *
   * @param gameNormalRecord 查询历史普通游戏记录的结果对象
   * @return 普通游戏记录显示类对象
   */
  @Mapping(target = "answer", expression = "java(SudokuUtils.unzipToMatrix(gameNormalRecord.getAnswer()))")
  @Mapping(target = "gameRecord", source = "gameRecordResult")
  GameNormalRecordVO resultToVO(GameNormalRecordResultForHistory gameNormalRecord);
}
