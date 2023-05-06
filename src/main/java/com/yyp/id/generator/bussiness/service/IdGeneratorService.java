package com.yyp.id.generator.bussiness.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyp.id.generator.bussiness.dto.IdGeneratorResult;
import com.yyp.id.generator.dao.entities.TDistributedId;
import com.yyp.id.generator.dao.service.TDistributedIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * id生成器服务
 */
@Service
@RequiredArgsConstructor
public class IdGeneratorService {

    private final TDistributedIdService tDistributedIdService;

    /**
     * 这里需要是线程安全的获取
     *
     * @param generator id生成器标识 用来获取对应的id
     * @param lastId    为了更高效生成id传入的上次的生成结果 会使用到endId 如果为空则会从数据库获取一次再尝试组成结果
     * @return id
     */
    public IdGeneratorResult generateId(String generator, IdGeneratorResult lastId) {
        return Optional.ofNullable(lastId)
                .map(lastIdResult -> this.getIdGeneratorResultFromDb(generator, lastIdResult.getEndId(), lastIdResult.getStep()))
                .orElseGet(() -> this.getIdGeneratorResultFromDb(generator));
    }

    private IdGeneratorResult getIdGeneratorResultFromDb(String generator) {
        //1.从数据库获取当前生成器的信息
        TDistributedId tDistributedId = getDistributeIdFromDb(generator);
        //2.有信息更新数据返回结果 没有信息插入数据并返回结果
        return Optional.ofNullable(tDistributedId)
                .map(distributedId -> {
                    long endId = distributedId.getIdGeneratorValue();
                    boolean updateFlag = tDistributedIdService.updateIdByEndId(generator, endId);
                    while (!updateFlag) {
                        distributedId = getDistributeIdFromDb(generator);
                        updateFlag = tDistributedIdService.updateIdByEndId(generator, endId);
                    }
                    long step = distributedId.getIdGeneratorValueStep();
                    return new IdGeneratorResult(endId, new AtomicLong(endId), endId + step, step);
                })
                .orElseGet(() -> {
                    TDistributedId entity = new TDistributedId();
                    entity.setIdGeneratorName(generator);
                    tDistributedIdService.save(entity);
                    entity = getDistributeIdFromDb(generator);
                    tDistributedIdService.updateIdByEndId(generator, entity.getIdGeneratorValue());
                    long endId = entity.getIdGeneratorValue();
                    long step = entity.getIdGeneratorValueStep();
                    return new IdGeneratorResult(endId, new AtomicLong(endId), endId + step, step);
                });
    }

    /**
     * 首先尝试快速获取id 获取失败走正常流程获取id
     *
     * @param generator id生成器
     * @param endId     上一次的结束id
     * @param step      id步进
     * @return id生成结果
     */
    private IdGeneratorResult getIdGeneratorResultFromDb(String generator, long endId, long step) {
        boolean updateFlag = tDistributedIdService.updateIdByEndId(generator, endId);
        return updateFlag
                ? new IdGeneratorResult(endId, new AtomicLong(endId), endId + step, step)
                : getIdGeneratorResultFromDb(generator);
    }


    /**
     * 从数据库查询出id生成器配置信息
     *
     * @param generator id生成器
     * @return id生成器配置
     */
    public TDistributedId getDistributeIdFromDb(String generator) {
        LambdaQueryWrapper<TDistributedId> queryWrapper = new LambdaQueryWrapper<>(TDistributedId.class);
        queryWrapper.eq(TDistributedId::getIdGeneratorName, generator);
        return tDistributedIdService.getOne(queryWrapper);
    }

}
