package com.zumin.sudoku.common.web.config

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j
import com.zumin.sudoku.common.core.CommonCoreProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.OAuthBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableKnife4j
@EnableSwagger2
@EnableConfigurationProperties(value = [CommonWebProperties::class, CommonCoreProperties::class])
class SwaggerConfiguration(
  private val commonWebProperties: CommonWebProperties,
  private val commonCoreProperties: CommonCoreProperties,
) {

  @Bean
  fun restApi(): Docket {
    val oAuth = OAuthBuilder()
      .name("oauth2")
      .grantTypes(listOf(ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl)))
      .build()
    val scopes = arrayOf(
      AuthorizationScope("read", "read  resources"),
      AuthorizationScope("write", "write resources"),
      AuthorizationScope("reads", "read all resources"),
      AuthorizationScope("writes", "write all resources")
    )
    val securityContext = SecurityContext(listOf(SecurityReference("oauth2", scopes)), PathSelectors.ant("/**"))
    return Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("${commonCoreProperties.projectPackage}.controller"))
      .paths(PathSelectors.any())
      .build()
      .securityContexts(listOf(securityContext))
      .securitySchemes(listOf(oAuth))
      .apiInfo(apiInfo())
  }

  private fun apiInfo(): ApiInfo {
    val (title, description) = commonWebProperties.api
    return ApiInfoBuilder().title(title)
      .description("<div style='font-size:14px;color:red;'>$description</div>")
      .contact(Contact("yl", "https://github.com/zuminX", "1215287120@qq.com"))
      .license("Open Source")
      .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
      .version("1.0.0")
      .build()
  }
}

private const val passwordTokenUrl = "http://localhost:9999/sudoku-auth/oauth/token"