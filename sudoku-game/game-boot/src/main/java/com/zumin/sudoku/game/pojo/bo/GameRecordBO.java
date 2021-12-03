package com.zumin.sudoku.game.pojo.bo;

import com.zumin.sudoku.common.core.utils.PublicUtils;
import com.zumin.sudoku.game.utils.SudokuUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

/**
 * 数独记录业务类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRecordBO implements Serializable {

  private static final long serialVersionUID = 2502730297414700117L;

  /**
   * 数独记录的ID
   */
  private Long id;

  /**
   * 数独数据
   */
  private GameDataBO gameData;

  /**
   * 开始时间
   */
  private LocalDateTime startTime;

  /**
   * 结束时间
   */
  private LocalDateTime endTime;

  /**
   * 数独难度ID
   */
  private Long levelId;

  /**
   * 是否记录
   */
  @Transient
  private boolean isRecord;

}
