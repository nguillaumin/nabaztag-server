#!/bin/sh
OS=`dirname $0`"/../"
. $OS/scripts/common.sh

HOSTNAME=`hostname`

COUNT="1"
if [ -n "$4" ]; then
	COUNT="$4"
fi

for i in `seq 1 ${COUNT}`;
do
nohup ${java_cmd} -Djava.net.preferIPv4Stack=true -Dcrawler=TestCache_${HOSTNAME}_${i} -Dlog4j.configuration=log4j-crawlers.properties net.violet.platform.datamodel.cache.CacheNodeTest "$@" > /usr/local/crawler/logs/TestCache_${HOSTNAME}_${i}.log 2>&1 < /dev/null &
done
