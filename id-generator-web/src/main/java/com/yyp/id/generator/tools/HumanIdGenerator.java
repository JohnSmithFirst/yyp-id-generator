package com.yyp.id.generator.tools;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 人类可读id生成器
 * 思路来自雪花算法的id生成方法 便于人看 可以提供每一秒种1000个id 超过1000个等待到下一秒获取
 */
public class HumanIdGenerator {

    /**
     * 实例编号 00到99 共100个 占用timeFormat倒数第四第五位 可做成配置项
     */
    private final String maxInstancesNumber = "01";
    private final String timeFormat = String.format("yyyyMMddHHmmss%s000", maxInstancesNumber);

    private final Map<String, IdGeneratorContainer> cache = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();


    /**
     * 获取一个基于时间的id
     *
     * @return
     */
    public Long acquireTimeLong() {
        lock.lock();
        try {
            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
            clearCache(format);
            IdGeneratorContainer idGeneratorContainer = cache.get(format);
            Long nextId = null;
            while ((nextId = Optional.ofNullable(idGeneratorContainer)
                    .map(IdGeneratorContainer::nextId)
                    .orElseGet(() -> {
                        String elseFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
                        IdGeneratorContainer idGeneratorContainer1 = new IdGeneratorContainer(Long.parseLong(elseFormat));
                        cache.put(elseFormat, idGeneratorContainer1);
                        return idGeneratorContainer1.nextId();
                    })) < 0L) {
                try {
                    //id发放完了 等待下一秒发放 sleep 0秒 主动释放cpu
                    TimeUnit.SECONDS.sleep(0L);
                    idGeneratorContainer = cache.get(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return nextId;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param holdCacheKey 不清除的cacheKey
     */
    private void clearCache(String holdCacheKey) {
        IdGeneratorContainer idGeneratorContainer = cache.get(holdCacheKey);
        cache.clear();
        Optional.ofNullable(idGeneratorContainer)
                .ifPresent(idGeneratorContainer1 -> cache.put(holdCacheKey, idGeneratorContainer1));
    }
}

@Data
class IdGeneratorContainer {
    private AtomicLong atomicLong;
    private Integer count = 0;

    IdGeneratorContainer(long beginId) {
        atomicLong = new AtomicLong(beginId);
    }

    /**
     * @return -1 要等待至下一秒 当前秒的id发放完了
     */
    public long nextId() {
        return count++ < 1000 ? atomicLong.getAndIncrement() : -1L;
    }
}
