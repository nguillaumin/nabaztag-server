#!/bin/sh

# usage exemples :
# ./rest-get-action-probe.sh http://localhost:8080 public private objects/findByName/Aleister
# ./rest-get-action-probe.sh http://localhost:8080 public private applications/getPackage/2Aa73a9723

if [ $# != "4" ]; then # 4 arguments are required
	echo "usage : $0 %SERVER %USER %PWD %REST_ACTION"
	exit 2
fi

SERVER=$1; USER=$2; PWD=$3; REST_ACTION=$4

RESULT=`curl --silent --digest --user $USER:$PWD $SERVER/OS/rest/$REST_ACTION 2>&1`

echo "$RESULT"
