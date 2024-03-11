#!/bin/sh
export RABBIT_ADDRESSES=localhost:5672    
set STORE_TYPE=mysql
set MYSQL_USER=zikpin
set MYSQL_PASS=zipkin
java -jar ./zipkin-server-3.1.1-exec.jar
