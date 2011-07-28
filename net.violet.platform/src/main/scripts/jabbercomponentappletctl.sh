#! /bin/sh

HOSTNAME=$(/bin/hostname)

if [ ${HOSTNAME} != "nabdev" -a ${HOSTNAME} != "prune" ]; then
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

COMMAND="$1"
DAEMON=JabberComponentManagerAppletPacket

case "$COMMAND" in
	start)
		nohup ant -f $OS/build-crawlers.xml -Djava.rmi.server.hostname=${admin_if} -Ddaemon=${DAEMON} startDaemon > /usr/local/crawler/${DAEMON}-std.log 2>&1 < /dev/null &
		sleep 1
		cat /usr/local/crawler/${DAEMON}-std.log
	;;
	stop)
		ant -f $OS/build-crawlers.xml -Djava.rmi.server.hostname=${admin_if} -Ddaemon=${DAEMON} stopDaemon 2>&1 < /dev/null | tee -a /usr/local/crawler/logs/${DAEMON}.log
	;;
	*)
		echo "$0 (start|stop)"
esac
