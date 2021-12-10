package com.zumin.sudoku.game.service

import com.zumin.sudoku.common.mybatis.page.Page
import com.zumin.sudoku.common.mybatis.page.PageInformation
import com.zumin.sudoku.common.redis.RedisUtils
import com.zumin.sudoku.common.web.getCurrentUsername
import com.zumin.sudoku.game.pojo.GameLevel
import com.zumin.sudoku.game.pojo.RankItemBO
import com.zumin.sudoku.game.pojo.RankItemDataBO
import com.zumin.sudoku.game.rank.RankingType
import com.zumin.sudoku.game.rank.TypedTupleToRankItem
import org.springframework.stereotype.Service

// 排行榜人数
private const val RANKING_NUMBER = 10000L

/**
 * 游戏排行业务层类
 */
@Service
class GameRankService(
  private val levelService: GameLevelService,
  private val normalRecordService: GameNormalRecordService,
  private val redisUtils: RedisUtils,
) {

  /**
   * 初始化排行数据
   */
  fun initRanking() {
    initRankingData(RankingType.AVERAGE_SPEND_TIME, normalRecordService::listAverageSpendTimeRankData)
    initRankingData(RankingType.MIN_SPEND_TIME, normalRecordService::listMinSpendTimeRankData)
    initRankingData(RankingType.CORRECT_NUMBER, normalRecordService::listCorrectNumberRankData)
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
  fun getRanking(rankingType: RankingType, level: GameLevel, page: Int, pageSize: Int): Page<RankItemDataBO> {
    val key = rankingType.getRankingKey(level.id!!)
    val rankItemDataList = getRankingData(key, rankingType.typedTupleToRankItemCallback, page, pageSize)
    return transformToPage(page, pageSize, key, rankItemDataList)
  }

  /**
   * 获取当前用户指定排行的排名
   *
   * @param rankingType 排行类型
   * @param level       难度等级ID
   * @return 若排名不存在或大于RANKING_NUMBER，则返回null；否则返回对应的排名
   */
  fun getCurrentUserRank(rankingType: RankingType, level: GameLevel): Long? {
    val rank = redisUtils.getZSetRank(rankingType.getRankingKey(level.id!!), getCurrentUsername())
    if (rank == null || rank > RANKING_NUMBER) {
      return null
    }
    return rank
  }

  /**
   * 删除排行榜中超过排行榜人数限制的数据
   */
  fun removeExceedNumberData() {
    val gameLevelId = levelService.listGameLevelId()
    RankingType.values().forEach { type ->
      gameLevelId.forEach { redisUtils.removeZSetByRange(type.getRankingKey(it), RANKING_NUMBER, -1) }
    }
  }

  /**
   * 更新用户的排名
   *
   * @param rankingType 排行类型
   * @param gameLevelId 数独等级ID
   * @param username    用户名
   * @param data        数据
   */
  private fun updateUserRank(rankingType: RankingType, gameLevelId: Long, username: String, data: Int?) {
    if (data == null) {
      return
    }
    val tuple = rankingType.rankItemToTypedTupleCallback.invoke(RankItemDataBO(username, data))
    redisUtils.addZSet(rankingType.getRankingKey(gameLevelId), tuple)
  }

  /**
   * 初始化排行数据
   *
   * @param rankingType 排行类型
   * @param callback    获取排行项数据的回调方法
   */
  private fun initRankingData(rankingType: RankingType, callback: (Long) -> List<RankItemBO>) {
    val list = callback.invoke(RANKING_NUMBER)
    insert(rankingType, list)
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
  private fun getRankingData(rankingKey: String, callback: TypedTupleToRankItem, page: Int, pageSize: Int): List<RankItemDataBO> {
    val start = (page - 1L) * pageSize
    val end = start + pageSize - 1
    val rangeWithScores = redisUtils.getZSetByRangeWithScores<String>(rankingKey, start, end)
    return rangeWithScores.map(callback)
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
  private fun transformToPage(page: Int, pageSize: Int, rankingKey: String, data: List<RankItemDataBO>): Page<RankItemDataBO> {
    val numberOfRanks = redisUtils.getZSetSize(rankingKey).toInt()
    var totalPage = numberOfRanks / pageSize
    if (numberOfRanks % pageSize != 0) {
      ++totalPage
    }
    return Page(PageInformation(totalPage, page, pageSize), data)
  }

  /**
   * 向Redis中插入排行项数据
   *
   * @param rankItemList 排行项列表
   */
  private fun insert(rankingType: RankingType, rankItemList: List<RankItemBO>) {
    rankItemList.forEach { rankItem ->
      val tuples = rankItem.rankItemDataList.map { rankingType.rankItemToTypedTupleCallback.invoke(it) }.toSet()
      redisUtils.setZSet(rankingType.getRankingKey(rankItem.gameLevelId), tuples)
    }
  }
}

/**
 * 获取排行的Redis的key
 *
 * @param gameLevelId 游戏难度ID
 * @return 对应排行的key
 */
private fun RankingType.getRankingKey(gameLevelId: Long): String {
  return redisPrefix + gameLevelId
}