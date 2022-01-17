package club.supreme.framework.cache.config;

import club.supreme.framework.cache.lock.DistributedLock;
import club.supreme.framework.cache.lock.impl.CaffeineDistributedLockImpl;
import club.supreme.framework.cache.repository.CacheOps;
import club.supreme.framework.cache.repository.CachePlusOps;
import club.supreme.framework.cache.repository.impl.CaffeineOpsImpl;
import club.supreme.framework.config.SupremePropertiesAutoConfiguration;
import club.supreme.framework.props.SupremeCacheProperties;
import club.supreme.framework.props.SupremeProperties;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


/**
 * 内存缓存配置
 *
 * @author zuihou
 * @date 2019/08/07
 */
@Slf4j
@ConditionalOnProperty(prefix = SupremeProperties.PREFIX + StrUtil.DOT + SupremeCacheProperties.PREFIX, name = SupremeCacheProperties.PREFIX_TYPE, havingValue = "CAFFEINE")
@AutoConfigureAfter(SupremePropertiesAutoConfiguration.class)
@RequiredArgsConstructor
public class CaffeineAutoConfigure {

    private final SupremeProperties supremeProperties;

    /**
     * 为了解决演示环境启动报错而加的类
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributedLock caffeineDistributedLock() {
        return new CaffeineDistributedLockImpl();
    }

    /**
     * caffeine 持久库
     *
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheOps cacheOps() {
        log.warn("检查到缓存采用了 Caffeine(内存模式)");
        return new CaffeineOpsImpl();
    }

    /**
     * caffeine 增强持久库
     * 仅用于避免报错， 正式环境请勿使用
     *
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public CachePlusOps cachePlusOps() {
        return new CaffeineOpsImpl();
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        Caffeine caffeine = Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(500)
                .expireAfterWrite(supremeProperties.getCache().getDef().getTimeToLive())
                .maximumSize(supremeProperties.getCache().getDef().getMaxSize());
        cacheManager.setAllowNullValues(supremeProperties.getCache().getDef().isCacheNullValues());
        cacheManager.setCaffeine(caffeine);

        //配置了这里，就必须事先在配置文件中指定key 缓存才生效
//        Map<String, CustomCacheProperties.Redis> configs = cacheProperties.getConfigs();
//        Optional.ofNullable(configs).ifPresent((config)->{
//            cacheManager.setCacheNames(config.keySet());
//        });
        return cacheManager;
    }

}

