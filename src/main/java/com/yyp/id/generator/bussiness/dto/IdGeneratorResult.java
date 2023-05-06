package com.yyp.id.generator.bussiness.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class IdGeneratorResult {
    /**
     * id初始值
     */
    private AtomicLong beginId;
    /**
     * id结束值
     */
    private long endId;

    /**
     *
     * @return 当前id的值 -1是已超过范围 需要重新从接口获取
     */
    public Long currentId(){
        long result = beginId.getAndIncrement();
        return result < endId ? result : -1;
    }

}
