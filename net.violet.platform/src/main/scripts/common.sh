#!/bin/sh
OS=`dirname $0`"/.."
COMMON=`dirname $0`"/../../Common"
DBENGINE=`dirname $0`"/../../DBEngine"

CLASSPATH=$OS/build/WEB-INF/classes/:$OS/build_new/tests/classes/

for lib in $COMMON/lib/*.jar; do
  CLASSPATH=$CLASSPATH:$lib
done
for lib in $DBENGINE/lib/*.jar; do
  CLASSPATH=$CLASSPATH:$lib
done
for lib in $OS/lib/*.jar; do
  CLASSPATH=$CLASSPATH:$lib
done


export CLASSPATH

java_cmd=`which java || which /usr/local/java/jdk-current/bin/java || which /opt/jdk/bin/java || which $JAVA_HOME/bin/java`

if [ ! -x "$java_cmd" ]; then
	echo "Could not find java!"
	exit 1
fi

java_cli () {
	$java_cmd -Djava.net.preferIPv4Stack=true -Dprocess_name=java_cli$$ -Dlog4j.configuration=log4j-cli.properties "$@"
}
