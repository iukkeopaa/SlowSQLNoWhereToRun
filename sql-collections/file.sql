show variables like "long%";
set long_query_time=0.0001;


show variables like "slow%";
set global slow_query_log=ON;


show variables like '%max_connections%';
show variables like '%max_user_connections%';


[mysqld]
max_connections = 100
max_used_connections = 20


