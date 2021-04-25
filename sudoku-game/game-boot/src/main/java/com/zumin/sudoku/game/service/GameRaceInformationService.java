package com.zumin.sudoku.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zumin.sudoku.common.web.domain.LocalDateTimeRange;
import com.zumin.sudoku.common.web.utils.SecurityUtils;
import com.zumin.sudoku.game.convert.GameRaceInformationConvert;
import com.zumin.sudoku.game.enums.GameStatusCode;
import com.zumin.sudoku.game.exception.GameRaceException;
import com.zumin.sudoku.game.mapper.GameRaceInformationMapper;
import com.zumin.sudoku.game.pojo.body.GameRaceInformationBody;
import com.zumin.sudoku.game.pojo.entity.GameRaceInformation;
import com.zumin.sudoku.game.pojo.vo.GameRaceInformationVO;
import com.zumin.sudoku.game.utils.SudokuUtils;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameRaceInformationService extends ServiceImpl<GameRaceInformationMapper, GameRaceInformation> {

  /**
   * 比赛的最短时间
   */
  private static final Duration MINIMUM_RACE_DURATION = Duration.ofMinutes(5);

  private final GameRaceInformationConvert gameRaceInformationConvert;

  /**
   * 新增公开的竞赛
   *
   * @param gameRaceInformationBody 竞赛内容信息对象
   */
  @Transactional
  public void addPublicRace(GameRaceInformationBody gameRaceInformationBody) {
    checkSudokuMatrix(gameRaceInformationBody.getMatrix());
    checkSudokuHoles(gameRaceInformationBody.getHoles());
    checkRaceTime(gameRaceInformationBody.getRaceTimeRange());

    save(gameRaceInformationConvert.convert(gameRaceInformationBody, SecurityUtils.getUserId()));
  }

  /**
   * 获取公开的数独游戏竞赛信息
   *
   * @return 竞赛信息显示层对象
   */
  public List<GameRaceInformationVO> listPublicRace() {
    return baseMapper.selectAllByEndTimeBefore(LocalDateTime.now().plusDays(1L));
  }

  /**
   * 检查竞赛时间
   * <p>
   * 限制竞赛的时长至少为MINIMUM_RACE_DURATION，且距离竞赛的结束时间也至少为MINIMUM_RACE_DURATION
   *
   * @param raceTimeRange 竞赛时间范围
   */
  private void checkRaceTime(LocalDateTimeRange raceTimeRange) {
    LocalDateTime start = raceTimeRange.getStart(), end = raceTimeRange.getEnd();
    if (start.plus(MINIMUM_RACE_DURATION).compareTo(end) > 0 || LocalDateTime.now().plus(MINIMUM_RACE_DURATION).compareTo(end) > 0) {
      throw new GameRaceException(GameStatusCode.RACE_DURATION_TOO_SHORT);
    }
  }

  /**
   * 校验题目空缺数组的合法性
   *
   * @param holes 题目空缺数组
   */
  private void checkSudokuHoles(List<List<Boolean>> holes) {
    holes.stream().flatMap(Collection::stream).filter(Objects::isNull).forEach(aBoolean -> {
      throw new GameRaceException(GameStatusCode.RACE_SUDOKU_HOLES_Illegal);
    });
  }

  /**
   * 校验数独矩阵的合法性
   *
   * @param matrix 数独矩阵
   */
  private void checkSudokuMatrix(List<List<Integer>> matrix) {
    if (!SudokuUtils.checkSudokuValidity(matrix)) {
      throw new GameRaceException(GameStatusCode.RACE_SUDOKU_MATRIX_Illegal);
    }
  }
}
