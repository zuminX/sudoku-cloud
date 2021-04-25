package com.zumin.sudoku.game.service;


import com.zumin.sudoku.common.mybatis.page.Page;
import com.zumin.sudoku.common.mybatis.page.Page.PageInformation;
import com.zumin.sudoku.common.redis.utils.RedisUtils;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.game.pojo.bo.RankItemBO;
import com.zumin.sudoku.game.pojo.bo.RankItemDataBO;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.rank.RankingType;
import com.zumin.sudoku.game.rank.callback.GetRankingDataCallback;
import com.zumin.sudoku.game.rank.callback.TransformTypedTupleToRankItemCallback;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

/**
 * 游戏排行业务层类
 */
@Service
@RequiredArgsConstructor
public class GameRankService {

  /**
   * 排行榜人数
   */
  private static final Integer RANKING_NUMBER = 10_000;

  private final GameLevelService levelService;
  private final GameNormalRecordService normalRecordService;
  private final RedisUtils redisUtils;

  /**
   * 初始化排行数据
   */
  public void initRanking() {
    initRankingData(RankingType.AVERAGE_SPEND_TIME, normalRecordService::listAverageSpendTimeRankData);
    initRankingData(RankingType.MIN_SPEND_TIME, normalRecordService::listMinSpendTimeRankData);
    initRankingData(RankingType.CORRECT_NUMBER, normalRecordService::listCorrectNumberRankData);
  }

  /**
   * 获取排行数据
   *
   * @param rankingType 排行类型
   * @param level       难度等级ID
   * @param page        当前查询页
   * @param pageSize    每页显示的条数
   * @return 排行项数据列表的分页信息
   */
  public Page<RankItemDataBO> getRanking(RankingType rankingType, GameLevel level, Integer page, Integer pageSize) {
    String key = getRankingKey(rankingType, level.getId());
    List<RankItemDataBO> rankItemDataList = getRankingData(key, rankingType.getTypedTupleToRankItemCallback(), page, pageSize);
    return transformToPage(page, pageSize, key, rankItemDataList);
  }

  /**
   * 获取当前用户指定排行的排名
   *
   * @param rankingType 排行类型
   * @param level       难度等级ID
   * @return 若排名不存在或大于RANKING_NUMBER，则返回null；否则返回对应的排名
   */
  public Long getCurrentUserRank(RankingType rankingType, GameLevel level) {
    String username = SecurityUtils.getUsername();
    Long rank = redisUtils.getZSetRank(getRankingKey(rankingType, level.getId()), username);
    return rank == null || rank > RANKING_NUMBER ? null : rank;
  }

  /**
   * 删除排行榜中超过排行榜人数限制的数据
   */
  public void removeExceedNumberData() {
    Arrays.stream(RankingType.values())
        .forEach(prefix -> levelService.listGameLevelId()
            .forEach(id -> redisUtils.removeZSetByRange(getRankingKey(prefix, id), RANKING_NUMBER, -1)));
  }

  /**
   * 更新用户的排名
   *
   * @param rankingType 排行类型
   * @param gameLevelId 数独等级ID
   * @param username    用户名
   * @param data        数据
   */
  private void updateUserRank(RankingType rankingType, Long gameLevelId, String username, Integer data) {
    if (data == null) {
      return;
    }
    TypedTuple<String> tuple = rankingType.getRankItemToTypedTupleCallback().transform(new RankItemDataBO(username, data));
    redisUtils.addZSet(getRankingKey(rankingType, gameLevelId), tuple);
  }

  /**
   * 获取排行的Redis的key
   *
   * @param rankingType 排行类型
   * @param gameLevelId 游戏难度ID
   * @return 对应排行的key
   */
  private String getRankingKey(RankingType rankingType, Long gameLevelId) {
    return rankingType.getRedisPrefix() + gameLevelId;
  }

  /**
   * 初始化排行数据
   *
   * @param rankingType 排行类型
   * @param callback    获取排行项数据的回调方法
   */
  private void initRankingData(RankingType rankingType, GetRankingDataCallback callback) {
    List<RankItemBO> list = callback.getData(RANKING_NUMBER);
    InsertRankItem insertRankItem = new InsertRankItem(rankingType);
    insertRankItem.insert(list);
  }

  /**
   * 获取下标为[start,end]的排行数据
   *
   * @param rankingKey 排行key值
   * @param callback   将排行项数据转换为值-分数对的回调方法
   * @param page       当前查询页
   * @param pageSize   每页显示的条数
   * @return 排行项数据列表
   */
  private List<RankItemDataBO> getRankingData(String rankingKey, TransformTypedTupleToRankItemCallback callback, Integer page,
      Integer pageSize) {
    long start = (page - 1L) * pageSize, end = start + pageSize - 1;
    Set<TypedTuple<String>> rangeWithScores = redisUtils.getZSetByRangeWithScores(rankingKey, start, end);
    return rangeWithScores.stream().map(callback::transform).collect(Collectors.toList());
  }

  /**
   * 转化排行数据为分页数据
   *
   * @param page       当前查询页
   * @param pageSize   每页显示的条数
   * @param rankingKey 排行key值
   * @param data       排行数据列表
   * @return 排行项数据列表的分页信息
   */
  private Page<RankItemDataBO> transformToPage(Integer page, Integer pageSize, String rankingKey, List<RankItemDataBO> data) {
    int numberOfRanks = Math.toIntExact(redisUtils.getZSetSize(rankingKey));
    int totalPage = numberOfRanks / pageSize;
    if (numberOfRanks % pageSize != 0) {
      ++totalPage;
    }
    PageInformation pageInformation = new PageInformation(totalPage, page, pageSize);
    return new Page<>(pageInformation, data);
  }

  /**
   * 向Redis插入排行项数据的模板方法
   */
  @RequiredArgsConstructor
  private class InsertRankItem {

    private final RankingType rankingType;

    /**
     * 向Redis中插入排行项数据
     *
     * @param rankItemList 排行项列表
     */
    public void insert(List<RankItemBO> rankItemList) {
      rankItemList.forEach(rankItem -> {
        List<RankItemDataBO> rankItemDataList = rankItem.getRankItemDataList();
        Set<TypedTuple<String>> tuples = rankItemDataList.stream().map(rankingType.getRankItemToTypedTupleCallback()::transform).collect(Collectors.toSet());
        redisUtils.setZSet(getRankingKey(rankingType, rankItem.getGameLevelId()), tuples);
      });
    }
  }
}
