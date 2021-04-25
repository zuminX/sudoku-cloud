package com.zumin.sudoku.game.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.game.convert.GameRecordConvert;
import com.zumin.sudoku.game.mapper.GameRecordMapper;
import com.zumin.sudoku.game.pojo.bo.GameRecordBO;
import com.zumin.sudoku.game.pojo.entity.GameRecord;
import com.zumin.sudoku.game.utils.GameUtils;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameRecordService extends ServiceImpl<GameRecordMapper, GameRecord> {

  private final GameRecordConvert recordConvert;

  private final GameUtils gameUtils;

  public Integer countByDate(LocalDate startDate, LocalDate endDate) {
    return baseMapper.countByDateBetween(startDate, endDate);
  }

  /**
   * 插入当前游戏的数独记录
   */
  @Transactional
  public void saveNowGame() {
    GameRecord gameRecord = recordConvert.boToEntity(gameUtils.getGameRecord());
    save(gameRecord);
    updateIdInRedis(gameRecord.getId());
  }

  /**
   * 更新当前游戏的数独记录的结束时间
   */
  @Transactional
  public void updateNowGameEndTime() {
    GameRecordBO gameRecord = gameUtils.getGameRecord();
    update(Wrappers.lambdaUpdate(GameRecord.class).set(GameRecord::getEndTime, gameRecord.getEndTime()).eq(GameRecord::getId, gameRecord.getId()));
  }

  /**
   * 更新Redis中的数独记录的ID字段
   *
   * @param gameRecordId 数独记录ID
   */
  private void updateIdInRedis(Long gameRecordId) {
    GameRecordBO gameRecord = gameUtils.getGameRecord();
    gameRecord.setId(gameRecordId);
    gameUtils.setGameRecord(gameRecord);
  }

}
