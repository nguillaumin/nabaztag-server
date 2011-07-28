#!/bin/sh
cd /usr/local/tomcat/violet/OS
CLASSPATH=build/WEB-INF/classes/

for lib in lib/*.jar; do
	CLASSPATH=$CLASSPATH:$lib
done

export CLASSPATH

java -Djava.net.preferIPv4Stack=true net.violet.platform.message.VirtualRabbit "$@"

