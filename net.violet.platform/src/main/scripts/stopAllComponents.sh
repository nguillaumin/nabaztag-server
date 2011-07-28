#!/bin/sh
SCRIPT_DIR=`dirname $0`

$SCRIPT_DIR/jabbercomponentapictl.sh stop
$SCRIPT_DIR/jabbercomponentobjectsctl.sh stop
$SCRIPT_DIR/jabbercomponentappletctl.sh stop
