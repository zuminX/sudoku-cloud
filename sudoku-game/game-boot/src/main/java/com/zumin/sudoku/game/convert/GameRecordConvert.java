package com.zumin.sudoku.game.convert;


import com.zumin.sudoku.common.core.utils.PublicUtils;
import com.zumin.sudoku.game.pojo.bo.GameRecordBO;
import com.zumin.sudoku.game.pojo.body.GameRaceInformationBody;
import com.zumin.sudoku.game.pojo.entity.GameRecord;
import com.zumin.sudoku.game.pojo.result.GameRecordResultForHistory;
import com.zumin.sudoku.game.pojo.vo.GameRecordVO;
import com.zumin.sudoku.game.utils.SudokuUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 数独记录对象转换器
 */
@Mapper(imports = {PublicUtils.class, SudokuUtils.class})
public interface GameRecordConvert {

  /**
   * 将数独记录业务层对象转换为对应实体类对象
   * <p>
   * 将数独数据中的数独矩阵和题目空缺数组转换为字符串
   *
   * @param gameRecord 数独记录业务层对象
   * @return 数独记录表对应的实体类对象
   */
  @Mapping(target = "matrix", expression = "java(PublicUtils.compressionIntArray(gameRecord.getGameData().getMatrix()))")
  @Mapping(target = "holes", expression = "java(PublicUtils.compressionBoolArray(gameRecord.getGameData().getHoles()))")
  GameRecord boToEntity(GameRecordBO gameRecord);

  /**
   * 将竞赛信息对象转换为数独记录对象
   *
   * @param raceInformation 竞赛信息对象
   * @return 数独记录对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "matrix", expression = "java(PublicUtils.compressionIntList(raceInformation.getMatrix()))")
  @Mapping(target = "holes", expression = "java(PublicUtils.compressionBoolList(raceInformation.getHoles()))")
  @Mapping(target = "startTime", source = "raceInformation.raceTimeRange.start")
  @Mapping(target = "endTime", source = "raceInformation.raceTimeRange.end")
  GameRecord raceBodyToEntity(GameRaceInformationBody raceInformation);

  /**
   * 将查询历史数独记录的结果对象转换为数独记录显示对象
   *
   * @param gameRecordResult 查询历史数独记录的结果对象
   * @return 数独记录显示对象
   */
  @Mapping(target = "matrix", expression = "java(SudokuUtils.unzipToMatrix(gameRecordResult.getMatrix()))")
  @Mapping(target = "holes", expression = "java(SudokuUtils.unzipToHoles(gameRecordResult.getHoles()))")
  GameRecordVO resultHistoryToVo(GameRecordResultForHistory gameRecordResult);

}
