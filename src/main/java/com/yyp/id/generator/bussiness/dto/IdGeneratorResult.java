package com.yyp.id.generator.bussiness.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class IdGeneratorResult {
    /**
     * id初始值
     */
    private final Long beginId;

    private final AtomicLong currentId;
    /**
     * id结束值
     */
    private final Long endId;
    /**
     * 步进
     */
    private final Long step;

    /**
     * @return 当前id的值 -1是已超过范围 需要重新从接口获取
     */
    public long currentId() {
        long result = currentId.getAndIncrement();
        return result < endId ? result : -1;
    }

}
