package com.zumin.sudoku.game.utils;


import com.zumin.sudoku.common.redis.utils.RedisUtils;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.game.constant.GameRedisKey;
import com.zumin.sudoku.game.enums.AnswerSituation;
import com.zumin.sudoku.game.pojo.bo.GameDataBO;
import com.zumin.sudoku.game.pojo.bo.GameRecordBO;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 游戏工具类
 */
@Component
@RequiredArgsConstructor
public class GameUtils {

  /**
   * 每局数独游戏最长存在时间(h)
   */
  private static final int GAME_MAX_TIME = 2;

  private final RedisUtils redisUtils;

  /**
   * 判断答题状态
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param gameDataBO 数独数据
   * @return 用户答题状态
   */
  public static AnswerSituation judgeAnswerSituation(List<List<Integer>> userMatrix, GameDataBO gameDataBO) {
    int[][] matrix = gameDataBO.getMatrix();
    boolean[][] holes = gameDataBO.getHoles();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (SudokuUtils.isNotHole(holes, i, j)) {
          continue;
        }
        Integer userValue = userMatrix.get(i).get(j);
        if (userValue == null || userValue != matrix[i][j]) {
          return AnswerSituation.ERROR;
        }
      }
    }
    return AnswerSituation.IDENTICAL;
  }

  /**
   * 从redis中获取当前数独记录
   *
   * @return 数独记录
   */
  public GameRecordBO getGameRecord() {
    return redisUtils.get(getGameRecordKey());
  }

  /**
   * 保存数独记录至redis中
   *
   * @param sudokuRecord 数独记录
   */
  public void setGameRecord(GameRecordBO sudokuRecord) {
    redisUtils.set(getGameRecordKey(), sudokuRecord, GAME_MAX_TIME, TimeUnit.HOURS);
  }

  /**
   * 移除数独记录
   */
  public void removeGameRecord() {
    redisUtils.delete(getGameRecordKey());
  }

  /**
   * 判断当前数独记录是否为记录模式
   *
   * @return 若是记录模式返回true，若不是记录模式或当前不存在记录则返回false
   */
  public boolean isRecord() {
    GameRecordBO sudokuRecord = getGameRecord();
    return sudokuRecord != null && sudokuRecord.isRecord();
  }

  /**
   * 获取数独记录的key值
   *
   * @return 数独记录在redis中的key值
   */
  private String getGameRecordKey() {
    return GameRedisKey.GAME_RECORD_PREFIX + SecurityUtils.getUserId();
  }
}
