#! /bin/sh

if [ -e $PWD/build-crawlers.xml ]; then
        OS=$PWD
elif [ -e $PWD/../build-crawlers.xml ]; then
        OS=$PWD/..
elif [ -e /usr/local/tomcat/violet/OS/build-crawlers.xml ]; then
        OS=/usr/local/tomcat/violet/OS
fi

if [ $# -eq 1 ]; then
	LIST=$1
else
	LIST=$OS/scripts/virtualobjectsLaunchList-`hostname`.txt
fi

if [ ! -e $LIST ]; then
	echo "$LIST n'existe pas. Syntaxe: startAllVirtualObjects.sh [fichier liste]"
	exit 1
fi

PORT="5560"
if [ -n "$2" ]; then
	PORT="$2"
fi

$OS/scripts/startDaemon.sh Shell ${PORT}
	
SERIALS=""
	
for serial in `cat ${LIST}`;do
#	echo ${serial}
	SERIALS="$serial;$SERIALS"
done

	ant -f $OS/build-crawlers.xml -Dserial=${SERIALS} -Dport=${PORT} -Dcommand=start virtualobject 2>&1 < /dev/null | tee -a /usr/local/crawler/VO_${name}-std.log