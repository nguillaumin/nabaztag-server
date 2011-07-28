#!/bin/sh

if [ $# != "3" ]; then # 3 arguments are required
	echo "usage : $0 %SERVER %USER %PWD"
	exit 2
fi

SERVER=$1; USER=$2; PWD=$3

RESULT=`curl --silent --digest --user $USER:$PWD $SERVER/vl/rest/test/echo?TITLE="I'm the ECHO" 2>&1`

echo "Response : $RESULT"
