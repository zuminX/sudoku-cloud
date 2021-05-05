CREATE DATABASE IF NOT EXISTS `sudoku_cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `sudoku_cloud`;

DROP TABLE IF EXISTS `game_level`;
CREATE TABLE `game_level`
(
    `id`        bigint AUTO_INCREMENT COMMENT '数独难度ID',
    `name`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '难度名',
    `min_empty` tinyint                                                      NOT NULL COMMENT '最小的空缺格子数',
    `max_empty` tinyint                                                      NOT NULL COMMENT '最大的空缺格子数',
    `sort`      int DEFAULT 0 COMMENT '难度排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `game_level`
VALUES (null, '简单模式', 30, 35, 0),
       (null, '普通模式', 35, 40, 1),
       (null, '困难模式', 40, 45, 2),
       (null, '困难+模式', 45, 50, 3);


DROP TABLE IF EXISTS `game_record`;
CREATE TABLE `game_record`
(
    `id`         bigint AUTO_INCREMENT COMMENT '数独记录的ID',
    `matrix`     char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数独矩阵',
    `holes`      char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '空缺的数独',
    `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time`   datetime COMMENT '结束时间',
    `level_id`   bigint COMMENT '数独难度ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `game_record`
VALUES (null, '239187465586394127471625398942761583318459276657832914894516732723948651165273849',
        '010100110110111000011011100110110001100100100110000000000011000000001000100101000',
        '2020-05-24 22:00:00',
        '2020-05-24 22:10:00', 1);


DROP TABLE IF EXISTS `game_normal_record`;
CREATE TABLE `game_normal_record`
(
    `id`        bigint AUTO_INCREMENT COMMENT '游戏记录的ID',
    `answer`    char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '输入的数独矩阵',
    `situation` tinyint NOT NULL DEFAULT 0 COMMENT '回答情况',
    `user_id`   bigint COMMENT '用户ID',
    `record_id` bigint COMMENT '数独记录ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `game_normal_record`
VALUES (null, '239187465586394127471625398942761583318459276657832914894516732723948651165273849', 0, 1, 1);


DROP TABLE IF EXISTS `game_race_information`;
CREATE TABLE `game_race_information`
(
    `id`          bigint AUTO_INCREMENT COMMENT '竞赛信息的ID',
    `title`       varchar(64)                                               NOT NULL COMMENT '竞赛的标题',
    `description` varchar(512) COMMENT '竞赛的描述',
    `matrix`      char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数独矩阵',
    `holes`       char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '空缺的数独',
    `start_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time`    datetime COMMENT '结束时间',
    `user_id`     bigint COMMENT '创建用户的ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `game_race_record`;
CREATE TABLE `game_race_record`
(
    `id`                  bigint AUTO_INCREMENT COMMENT '竞赛记录的ID',
    `answer`              char(81) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '输入的数独矩阵',
    `situation`           tinyint  DEFAULT 1 COMMENT '回答情况',
    `start_time`          datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time`            datetime COMMENT '结束时间',
    `user_id`             bigint NOT NULL COMMENT '用户ID',
    `race_information_id` bigint NOT NULL COMMENT '竞赛信息的ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;
