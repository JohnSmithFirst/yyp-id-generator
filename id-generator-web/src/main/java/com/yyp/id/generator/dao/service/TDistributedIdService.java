package com.yyp.id.generator.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyp.id.generator.dao.entities.TDistributedId;

/**
 * @author johnsmith
 * @description 针对表【t_distributed_id(分布式id表)】的数据库操作Service
 * @createDate 2023-04-21 10:11:50
 */
public interface TDistributedIdService extends IService<TDistributedId> {

    /**
     * @param generator id生成器
     * @param endId     当前结束id
     * @return true 成功 false 失败
     */
    boolean updateIdByEndId(String generator, long endId);
}
