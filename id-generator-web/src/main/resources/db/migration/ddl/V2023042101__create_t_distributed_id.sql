create table t_distributed_id
(
    id bigint auto_increment comment '主键',
    id_generator_name varchar(255) not null comment 'id生成器的名称',
    id_generator_value bigint default 1 not null comment '当前id生成器的取值',
    id_generator_value_step int default 100 null comment '该id生成器的步进数量 默认100',
    version int default 0 not null comment '版本号(预留)',
    id_delete tinyint default 0 not null comment '数据有效标识 0代表未删除 1代表已删除',
    constraint t_distributed_id_pk
        primary key (id)
)
    comment '分布式id表';

create unique index t_distributed_idx_id_generator_name
    on t_distributed_id (id_generator_name);

