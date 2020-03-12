# Spring Cloud修炼

## Eureka、Consul、Zookeeper三个注册中心的异同点

| 组件名    | 语言 | CAP  | 服务健康检查 | 对外暴露接口 | Spring Cloud集成 |
| --------- | ---- | ---- | ------------ | ------------ | ---------------- |
| Eureka    | Java | AP   | 可配支持     | HTTP         | 已集成           |
| Consul    | Go   | CP   | 支持         | HTTP/DNS     | 已集成           |
| Zookeeper | Java | CP   | 支持         | 客户端       | 已集成           |

## netflix ribbon路由策略

- com.netflix.loadbalancer.**RoundRobinRule**  轮询
- com.netflix.loadbalancer.**RandomRule**  随机
- com.netflix.loadbalancer.**RetryRule**    先按照RoundRobinRule获取服务，如果获取服务失败则在制定时间内会进行重试，获取可用的服务
- com.netflix.loadbalancer.**WeightedResponseTimeRule**   对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
- com.netflix.loadbalancer.**BestAvailableRule**  会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
- com.netflix.loadbalancer.**AvailabilityFilteringRule**  先过滤掉故障实例，再选择并发较小的实例
- com.netflix.loadbalancer.**ZoneAvoidanceRule**  默认规则，复合判断server所在区域的性能和server可用性选择服务器

官方文档明确给出警告：

ribbon自定义配置类不能放在@ComponentScan所扫描的当前包下以及其子包

否则我们自定义的这个配置就会被所有的Ribbon客户端所共享，达不到特殊定制化的需要

## 全链路监控

zipkin的下载地址： http://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/ 