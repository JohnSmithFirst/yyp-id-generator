package com.yyp.id.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyp.id.generator.entities.TDistributedId;
import com.yyp.id.generator.service.TDistributedIdService;
import com.yyp.id.generator.mapper.TDistributedIdMapper;
import org.springframework.stereotype.Service;

/**
* @author johnsmith
* @description 针对表【t_distributed_id(分布式id表)】的数据库操作Service实现
* @createDate 2023-04-21 10:11:49
*/
@Service
public class TDistributedIdServiceImpl extends ServiceImpl<TDistributedIdMapper, TDistributedId>
    implements TDistributedIdService{

}




