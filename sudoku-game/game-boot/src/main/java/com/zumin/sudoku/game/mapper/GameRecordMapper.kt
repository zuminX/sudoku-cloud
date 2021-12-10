package com.zumin.sudoku.game.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.game.pojo.GameRecord
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDate

@Mapper
interface GameRecordMapper : BaseMapper<GameRecord> {
  fun countByDateBetween(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): Int
}