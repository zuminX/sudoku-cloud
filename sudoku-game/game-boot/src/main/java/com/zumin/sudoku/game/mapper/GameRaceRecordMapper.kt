package com.zumin.sudoku.game.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.game.pojo.GameRaceRecord
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GameRaceRecordMapper : BaseMapper<GameRaceRecord>