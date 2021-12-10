package com.zumin.sudoku.game.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.game.pojo.GameLevel
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GameLevelMapper : BaseMapper<GameLevel>