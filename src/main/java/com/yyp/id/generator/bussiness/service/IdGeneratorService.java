package com.yyp.id.generator.bussiness.service;

import com.yyp.id.generator.bussiness.dto.IdGeneratorResult;
import org.springframework.stereotype.Service;

/**
 * id生成器服务
 */
@Service
public class IdGeneratorService {

    /**
     * 这里需要是线程安全的获取
     * @param generator id生成器标识 用来获取对应的id
     * @param lastId 为了更高效生成id传入的上次的生成结果 会使用到endId 如果为空则会从数据库获取一次再尝试组成结果
     * @return id
     */
    public IdGeneratorResult generateId(String generator, IdGeneratorResult lastId) {
        return null;
    }

}
