#!/bin/sh
SCRIPT_DIR=`dirname $0`

$SCRIPT_DIR/jabbercomponentapictl.sh start
$SCRIPT_DIR/jabbercomponentobjectsctl.sh start
$SCRIPT_DIR/jabbercomponentappletctl.sh start
