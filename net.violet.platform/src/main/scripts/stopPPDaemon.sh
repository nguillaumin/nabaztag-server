#! /bin/sh

PATH=/usr/local/java/jdk-current/bin/:/usr/local/ant/bin:$PATH

if [ -e $PWD/build-crawlersPP.xml ]; then
	OS=$PWD
elif [ -e $PWD/../build-crawlersPP.xml ]; then
	OS=$PWD/..
elif [ -e /usr/local/tomcat/violet/OS/build-crawlersPP.xml ]; then
	OS=/usr/local/tomcat/violet/OS
fi

CRAWLER=$1
PORT_OPTION=""
if [ -n "$2" ]; then
	PORT_OPTION="-Dport=$2 "
fi

if [ -n "${CRAWLER}" ]; then
	ant -f $OS/build-crawlersPP.xml -Ddaemon=${CRAWLER} ${PORT_OPTION} stopDaemon 2>&1 < /dev/null | tee -a /usr/local/crawler/logs/${CRAWLER}.log
else 
	echo "pas de cible"
fi
