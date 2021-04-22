package com.zumin.sudoku.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.game.mapper.GameRecordMapper;
import com.zumin.sudoku.game.pojo.entity.GameRecord;
import org.springframework.stereotype.Service;

@Service
public class GameRecordService extends ServiceImpl<GameRecordMapper, GameRecord> {

}
