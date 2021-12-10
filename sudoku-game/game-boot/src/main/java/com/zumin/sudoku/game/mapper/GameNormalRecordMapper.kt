package com.zumin.sudoku.game.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zumin.sudoku.game.pojo.GameNormalRecord
import com.zumin.sudoku.game.pojo.GameNormalRecordResultForHistory
import com.zumin.sudoku.game.pojo.RankItemBO
import com.zumin.sudoku.game.pojo.UserGameInformationVO
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface GameNormalRecordMapper : BaseMapper<GameNormalRecord> {
  /**
   * 查询平均花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  fun selectAverageSpendTimeRankLimit(@Param("limitNumber") limitNumber: Long): List<RankItemBO>

  /**
   * 查询最少花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  fun selectMinSpendTimeRankLimit(@Param("limitNumber") limitNumber: Long): List<RankItemBO>

  /**
   * 查询回答正确数的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  fun selectCorrectNumberRankLimit(@Param("limitNumber") limitNumber: Long): List<RankItemBO>

  /**
   * 根据用户ID查询其游戏信息
   *
   * @param userId 用户ID
   * @return 该用户的游戏信息
   */
  fun selectGameInformationByUserId(@Param("userId") userId: Long): List<UserGameInformationVO>

  /**
   * 根据用户ID查找其不属于指定数独记录ID的游戏记录，且结果按开始时间降序排序
   *
   * @param userId             用户ID
   * @param ignoreGameRecordId 排除的数独记录ID
   * @return 查询历史普通游戏记录结果的列表
   */
  fun selectByUserIdOrderByStartTimeDescIgnoreGameRecord(
    @Param("userId") userId: Long,
    @Param("ignoreGameRecordId") ignoreGameRecordId: Long,
  ): List<GameNormalRecordResultForHistory>

  /**
   * 根据用户ID查找其游戏记录，且结果按开始时间降序排序
   *
   * @param userId 用户ID
   * @return 查询历史普通游戏记录结果的列表
   */
  fun selectByUserIdOrderByStartTimeDesc(@Param("userId") userId: Long): List<GameNormalRecordResultForHistory>
}