#!/bin/sh

if [ $# != '5' ]; then # 5 arguments are required
	echo "usage : ./postecho-probe.sh %SERVER %USER %PWD %REQ_FORMAT [form/xml/json/yaml] %RESP_FORMAT [xml/json/yaml]"
	exit 2
fi

SERVER=$1; USER=$2; PWD=$3; REQ_FORMAT=$4; RESP_FORMAT=$5

# Test the 'input' parameter to determine the value of posted data
if [ $REQ_FORMAT = 'form' ]; then
	CONTENT_TYPE='application/x-www-form-urlencoded'
	POST_DATA='formpost-data.txt'

elif [ $REQ_FORMAT = 'xml' ]; then
	CONTENT_TYPE='text/xml'
	POST_DATA='xmlpost-data.xml'

elif [ $REQ_FORMAT = 'json' ]; then
	CONTENT_TYPE='application/json'
	POST_DATA='jsonpost-data.txt'

elif [ $REQ_FORMAT = 'yaml' ]; then
	CONTENT_TYPE='text/yaml'
	POST_DATA='yamlpost-data.txt'

else
	echo "BAD REQ_FORMAT VALUE. Values are : 'form', 'xml', 'json', 'yaml'"
	exit 3
fi

# Test the 'output' parameter to determine the value of Accept Header
if [ $RESP_FORMAT = 'xml' ]; then
	HEADER='text/xml'

elif [ $RESP_FORMAT = 'json' ]; then
	HEADER='application/json'

elif [ $RESP_FORMAT = 'yaml' ]; then
	HEADER='text/yaml'

else
	echo "BAD RESP_FORMAT VALUE. Values are : 'xml', 'json', 'yaml'"
	exit 4
fi


echo "POSTING $REQ_FORMAT data to $SERVER/vl/rest/test/echo --user $USER:$PWD >>>>"

RESULT=`curl --silent --digest --user $USER:$PWD --header "Content-Type: $CONTENT_TYPE;charset=UTF-8" --header "Accept: $HEADER" --data-binary @$POST_DATA $SERVER/vl/rest/test/echo 2>&1`

echo "Response : $RESULT"
