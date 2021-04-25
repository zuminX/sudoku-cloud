package com.zumin.sudoku.game.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.common.core.utils.PublicUtils;
import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.mybatis.page.PageParam;
import com.zumin.sudoku.common.mybatis.utils.PageUtils;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.game.convert.GameNormalRecordConvert;
import com.zumin.sudoku.game.enums.AnswerSituation;
import com.zumin.sudoku.game.mapper.GameNormalRecordMapper;
import com.zumin.sudoku.game.pojo.bo.RankItemBO;
import com.zumin.sudoku.game.pojo.entity.GameNormalRecord;
import com.zumin.sudoku.game.pojo.result.GameNormalRecordResultForHistory;
import com.zumin.sudoku.game.pojo.vo.GameNormalRecordVO;
import com.zumin.sudoku.game.pojo.vo.UserGameInformationVO;
import com.zumin.sudoku.game.utils.GameUtils;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameNormalRecordService extends ServiceImpl<GameNormalRecordMapper, GameNormalRecord> {

  private final GameUtils gameUtils;

  private final GameNormalRecordConvert normalRecordConvert;

  public List<RankItemBO> listAverageSpendTimeRankData(Integer limitNumber) {
    return baseMapper.selectAverageSpendTimeRankLimit(limitNumber);
  }

  public List<RankItemBO> listMinSpendTimeRankData(Integer limitNumber) {
    return baseMapper.selectMinSpendTimeRankLimit(limitNumber);
  }

  public List<RankItemBO> listCorrectNumberRankData(Integer limitNumber) {
    return baseMapper.selectCorrectNumberRankLimit(limitNumber);
  }

  /**
   * 更新游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  public void updateNowGame(List<List<Integer>> inputMatrix, AnswerSituation answerSituation) {
    update(Wrappers.lambdaUpdate(GameNormalRecord.class)
        .set(GameNormalRecord::getAnswer, PublicUtils.compressionIntList(inputMatrix))
        .set(GameNormalRecord::getAnswer, answerSituation.getCode())
        .eq(GameNormalRecord::getRecordId, gameUtils.getGameRecord().getId()));
  }

  /**
   * 插入当前游戏的游戏记录
   */
  @Transactional
  public void saveNowGame() {
    GameNormalRecord gameNormalRecord = GameNormalRecord.builder()
        .userId(SecurityUtils.getUserId())
        .recordId(gameUtils.getGameRecord().getId())
        .build();
    save(gameNormalRecord);
  }

  /**
   * 获取当前用户的游戏信息
   *
   * @return 用户游戏信息列表
   */
  public List<UserGameInformationVO> listUserGameInformation() {
    return listUserGameInformation(SecurityUtils.getUserId());
  }

  /**
   * 根据用户ID，获取其游戏信息
   *
   * @param userId 用户ID
   * @return 用户游戏信息列表
   */
  public List<UserGameInformationVO> listUserGameInformation(Long userId) {
    return baseMapper.selectGameInformationByUserId(userId);
  }

  /**
   * 获取历史游戏记录
   *
   * @return 游戏记录的分页信息
   */
  public Page<GameNormalRecordVO> getHistoryGameRecord() {
    Long userId = SecurityUtils.getUserId();
    if (!gameUtils.isRecord()) {
      return getHistoryGameRecordById(userId);
    }
    //防止显示当前正在进行的游戏
    Long nowGameRecordId = gameUtils.getGameRecord().getId();
    return getHistoryGameRecord(() -> baseMapper.selectByUserIdOrderByStartTimeDescIgnoreGameRecord(userId, nowGameRecordId));
  }

  /**
   * 根据用户ID，获取其历史游戏记录
   *
   * @param userId 用户ID
   * @return 普通游戏记录的分页信息
   */
  public Page<GameNormalRecordVO> getHistoryGameRecordById(Long userId) {
    return getHistoryGameRecord(() -> baseMapper.selectByUserIdOrderByStartTimeDesc(userId));
  }

  /**
   * 获取历史游戏记录
   *
   * @param supplier 查询函数
   * @return 普通游戏记录的分页信息
   */
  private Page<GameNormalRecordVO> getHistoryGameRecord(Supplier<List<GameNormalRecordResultForHistory>> supplier) {
    return PageUtils.getPage(new PageParam<>(supplier), normalRecordConvert::resultToVO);
  }

}
