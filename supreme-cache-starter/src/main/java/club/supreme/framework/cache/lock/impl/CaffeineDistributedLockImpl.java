package club.supreme.framework.cache.lock.impl;

import club.supreme.framework.cache.lock.DistributedLock;
import club.supreme.framework.enums.cache.CacheType;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineDistributedLockImpl implements DistributedLock {
    @Override
    public RLock lock(String lockName, int holdDuration) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
        return null;
    }

    @Override
    public RLock lock(String lockName, TimeUnit unit, int holdDuration) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
        return null;
    }

    @Override
    public boolean tryLock(String lockName, int waitDuration, int holdDuration) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
        return true;
    }

    @Override
    public boolean tryLock(String lockName, TimeUnit unit, int waitDuration, int holdDuration) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
        return true;
    }

    @Override
    public void unlock(String lockName) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
    }

    @Override
    public void unlock(RLock lock) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
    }

    @Override
    public void unlockSafely(String lockName) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
    }

    @Override
    public void unlockSafely(RLock lock) {
        log.error("当前缓存模式为：[{}], 加锁失败", CacheType.CAFFEINE.name());
    }
}
