package club.supreme.module.knife4j.config;


import club.supreme.framework.constant.StrPool;
import club.supreme.framework.props.SupremeProperties;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.util.ClassUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/**
 * knife4j自动配置类
 * 参考http://events.jianshu.io/p/2f19c1863da0
 * @author Uncarbon
 * @author xiaoymin
 */
@EnableOpenApi
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class Knife4jAutoConfiguration {

    /**
     * 头标记名
     */
    @Value(value = "${sa-token.token-name:Authorization}")
    private String HEADER_TOKEN_NAME;

    /**
     * 最高属性
     */
    @Resource
    private SupremeProperties supremeProperties;


    /**
     * 集团rest api
     *
     * @return {@link Docket}
     */
    @Bean
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(this.apiInfo())
                .select()
                // 对所有API进行抓取
//                .apis(RequestHandlerSelectors.any())
                .apis(basePackage(supremeProperties.getKnife4j().getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                // 在调试页上附加请求头
                .securityContexts(CollUtil.newArrayList(this.securityContext()))
                .securitySchemes(CollUtil.newArrayList(this.apiKeyOfToken()))
                ;
    }

    /**
     * api信息
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(supremeProperties.getKnife4j().getTitle())
                .description(supremeProperties.getKnife4j().getDescription())
                .termsOfServiceUrl("")
                .contact(ApiInfo.DEFAULT_CONTACT)
                .version(supremeProperties.getKnife4j().getVersion())
                .build();
    }

    /**
     * 安全上下文
     *
     * @return {@link SecurityContext}
     */
    private SecurityContext securityContext() {
        List<SecurityReference> references = new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        references.add(new SecurityReference(HEADER_TOKEN_NAME, authorizationScopes));

        return SecurityContext.builder()
                .securityReferences(references)
                .build();
    }

    /**
     * api密匙令牌
     *
     * @return {@link ApiKey}
     */
    private ApiKey apiKeyOfToken() {
        return new ApiKey(HEADER_TOKEN_NAME, HEADER_TOKEN_NAME, "header");
    }


    /**
     * 基本包
     *
     * @param basePackage 基本包
     * @return {@link Predicate}<{@link RequestHandler}>
     */
    private Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    /**
     * 处理程序包
     *
     * @param basePackage 基本包
     * @return {@link Function}<{@link Class}<{@link ?}>, {@link Boolean}>
     */
    private Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> StrUtil.split(basePackage, StrPool.SEMICOLON).stream().anyMatch(ClassUtils.getPackageName(input)::startsWith);
    }

    /**
     * 声明类
     *
     * @param input 输入
     * @return {@link Optional}<{@link ?} {@link extends} {@link Class}<{@link ?}>>
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}
