#!/bin/sh
export RABBIT_ADDRESSES=localhost:5672    
java -jar ./zipkin-server-3.1.1-exec.jar
