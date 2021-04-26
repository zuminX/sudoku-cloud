CREATE DATABASE IF NOT EXISTS `sudoku_cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `sudoku_cloud`;

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `resource_ids`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `client_secret`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `scope`                   varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `authorities`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `access_token_validity`   int                                                      NULL DEFAULT NULL,
    `refresh_token_validity`  int                                                      NULL DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `autoapprove`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `oauth_client_details`
VALUES ('sudoku-ums', '', '2233qqaazz', 'all', 'password,client_credentials,refresh_token,
authorization_code', '', NULL, 3600, 7200, NULL, 'true');

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                bigint AUTO_INCREMENT COMMENT '用户ID',
    `username`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户名',
    `password`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `nickname`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '昵称',
    `avatar`            varchar(128) DEFAULT NULL COMMENT '头像地址',
    `create_time`       datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `recent_login_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
    `enabled`           tinyint      DEFAULT 1 COMMENT '是否启用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_user`
VALUES (null, 'test1', '$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试管理员', NULL, NOW(), NOW(), 1),
       (null, 'test2', '$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试用户', NULL, NOW(), NOW(), 1);


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_user_role`
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 1);


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`       bigint AUTO_INCREMENT COMMENT '角色ID',
    `name`     varchar(32) NOT NULL COMMENT '角色名',
    `nickname` varchar(32) DEFAULT NULL COMMENT '角色昵称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `sys_role`
VALUES (1, 'ROLE_USER', '用户'),
       (2, 'ROLE_ADMIN', '管理员');


DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`
(
    `id`     bigint AUTO_INCREMENT COMMENT '资源ID',
    `perms`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
    `method` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci        NULL DEFAULT NULL COMMENT '请求方法类型（POST/PUT/DELETE/PATCH）',
    `name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '资源名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_resource`
VALUES (1, '/admin/**', '*', '系统后台管理'),
       (2, '/game/gameRace/publishPublicRace', '*', '发布数独竞赛'),
       (3, '/game/statistics/**', '*', '游戏统计信息'),
       (4, '/game/game/generateSudokuFinal', '*', '生成数独终盘'),
       (5, '/game/user/historyGameRecordById', '*', '用户历史游戏记录'),
       (6, '/game/user/gameInformationById', '*', '用户游戏信息')
;


DROP TABLE IF EXISTS `sys_resource_role`;
CREATE TABLE `sys_resource_role`
(
    `id`          bigint AUTO_INCREMENT COMMENT '资源角色ID',
    `resource_id` bigint NOT NULL COMMENT '资源ID',
    `role_id`     bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_resource_role`
VALUES (1, 1, 2),
       (2, 2, 2),
       (3, 3, 2),
       (4, 4, 2),
       (5, 5, 2),
       (6, 6, 2)
;