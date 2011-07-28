#!/bin/bash

XML_FEED[0]="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\t<rss version=\"2.0\">\n\t<channel>\n\t\t<title>RSS - VO</title>\n\t\t<link></link>\n\t\t<description>RSS - VO</description>\n\t\t<language>fr</language>"
XML_FEED[1]="\t</channel>"

if [ -f $1 ] 
then
	cp $1 "$1.tmp"
else
	touch $1
	touch $1.tmp
fi
PUBDATE=$(date -R)
echo -e ${XML_FEED[0]} > $1
echo -e "\t\t<lastBuildDate>$PUBDATE</lastBuildDate>" >> $1
echo -e "\t\t<item>\n\t\t\t<title>Item of $PUBDATE</title>\n\t\t\t<pubDate>$PUBDATE</pubDate>\n\t\t\t<guid>$(date +%s)</guid>\n\t\t</item>" >> $1
tail -n +9 "$1.tmp" | head -n 45 | grep -v "</channel>" >> $1
echo -e ${XML_FEED[1]} >> $1

rm "$1.tmp"