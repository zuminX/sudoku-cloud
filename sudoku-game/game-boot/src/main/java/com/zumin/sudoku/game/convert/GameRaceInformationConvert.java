package com.zumin.sudoku.game.convert;


import com.zumin.sudoku.common.core.utils.PublicUtils;
import com.zumin.sudoku.game.pojo.body.GameRaceInformationBody;
import com.zumin.sudoku.game.pojo.entity.GameRaceInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 竞赛信息对象转换器
 */
@Mapper(imports = PublicUtils.class)
public interface GameRaceInformationConvert {

  /**
   * 将竞赛内容信息对象转换为竞赛信息表对应的实体类对象
   *
   * @param raceInformation 竞赛内容信息对象
   * @param userId          创建者ID
   * @return 竞赛信息表对应的实体类对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "matrix", expression = "java(PublicUtils.compressionIntList(raceInformation.getMatrix()))")
  @Mapping(target = "holes", expression = "java(PublicUtils.compressionBoolList(raceInformation.getHoles()))")
  @Mapping(target = "startTime", source = "raceInformation.raceTimeRange.start")
  @Mapping(target = "endTime", source = "raceInformation.raceTimeRange.end")
  GameRaceInformation convert(GameRaceInformationBody raceInformation, Long userId);
}
