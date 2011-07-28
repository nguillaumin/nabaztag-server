#! /bin/bash
OUTPUTFILE="$1"
FORMAT="$2"
shift 2

# Assumes ffmpeg is on the PATH
cat "$@" | ffmpeg -f ${FORMAT} -i - -f ${FORMAT} -acodec copy -y ${OUTPUTFILE} 2>/dev/null
