package com.zumin.sudoku.game.rank

import com.zumin.sudoku.game.pojo.RankItemDataBO
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.bouncycastle.asn1.x500.style.RFC4519Style.name
import org.springframework.data.redis.core.DefaultTypedTuple
import org.springframework.data.redis.core.ZSetOperations
import java.util.*
import java.util.stream.Collectors

/**
 * 获取所有排行类型名
 *
 * @return 排行类型名列表
 */
fun getAllRankingTypeName() : List<String> {
  return RankingType.values().map(RankingType::typeName)
}

/**
 * 根据名字查找对应的排行类型对象
 *
 * @return 若存在该名字的排行类型对象，则将其返回。否则返回null
 */
fun String.toRankingType(): RankingType? {
  return RankingType.values().firstOrNull { this == it.typeName }
}

@ApiModel("排行类型类")
enum class RankingType(
  @ApiModelProperty("排行名称")
  val typeName: String,
  @ApiModelProperty("Redis的Key值前缀")
  val redisPrefix: String,
  @ApiModelProperty("排行项数据转换为值-分数对的回调方法")
  val rankItemToTypedTupleCallback: RankItemToTypedTuple,
  @ApiModelProperty("值-分数对转换为排行项数据的回调方法")
  val typedTupleToRankItemCallback: TypedTupleToRankItem,
) {
  AVERAGE_SPEND_TIME("平均花费时间", AVERAGE_SPEND_TIME_RANKING_PREFIX, Rank_Item_To_Typed_Tuple_ASC,
    Typed_Tuple_To_Rank_Item_ASC),
  CORRECT_NUMBER("回答正确数", CORRECT_NUMBER_RANKING_PREFIX, Rank_Item_To_Typed_Tuple_DESC,
    Typed_Tuple_To_Rank_Item_DESC),
  MIN_SPEND_TIME("最小花费时间", MIN_SPEND_TIME_RANKING_PREFIX, Rank_Item_To_Typed_Tuple_ASC,
    Typed_Tuple_To_Rank_Item_ASC);
}

// 平均花费时间排行key值的前缀
private const val AVERAGE_SPEND_TIME_RANKING_PREFIX = "average_spend_time_ranking:"

// 最少花费时间排行key值的前缀
private const val MIN_SPEND_TIME_RANKING_PREFIX = "min_spend_time_ranking:"

// 回答正确数排行key值的前缀
private const val CORRECT_NUMBER_RANKING_PREFIX = "correct_number_ranking:"

// 将值-分数对转换为排行项数据
typealias RankItemToTypedTuple = (RankItemDataBO) -> ZSetOperations.TypedTuple<String>

// 将数据按升序进行排行
val Rank_Item_To_Typed_Tuple_ASC: RankItemToTypedTuple = {
  val (username, data) = it
  DefaultTypedTuple(username, data.toDouble())
}

// 将数据按降序进行排行
val Rank_Item_To_Typed_Tuple_DESC: RankItemToTypedTuple = {
  val (username, data) = it
  DefaultTypedTuple(username, -1 * data.toDouble())
}

// 将值-分数对转换为排行项数据
typealias TypedTupleToRankItem = (ZSetOperations.TypedTuple<String>) -> RankItemDataBO

// 处理按升序进行排行的数据
val Typed_Tuple_To_Rank_Item_ASC: TypedTupleToRankItem = { RankItemDataBO(it.value!!, it.score!!.toInt()) }

// 将数据按降序进行排行
val Typed_Tuple_To_Rank_Item_DESC: TypedTupleToRankItem = { RankItemDataBO(it.value!!, -1 * it.score!!.toInt()) }