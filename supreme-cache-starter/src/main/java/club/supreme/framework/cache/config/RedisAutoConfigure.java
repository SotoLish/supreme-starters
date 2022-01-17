package club.supreme.framework.cache.config;

import club.supreme.framework.cache.lock.DistributedLock;
import club.supreme.framework.cache.lock.impl.RedisDistributedLockImpl;
import club.supreme.framework.cache.redis.RedisOps;
import club.supreme.framework.cache.redis.serializer.RedisObjectSerializer;
import club.supreme.framework.cache.repository.CacheOps;
import club.supreme.framework.cache.repository.CachePlusOps;
import club.supreme.framework.cache.repository.impl.RedisOpsImpl;
import club.supreme.framework.config.SupremePropertiesAutoConfiguration;
import club.supreme.framework.constant.StrPool;
import club.supreme.framework.enums.cache.SerializerType;
import club.supreme.framework.props.SupremeCacheProperties;
import club.supreme.framework.props.SupremeProperties;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * redis 配置类
 *
 * @author zuihou
 * @date 2019-08-06 10:42
 */
@ConditionalOnClass(RedisConnectionFactory.class)
@ConditionalOnProperty(prefix = SupremeProperties.PREFIX + StrUtil.DOT + SupremeCacheProperties.PREFIX, name = SupremeCacheProperties.PREFIX_TYPE, havingValue = "REDIS", matchIfMissing = true)
@AutoConfigureAfter(SupremePropertiesAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class RedisAutoConfigure {
    private final SupremeProperties supremeProperties;

    /**
     * 分布式锁
     *
     * @return 分布式锁
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributedLock redisDistributedLock() {
        return new RedisDistributedLockImpl();
    }

    /**
     * RedisTemplate配置
     *
     * @param factory redis链接工厂
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        setSerializer(factory, template, redisSerializer);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        SerializerType serializerType = supremeProperties.getCache().getSerializerType();
        if (SerializerType.JDK == serializerType) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            return new JdkSerializationRedisSerializer(classLoader);
        }
        return new RedisObjectSerializer();
    }

    private void setSerializer(RedisConnectionFactory factory, RedisTemplate template, RedisSerializer<Object> redisSerializer) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(redisSerializer);
        template.setValueSerializer(redisSerializer);
        template.setConnectionFactory(factory);
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        return template;
    }

    /**
     * redis 持久库
     *
     * @param redisOps the redis template
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheOps cacheOps(RedisOps redisOps) {
        log.warn("检查到缓存采用了 Redis模式");
        return new RedisOpsImpl(redisOps);
    }

    /**
     * redis 增强持久库
     *
     * @param redisOps the redis template
     * @return the redis repository
     */
    @Bean
    @ConditionalOnMissingBean
    public CachePlusOps cachePlusOps(RedisOps redisOps) {
        return new RedisOpsImpl(redisOps);
    }

    /**
     * 用于 @Cacheable 相关注解
     *
     * @param redisConnectionFactory 链接工厂
     * @return 缓存管理器
     */
    @Bean(name = "cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defConfig = getDefConf();
        defConfig.entryTtl(supremeProperties.getCache().getDef().getTimeToLive());

        Map<String, SupremeCacheProperties.Cache> configs = supremeProperties.getCache().getConfigs();
        Map<String, RedisCacheConfiguration> map = Maps.newHashMap();
        //自定义的缓存过期时间配置
        Optional.ofNullable(configs).ifPresent(config ->
                config.forEach((key, cache) -> {
                    RedisCacheConfiguration cfg = handleRedisCacheConfiguration(cache, defConfig);
                    map.put(key, cfg);
                })
        );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defConfig)
                .withInitialCacheConfigurations(map)
                .build();
    }

    private RedisCacheConfiguration getDefConf() {
        RedisCacheConfiguration def = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new RedisObjectSerializer()));
        return handleRedisCacheConfiguration(supremeProperties.getCache().getDef(), def);
    }

    private RedisCacheConfiguration handleRedisCacheConfiguration(SupremeCacheProperties.Cache redisProperties, RedisCacheConfiguration config) {
        if (Objects.isNull(redisProperties)) {
            return config;
        }
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.computePrefixWith(cacheName -> redisProperties.getKeyPrefix().concat(StrPool.COLON).concat(cacheName).concat(StrPool.COLON));
        } else {
            config = config.computePrefixWith(cacheName -> cacheName.concat(StrPool.COLON));
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisOps getRedisOps(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        return new RedisOps(redisTemplate, stringRedisTemplate, supremeProperties.getCache().getCacheNullVal());
    }
}
