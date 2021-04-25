package com.zumin.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.game.pojo.entity.GameRaceInformation;
import com.zumin.sudoku.game.pojo.vo.GameRaceInformationVO;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GameRaceInformationMapper extends BaseMapper<GameRaceInformation> {

  /**
   * 查询在指定结束日期时间前的竞赛信息
   *
   * @param maxEndTime 最大的结束日期时间
   * @return 竞赛信息列表
   */
  List<GameRaceInformationVO> selectAllByEndTimeBefore(@Param("maxEndTime") LocalDateTime maxEndTime);
}