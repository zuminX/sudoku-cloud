package com.zumin.sudoku.game.controller;

import com.zumin.sudoku.common.web.annotation.ComRestController;
import com.zumin.sudoku.game.convert.UserAnswerInformationConvert;
import com.zumin.sudoku.game.enums.AnswerSituation;
import com.zumin.sudoku.game.pojo.bo.GameDataBO;
import com.zumin.sudoku.game.pojo.bo.SudokuGridInformationBO;
import com.zumin.sudoku.game.pojo.bo.UserAnswerInformationBO;
import com.zumin.sudoku.game.pojo.entity.GameLevel;
import com.zumin.sudoku.game.pojo.vo.UserAnswerInformationVO;
import com.zumin.sudoku.game.service.GameNormalRecordService;
import com.zumin.sudoku.game.service.GameRecordService;
import com.zumin.sudoku.game.service.SudokuService;
import com.zumin.sudoku.game.utils.GameUtils;
import com.zumin.sudoku.game.utils.SudokuBuilder;
import com.zumin.sudoku.game.validator.IsSudokuMatrix;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@ComRestController(path = "/game", tags = "游戏API接口")
public class GameController extends GameBaseController {

  private final SudokuService sudokuService;

  private final GameNormalRecordService normalRecordService;

  private final GameRecordService recordService;

  private final UserAnswerInformationConvert userAnswerInformationConvert;

  private final GameUtils gameUtils;

  @GetMapping("/generateTopic")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long.class, required = true),
      @ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean.class, required = true)})
  public GameDataBO generateSudokuTopic(@RequestParam GameLevel level,
      @RequestParam @NotNull(message = "是否记录游戏不能为空") Boolean isRecord) {
    GameDataBO gameDataBO = sudokuService.generateSudokuTopic(level, isRecord);
    insertGameRecord();
    return gameDataBO;
  }

  @PostMapping("/help")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public SudokuGridInformationBO getHelp(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    return sudokuService.getHelp(userMatrix);
  }

  @PostMapping("/check")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public UserAnswerInformationVO checkSudokuData(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    UserAnswerInformationBO userAnswerInformation = sudokuService.checkSudokuData(userMatrix);
    updateGameRecord(userMatrix, userAnswerInformation.getSituation());
    gameUtils.removeGameRecord();
    return userAnswerInformationConvert.boToVo(userAnswerInformation);
  }

  @GetMapping("/generateSudokuFinal")
  @ApiOperation("生成数独终盘")
  @ApiImplicitParam(name = "gameLevel", value = "难度级别", dataTypeClass = Long.class, required = true)
  public GameDataBO generateSudokuFinal(@RequestParam("level") GameLevel gameLevel) {
    return SudokuBuilder.generateSudokuFinal(gameLevel.getMinEmpty(), gameLevel.getMaxEmpty());
  }

  /**
   * 新增当前的游戏记录
   */
  @Transactional
  protected void insertGameRecord() {
    if (gameUtils.isRecord()) {
      recordService.saveNowGame();
      normalRecordService.saveNowGame();
    }
  }

  /**
   * 更新当前的游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  protected void updateGameRecord(List<List<Integer>> inputMatrix, AnswerSituation answerSituation) {
    if (gameUtils.isRecord()) {
      recordService.updateNowGameEndTime();
      normalRecordService.updateNowGame(inputMatrix, answerSituation);
    }
  }
}
