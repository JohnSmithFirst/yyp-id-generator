package com.yyp.id.generator.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyp.id.generator.dao.entities.TDistributedId;

/**
 * @author johnsmith
 * @description 针对表【t_distributed_id(分布式id表)】的数据库操作Mapper
 * @createDate 2023-04-21 10:11:50
 * @Entity generator.TDistributedId
 */
public interface TDistributedIdMapper extends BaseMapper<TDistributedId> {

    boolean updateIdByEndId(String generator, long endId);

}




