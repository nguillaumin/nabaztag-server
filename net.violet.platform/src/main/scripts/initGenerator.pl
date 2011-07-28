#!/usr/bin/perl -w

# run the perl script with arguments : 
#										1 . the sql query
#										2 . a to append at the end of the file or nothing to replace
#										3 . the file name (put automatically in  /usr/local/tomcat/violet/OS/net/violet/platform/datamodel/mock/)

$writeMode = (($#ARGV + 1 == 3 && $ARGV[1]  eq "a") ? ">>" : ">");

if ($#ARGV + 1 >= 2) {
	
	if ($ARGV[0] =~ m/^SELECT\s+(?:[^*]*\*|[^,\s]+,?\s*)+\s+FROM\s+[^;]+;$/i) {
		open (OUTPUT_FILE, "$writeMode /usr/local/tomcat/violet/OS/net/violet/platform/datamodel/mock/".(($#ARGV + 1 == 3) ? $ARGV[2] : $ARGV[1]));
		open(QUERY_RESULT, "mysql -udev -p123 -h192.168.1.11 bdCore  --default-character-set=UTF8 --execute=\"$ARGV[0]\" |")|| die "Could not get a feed back from MySQL\n";

		$lineNumber = 0;

		while ($line = <QUERY_RESULT>) {

			if ($lineNumber > 0 ) {
				$line =~ s/\t/,/g;
				$line =~ s/(?<=^)/\(/;
				$line =~ s/(?=$)/\)/;
				print OUTPUT_FILE "$line";
			} else {
				$lineNumber++;
			}
		}
	} else {
		print "Bad argument your query could not work, thus was not sent to MySQL\n";
	}
} else {
	print "Not arguments\n";
}
