package com.zumin.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameLevelMapper extends BaseMapper<GameLevel> {
}