### 最大数据量

>《阿里巴巴Java开发手册》提出单表行数超过500万行或者单表容量超过2GB，才推荐分库分表。
> 
> 性能由综合因素决定，抛开业务复杂度，影响程度依次是硬件配置、MySQL配置、数据表设计、索引优化。500万这个值仅供参考，并非铁律。
> 


### 最大并发数


>并发数是指同一时刻数据库能处理多少个请求，由max_connections和max_user_connections决定。
>
>max_connections是指MySQL实例的最大连接数，上限值是16384，max_user_connections是指每个数据库用户的最大连接数。
> 
> MySQL会为每个连接提供缓冲区，意味着消耗更多的内存。
>
>如果连接数设置太高硬件吃不消，太低又不能充分利用硬件。
>
> 


### 查询耗时0.5秒


>建议将单次查询耗时控制在0.5秒以内，0.5秒是个经验值，源于用户体验的3秒原则

如果用户的操作3秒内没有响应，将会厌烦甚至退出。

响应时间=客户端UI渲染耗时+网络请求耗时+应用程序处理耗时+查询数据库耗时，0.5秒就是留给数据库1/6的处理时间。