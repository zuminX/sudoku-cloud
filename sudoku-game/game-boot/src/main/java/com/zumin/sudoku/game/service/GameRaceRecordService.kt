package com.zumin.sudoku.game.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.zumin.sudoku.game.mapper.GameRaceRecordMapper
import com.zumin.sudoku.game.pojo.GameRaceRecord
import org.springframework.stereotype.Service

@Service
class GameRaceRecordService : ServiceImpl<GameRaceRecordMapper, GameRaceRecord>()