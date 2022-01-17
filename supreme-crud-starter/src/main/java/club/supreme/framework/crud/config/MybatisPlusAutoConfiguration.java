package club.supreme.framework.crud.config;

import club.supreme.framework.crud.handler.SupremeIdentifierGeneratorHandler;
import club.supreme.framework.crud.handler.MybatisPlusAutoFillColumnHandler;
import club.supreme.framework.crud.handler.SupremeTenantLineHandler;
import club.supreme.framework.crud.injector.SupremeSqlInjector;
import club.supreme.framework.enums.crud.TenantIsolateLevelEnumI;
import club.supreme.framework.props.SupremeProperties;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * Mybatis-Plus配置类
 * @author Uncarbon
 */
@EnableTransactionManagement(
        proxyTargetClass = true
)
@Configuration
public class MybatisPlusAutoConfiguration {

    @Resource
    private SupremeProperties supremeProperties;

    @Resource
    private SupremeTenantLineHandler supremeTenantLineHandler;


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        /*
        https://mybatis.plus/guide/interceptor.html#mybatisplusinterceptor
        使用多个功能需要注意顺序关系,建议使用如下顺序

        多租户,动态表名
        分页,乐观锁
        sql性能规范,防止全表更新与删除
         */
        // 多租户 && 行级租户隔离级别
        if (Boolean.TRUE.equals(supremeProperties.getCrud().getTenant().getEnabled())
                && TenantIsolateLevelEnumI.LINE.equals(supremeProperties.getCrud().getTenant().getIsolateLevel())
        ) {
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(supremeTenantLineHandler));
        }

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.getDbType(supremeProperties.getCrud().getDbType()));
        // 设置sql的limit为无限制，默认是500
        paginationInnerInterceptor.setMaxLimit(-1L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 乐观锁
        if (Boolean.TRUE.equals(supremeProperties.getCrud().getOptimisticLock().getEnabled())) {
            interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        }

        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());


        return interceptor;
    }

    /**
     * 自定义ID生成器
     */
    @Bean
    public IdentifierGenerator supremeSnowflakeIdentifierGeneratorHandler() {
        return new SupremeIdentifierGeneratorHandler(supremeProperties);
    }

    /**
     * 字段自动填充
     */
    @Bean
    public MybatisPlusAutoFillColumnHandler mybatisPlusAutoFillColumnHandler() {
        return new MybatisPlusAutoFillColumnHandler();
    }

    /**
     * 行级租户
     */
    @Bean
    public SupremeTenantLineHandler supremeTenantLineHandler() {
        return new SupremeTenantLineHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public SupremeSqlInjector getMySqlInjector() {
        return new SupremeSqlInjector();
    }
}
