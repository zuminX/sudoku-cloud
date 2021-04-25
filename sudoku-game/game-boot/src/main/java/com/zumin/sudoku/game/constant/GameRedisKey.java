package com.zumin.sudoku.game.constant;

public interface GameRedisKey {

  /**
   * 游戏记录key值的前缀
   */
  String GAME_RECORD_PREFIX = "game_record:";

  /**
   * 平均花费时间排行key值的前缀
   */
  String AVERAGE_SPEND_TIME_RANKING_PREFIX = "average_spend_time_ranking:";

  /**
   * 最少花费时间排行key值的前缀
   */
  String MIN_SPEND_TIME_RANKING_PREFIX = "min_spend_time_ranking:";

  /**
   * 回答正确数排行key值的前缀
   */
  String CORRECT_NUMBER_RANKING_PREFIX = "correct_number_ranking:";
}
