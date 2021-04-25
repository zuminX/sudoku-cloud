package com.zumin.sudoku.game.service;

import com.zumin.sudoku.common.core.utils.DateUtils;
import com.zumin.sudoku.common.core.utils.PublicUtils;
import com.zumin.sudoku.common.core.utils.TwoDimensionalListUtils;
import com.zumin.sudoku.game.enums.AnswerSituation;
import com.zumin.sudoku.game.pojo.bo.GameDataBO;
import com.zumin.sudoku.game.pojo.bo.GameRecordBO;
import com.zumin.sudoku.game.pojo.bo.SudokuGridInformationBO;
import com.zumin.sudoku.game.pojo.bo.UserAnswerInformationBO;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.utils.GameUtils;
import com.zumin.sudoku.game.utils.SudokuBuilder;
import com.zumin.sudoku.game.utils.SudokuUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数独业务层类
 */
@Service
@RequiredArgsConstructor
public class SudokuService {

  private final GameUtils gameUtils;

  /**
   * 生成数独题目
   *
   * @param gameLevel 数独等级
   * @param isRecord  是否记录
   * @return 数独题目
   */
  public GameDataBO generateSudokuTopic(GameLevel gameLevel, Boolean isRecord) {
    GameDataBO gameDataBO = SudokuBuilder.generateSudokuFinal(gameLevel.getMinEmpty(), gameLevel.getMaxEmpty());
    saveGameRecord(gameDataBO, gameLevel.getId(), isRecord);
    return hideVacancyGrid(gameDataBO);
  }

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  public SudokuGridInformationBO getHelp(List<List<Integer>> userMatrix) {
    GameRecordBO sudokuRecord = gameUtils.getGameRecord();
    ArrayList<SudokuGridInformationBO> errorGridInformationList = findErrorGridInformation(sudokuRecord.getGameData(), userMatrix);
    return randomGridInformation(errorGridInformationList);
  }

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  public UserAnswerInformationBO checkSudokuData(List<List<Integer>> userMatrix) {
    updateEndTimeForSudokuRecord();

    GameRecordBO sudokuRecord = gameUtils.getGameRecord();
    GameDataBO gameDataBO = gameUtils.getGameRecord().getGameData();

    AnswerSituation situation = GameUtils.judgeAnswerSituation(userMatrix, gameDataBO);

    return UserAnswerInformationBO.builder()
        .situation(situation)
        .matrix(gameDataBO.getMatrix())
        .spendTime(DateUtils.computeAbsDiff(sudokuRecord.getEndTime(), sudokuRecord.getStartTime()))
        .build();
  }

  /**
   * 隐藏空缺的格子信息
   *
   * @return 隐藏后的数独数据对象
   */
  private GameDataBO hideVacancyGrid(GameDataBO sudokuData) {
    return new GameDataBO(SudokuUtils.setVacancyGridToZero(sudokuData.getMatrix(), sudokuData.getHoles()), PublicUtils.deepClone(sudokuData.getHoles()));
  }

  /**
   * 更新Redis中的数独记录的结束时间字段
   */
  private void updateEndTimeForSudokuRecord() {
    GameRecordBO sudokuRecord = gameUtils.getGameRecord();
    sudokuRecord.setEndTime(LocalDateTime.now());
    gameUtils.setGameRecord(sudokuRecord);
  }

  /**
   * 随机返回一个格子信息
   *
   * @param errorGridInformationList 错误的数独格子信息列表
   * @return 数独格子信息
   */
  private SudokuGridInformationBO randomGridInformation(ArrayList<SudokuGridInformationBO> errorGridInformationList) {
    int size = errorGridInformationList.size();
    return size > 0 ? errorGridInformationList.get(PublicUtils.getRandomInt(0, size - 1)) : null;
  }

  /**
   * 保存游戏记录
   *
   * @param gameDataBO    数独数据
   * @param sudokuLevelId 数独难度ID
   * @param isRecord      是否记录
   */
  private void saveGameRecord(GameDataBO gameDataBO, Long sudokuLevelId, Boolean isRecord) {
    gameUtils.setGameRecord(GameRecordBO.builder()
        .gameData(gameDataBO)
        .startTime(LocalDateTime.now())
        .levelId(sudokuLevelId)
        .isRecord(isRecord)
        .build());
  }

  /**
   * 寻找错误的数独格子信息
   *
   * @param gameDataBO 数独数据
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户错误的数独格子信息
   */
  private ArrayList<SudokuGridInformationBO> findErrorGridInformation(GameDataBO gameDataBO, List<List<Integer>> userMatrix) {
    ArrayList<SudokuGridInformationBO> errorGridInformationList = new ArrayList<>();
    TwoDimensionalListUtils.forEachIfNotEquals(gameDataBO.getMatrix(), userMatrix,
        (i, j, topicValue, userValue) -> errorGridInformationList.add(new SudokuGridInformationBO(i, j, topicValue)));
    return errorGridInformationList;
  }

}
