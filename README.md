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

# Spring Cloud Alibaba

地址:  https://github.com/alibaba/spring-cloud-alibaba/blob/master/README-zh.md 

## Nacos

下载地址:  https://github.com/alibaba/nacos/releases/tag/1.2.0-beta.0 

说明文档：  https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html 

## Sentinel

下载地址： https://github.com/alibaba/Sentinel/releases 

说明文档：  [https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D](https://github.com/alibaba/Sentinel/wiki/介绍) 

## Seata

官网： https://seata.io/zh-cn/ 

### Seata术语

TC-事务协调者

维护全局和分支事务的状态，驱动全局事务提交或回滚

TM-事务管理器

定于全局事务的范围：开始全局事务、提交或回滚全局事务

RM-资源管理器

管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚

Seata安装



修改配置文件file.conf

```json
service {
  #transaction service group mapping
  vgroup_mapping.my_test_tx_group = "fsp_tx_group"
  #only support when registry.type=file, please don't set multiple addresses
  default.grouplist = "127.0.0.1:8091"
  #disable seata
  disableGlobalTransaction = false
}

## transaction log store, only used in seata-server
store {
  ## store mode: file、db
  mode = "db"

  ## file store property
  file {
    ## store location dir
    dir = "sessionStore"
  }

  ## database store property
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/db2020?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8"
    user = "root"
    password = "root"
  }
}
```

创建数据库seata

创建表结构，${seata}\conf\



修改registry.conf

```json
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos"

  nacos {
    serverAddr = "localhost:8848"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}

```

先启动nacos在启动seata