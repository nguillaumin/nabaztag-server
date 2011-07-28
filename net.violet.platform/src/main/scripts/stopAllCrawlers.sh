#! /bin/sh

if [ -e $PWD/build-crawlers.xml ]; then
        OS=$PWD
elif [ -e $PWD/../build-crawlers.xml ]; then
        OS=$PWD/..
elif [ -e /usr/local/tomcat/violet/OS/build-crawlers.xml ]; then
        OS=/usr/local/tomcat/violet/OS
fi

PORT="5556"
if [ -n "$2" ]; then
	PORT="$2"
fi
	
$OS/scripts/startDaemon.sh Shell ${PORT}
echo Stop Shell ${crawler}

