#! /bin/bash

PATH2FILE="$1"
INPUTFORMAT="$2"

if [ "$INPUTFORMAT" = "s16le" ]; then
	INPUTFORMAT="$INPUTFORMAT $3 $4"
	shift 2
fi

TMPFILE="$3"
shift 3

FADEOUT=""

if [ "$1" = "fade" ]; then
	FADEOUT="$1 $2 $3 $4 $5"
	shift 5
fi

OUTPUTCOMMAND="$@"

/usr/local/bin/ffmpeg -f ${INPUTFORMAT} -i ${PATH2FILE} -f wav -vn -y ${TMPFILE} 2> /dev/null && rm -f ${PATH2FILE} && /usr/local/bin/sox -V0 -t wav -v $(/usr/local/bin/sox ${TMPFILE} -n stat -v 2>&1) ${TMPFILE} -t wav - ${FADEOUT} | /usr/local/bin/ffmpeg -f wav -i - -f ${OUTPUTCOMMAND} -vn -y ${PATH2FILE} 2> /dev/null
