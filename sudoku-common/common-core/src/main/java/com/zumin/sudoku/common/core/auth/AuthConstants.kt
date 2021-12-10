package com.zumin.sudoku.common.core.auth

/**
 * 认证请求头key
 */
const val AUTHORIZATION_KEY = "Authorization"

/**
 * JWT令牌前缀
 */
const val AUTHORIZATION_PREFIX = "bearer "

/**
 * Basic认证前缀
 */
const val BASIC_PREFIX = "Basic "

/**
 * JWT载体key
 */
const val JWT_PAYLOAD_KEY = "payload"

/**
 * JWT ID 唯一标识
 */
const val JWT_JTI = "jti"

/**
 * JWT ID 唯一标识
 */
const val JWT_EXP = "exp"

const val CLIENT_DETAILS_FIELDS =
  ("""client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove""")

const val BASE_CLIENT_DETAILS_SQL = "select $CLIENT_DETAILS_FIELDS from oauth_client_details"

const val FIND_CLIENT_DETAILS_SQL = "$BASE_CLIENT_DETAILS_SQL order by client_id"

const val SELECT_CLIENT_DETAILS_SQL = "$BASE_CLIENT_DETAILS_SQL where client_id = ?"

/**
 * JWT存储权限前缀
 */
const val AUTHORITY_PREFIX = "ROLE_"

/**
 * JWT存储权限属性
 */
const val JWT_AUTHORITIES_KEY = "authorities"

/**
 * 数独系统用户客户端ID
 */
const val USER_CLIENT_ID = "sudoku-ums"

/**
 * 数独系统微信小程序客户端ID
 */
const val WEAPP_CLIENT_ID = "sudoku-weapp"

const val LOGOUT_PATH = "/sudoku-auth/oauth/logout"
