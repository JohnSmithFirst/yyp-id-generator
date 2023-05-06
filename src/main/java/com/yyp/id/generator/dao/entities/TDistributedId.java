package com.yyp.id.generator.dao.entities;

import java.io.Serializable;
import lombok.Data;

/**
 * 分布式id表
 * @TableName t_distributed_id
 */
@Data
public class TDistributedId implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * id生成器的名称
     */
    private String idGeneratorName;

    /**
     * 当前id生成器的取值
     */
    private Long idGeneratorValue;

    /**
     * 该id生成器的步进数量 默认100
     */
    private Integer idGeneratorValueStep;

    /**
     * 版本号(预留)
     */
    private Integer version;

    /**
     * 数据有效标识 0代表未删除 1代表已删除
     */
    private Integer idDelete;

}