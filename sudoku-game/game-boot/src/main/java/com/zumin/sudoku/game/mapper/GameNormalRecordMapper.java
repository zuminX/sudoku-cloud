package com.zumin.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zumin.sudoku.game.pojo.bo.RankItemBO;
import com.zumin.sudoku.game.pojo.entity.GameNormalRecord;
import com.zumin.sudoku.game.pojo.result.GameNormalRecordResultForHistory;
import com.zumin.sudoku.game.pojo.vo.UserGameInformationVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GameNormalRecordMapper extends BaseMapper<GameNormalRecord> {

  /**
   * 查询平均花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectAverageSpendTimeRankLimit(@Param("limitNumber") Integer limitNumber);

  /**
   * 查询最少花费时间的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectMinSpendTimeRankLimit(@Param("limitNumber") Integer limitNumber);

  /**
   * 查询回答正确数的排名
   *
   * @param limitNumber 最多的排名数
   * @return 排行项列表
   */
  List<RankItemBO> selectCorrectNumberRankLimit(@Param("limitNumber") Integer limitNumber);

  /**
   * 根据用户ID查询其游戏信息
   *
   * @param userId 用户ID
   * @return 该用户的游戏信息
   */
  List<UserGameInformationVO> selectGameInformationByUserId(@Param("userId") Long userId);

  /**
   * 根据用户ID查找其不属于指定数独记录ID的游戏记录，且结果按开始时间降序排序
   *
   * @param userId             用户ID
   * @param ignoreGameRecordId 排除的数独记录ID
   * @return 查询历史普通游戏记录结果的列表
   */
  List<GameNormalRecordResultForHistory> selectByUserIdOrderByStartTimeDescIgnoreGameRecord(@Param("userId") Long userId,
      @Param("ignoreGameRecordId") Long ignoreGameRecordId);

  /**
   * 根据用户ID查找其游戏记录，且结果按开始时间降序排序
   *
   * @param userId 用户ID
   * @return 查询历史普通游戏记录结果的列表
   */
  List<GameNormalRecordResultForHistory> selectByUserIdOrderByStartTimeDesc(@Param("userId") Long userId);
}