## 分布式id生成器
> 1.常规使用使用http接口  
> 2.测试环境可使用代码中的docker部署测试  
> 3.如果没有分布式集中存储的需求,可使用[HumanIdGenerator.java](id-generator-facade%2Fsrc%2Fmain%2Fjava%2Fcom%2Fyyp%2Fid%2Fgenerator%2Ftools%2FHumanIdGenerator.java)来单机使用  
> 4.例如 2023010112121200000 中的20230101121212为格式化日期 后五位单机一秒可以有10万个id 默认配置是前两位从00-99可以有100个实例 每个实例一秒钟有1000个id可以产生可以根据实际情况再调整代码  

## 使用方法说明
待完善