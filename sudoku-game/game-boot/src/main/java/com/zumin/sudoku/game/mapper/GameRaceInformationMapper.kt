package com.zumin.sudoku.game.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.game.pojo.GameRaceInformation
import com.zumin.sudoku.game.pojo.GameRaceInformationVO
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDateTime

@Mapper
interface GameRaceInformationMapper : BaseMapper<GameRaceInformation> {
  /**
   * 查询在指定结束日期时间前的竞赛信息
   *
   * @param maxEndTime 最大的结束日期时间
   * @return 竞赛信息列表
   */
  fun selectAllByEndTimeBefore(@Param("maxEndTime") maxEndTime: LocalDateTime): List<GameRaceInformationVO>
}