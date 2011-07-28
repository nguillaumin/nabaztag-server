#! /bin/sh

OS=/usr/local/tomcat/violet/OS

PORT_OPTION=""
if [ -n "$2" ]; then
	PORT_OPTION="$2"
fi

if test $1; then
	RESULT=$(sh ${OS}/scripts/crawlerctl status $1 ${PORT_OPTION})
	STATUS=$(echo ${RESULT} | sed -n -e 's/\([A-Z]*\).*/\1/p')

	case "$STATUS" in
	UP)
		echo "CRAWLER OK : status : $STATUS"
		exit 0
	;;
	DOWN)
		echo "CRAWLER CRITICAL : status : $STATUS"
		exit 2
	;;
	SLOW)
		STATUS=$(echo ${RESULT} | sed -n -e 's/\([A-Z]*\)/\1/p')
		echo "CRAWLER SLOW : status : $STATUS"
		exit 0
	;;
	*)
		echo "CRAWLER CRITICAL : connection impossible au shell de crawler"
	;;
	esac
else
	echo "CRAWLER CRITICAL : vous devez indiquer le nom du crawler a verifier."
	exit 2
fi
