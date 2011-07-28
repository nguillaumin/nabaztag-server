#!/bin/sh

if [ $# != "4" ]; then # 4 arguments are required
	echo "usage : ./xmlrpc-probe.sh %SERVER %USER %PWD %ACTION"
	exit 2
fi

SERVER=$1; USER=$2; PWD=$3; ACTION=$4

echo "POSTING $ACTION command to $SERVER/vl/xmlrpc --user $USER:$PWD >>>>"

RESULT=`curl -s --digest --user $USER:$PWD -d @xmlrpc-$ACTION-data.xml $SERVER/vl/xmlrpc 2>&1`

echo "Response : $RESULT"
