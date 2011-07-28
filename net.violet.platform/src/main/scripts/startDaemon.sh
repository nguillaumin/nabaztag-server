#! /bin/sh

HOSTNAME=$(/bin/hostname)

if [ ${HOSTNAME} != "nabdev" -a ${HOSTNAME} != "prune" -a ${HOSTNAME} != "lavande" -a ${HOSTNAME} != "mug" ]; then
  . /etc/sysconfig/network-scripts/ifcfg-eth3
  admin_if=${IPADDR}
fi

PATH=/usr/local/java/jdk-current/bin/:/usr/local/ant/bin:$PATH
if [ -e $PWD/build-crawlers.xml ]; then
	OS=$PWD
elif [ -e $PWD/../build-crawlers.xml ]; then
	OS=$PWD/..
elif [ -e /usr/local/tomcat/violet/OS/build-crawlers.xml ]; then
	OS=/usr/local/tomcat/violet/OS
fi

CRAWLER="$1"
PORT_OPTION=""
if [ -n "$2" ]; then
	PORT_OPTION="-Dport=$2 "
fi
	
if [ -n "${CRAWLER}" ]; then
	nohup ant -f $OS/build-crawlers.xml -Djava.rmi.server.hostname=${admin_if} -Ddaemon=${CRAWLER} ${PORT_OPTION} startDaemon > /usr/local/crawler/${CRAWLER}-${PORT}-std.log 2>&1 < /dev/null &
	sleep 1
	cat /usr/local/crawler/${CRAWLER}-${PORT}-std.log
else 
	echo "pas de cible"
fi
