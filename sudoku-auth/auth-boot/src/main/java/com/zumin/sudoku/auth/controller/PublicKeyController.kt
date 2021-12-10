package com.zumin.sudoku.auth.controller

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.zumin.sudoku.common.web.ComRestController
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey

@ComRestController(tags = ["获取公钥接口"])
class PublicKeyController(private val keyPair: KeyPair) {

  @ApiOperation("获取公钥")
  @GetMapping("/getPublicKey")
  fun loadPublicKey(): Map<String, Any> {
    val publicKey = keyPair.public as RSAPublicKey
    val key = RSAKey.Builder(publicKey).build()
    return JWKSet(key).toJSONObject()
  }
}