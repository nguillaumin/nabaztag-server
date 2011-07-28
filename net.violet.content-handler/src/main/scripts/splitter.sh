#! /bin/bash
OUTPUTFILE="$1"
FROM="$2"
TO="$3"
FORMAT="$4"

/usr/local/bin/ffmpeg -f ${FORMAT} -ss ${FROM} -t ${TO} -i - -f ${FORMAT} -acodec copy -y ${OUTPUTFILE} 2>/dev/null
