#! /bin/sh

PATH=/usr/local/java/jdk-current/bin/:/usr/local/ant/bin:$PATH
if [ -e $PWD/build-crawlersPP.xml ]; then
	OS=$PWD
elif [ -e $PWD/../build-crawlersPP.xml ]; then
	OS=$PWD/..
elif [ -e /usr/local/tomcat/violet/OS/build-crawlersPP.xml ]; then
	OS=/usr/local/tomcat/violet/OS
fi

CRAWLER="$1"
PORT_OPTION=""
if [ -n "$2" ]; then
	PORT_OPTION="-Dport=$2 "
fi
	
if [ -n "${CRAWLER}" ]; then
	nohup ant -f $OS/build-crawlersPP.xml -Ddaemon=${CRAWLER} ${PORT_OPTION} startDaemon > /usr/local/crawler/${CRAWLER}-${PORT}-std.log 2>&1 < /dev/null &
	sleep 1
	cat /usr/local/crawler/${CRAWLER}-${PORT}-std.log
else 
	echo "pas de cible"
fi
