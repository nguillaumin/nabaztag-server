#!/bin/sh
cd /usr/local/tomcat/violet/OS
CLASSPATH=build/WEB-INF/classes/

for lib in lib/*.jar; do
	CLASSPATH=$CLASSPATH:$lib
done

export CLASSPATH

java -agentpath:/home/crawler/yjp-7.0.10/bin/linux-x86-32/libyjpagent.so -Dcrawler=HadoopCopy -Dlog4j.configuration=log4j-crawlers.properties -Djava.net.preferIPv4Stack=true net.violet.platform.files.HadoopCrawler "$@"

