package com.zumin.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.game.pojo.entity.GameRecord;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GameRecordMapper extends BaseMapper<GameRecord> {

  Integer countByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}