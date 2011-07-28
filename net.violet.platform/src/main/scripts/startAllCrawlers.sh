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

if [ $# -eq 1 ]; then
        LIST=$1
else
        LIST=$OS/scripts/crawlersLaunchList-`hostname`_$PORT
fi

if [ ! -e $LIST ]; then
        echo "$LIST n'existe pas. Syntaxe: startAllCrawlers.sh [fichier liste]"
        exit 1
fi



STARTSHELL=0
for crawler in `cat ${LIST}`;do
        if [ $STARTSHELL -eq 0 ]; then
                $OS/scripts/startDaemon.sh Shell ${PORT}
                sleep 3
                STARTSHELL=1
        fi
        echo ${crawler}
        $OS/scripts/crawlerctl start ${crawler} ${PORT}
done
