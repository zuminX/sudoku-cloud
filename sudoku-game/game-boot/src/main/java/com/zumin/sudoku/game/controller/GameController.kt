package com.zumin.sudoku.game.controller

import com.zumin.sudoku.common.web.ComRestController
import com.zumin.sudoku.game.enums.AnswerSituation
import com.zumin.sudoku.game.pojo.GameDataBO
import com.zumin.sudoku.game.pojo.GameLevel
import com.zumin.sudoku.game.pojo.SudokuGridInformationBO
import com.zumin.sudoku.game.pojo.UserAnswerInformationVO
import com.zumin.sudoku.game.service.GameNormalRecordService
import com.zumin.sudoku.game.service.GameRecordService
import com.zumin.sudoku.game.service.SudokuService
import com.zumin.sudoku.game.toUserAnswerInformationVO
import com.zumin.sudoku.game.utils.GameUtils
import com.zumin.sudoku.game.utils.generateSudokuFinal
import com.zumin.sudoku.game.validator.IsSudokuMatrix
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@ComRestController(path = ["/game"], tags = ["游戏API接口"])
class GameController(
  private val sudokuService: SudokuService,
  private val normalRecordService: GameNormalRecordService,
  private val recordService: GameRecordService,
  private val gameUtils: GameUtils,
) : GameBaseController() {

  @GetMapping("/generateTopic")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams(
    ApiImplicitParam(name = "level", value = "难度等级ID", dataTypeClass = Long::class, required = true),
    ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean::class, required = true))
  fun generateSudokuTopic(@RequestParam level: GameLevel, @RequestParam isRecord: Boolean): GameDataBO {
    val gameDataBO: GameDataBO = sudokuService.generateSudokuTopic(level, isRecord)
    insertGameRecord()
    return gameDataBO
  }

  @PostMapping("/help")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = MutableList::class, required = true)
  fun getHelp(@RequestBody @IsSudokuMatrix userMatrix: ArrayList<ArrayList<Int?>>): SudokuGridInformationBO? {
    return sudokuService.getHelp(userMatrix)
  }

  @PostMapping("/check")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = MutableList::class, required = true)
  fun checkSudokuData(@RequestBody @IsSudokuMatrix userMatrix: ArrayList<ArrayList<Int?>>): UserAnswerInformationVO {
    val userAnswerInformation = sudokuService.checkSudokuData(userMatrix)
    updateGameRecord(userMatrix, userAnswerInformation.situation)
    gameUtils.removeGameRecord()
    return userAnswerInformation.toUserAnswerInformationVO()
  }

  @GetMapping("/generateSudokuFinal")
  @ApiOperation("生成数独终盘")
  @ApiImplicitParam(name = "gameLevel", value = "难度级别", dataTypeClass = Long::class, required = true)
  fun generateSudokuFinal(@RequestParam("level") gameLevel: GameLevel): GameDataBO {
    return generateSudokuFinal(gameLevel.minEmpty!!, gameLevel.maxEmpty!!)
  }

  /**
   * 新增当前的游戏记录
   */
  @Transactional
  protected fun insertGameRecord() {
    if (gameUtils.isRecord()) {
      recordService.saveNowGame()
      normalRecordService.saveNowGame()
    }
  }

  /**
   * 更新当前的游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  protected fun updateGameRecord(inputMatrix: List<List<Int?>>, answerSituation: AnswerSituation) {
    if (gameUtils.isRecord()) {
      recordService.updateNowGameEndTime()
      normalRecordService.updateNowGame(inputMatrix, answerSituation)
    }
  }
}