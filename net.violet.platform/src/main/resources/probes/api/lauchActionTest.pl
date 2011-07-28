#!/usr/bin/perl -w
use strict;
use File::Spec::Functions qw(rel2abs);
use File::Basename;
my $path = dirname(rel2abs($0));
 
sub syntax_error {
	print STDERR "Syntax error\n";
	print STDERR "Usage : /lauchActionTest.pl [-v|-q] server [test...test]\n";
	exit 2;
}

# Verification qu'il y a au moins un paramètre
if ($#ARGV < 0) {
	syntax_error;
}
	
my $server = shift @ARGV;
my $verbose = 0;
my $quiet = 0;
if (($server eq "-v") || ($server eq "-q")) {
	if ($#ARGV < 0) {
		syntax_error;
	}
	if ($server eq "-v") {
		$verbose = 1;
	} else {
		$quiet = 1;
	}
	$server = shift @ARGV;
}

my %applications = (
	'webui' => 'webui:private',
	'external' => 'external:private'
);

# last catch group
my @last_groups = ();
my $content_type;

my %tests = (
	'regex' => sub {
		my ($value, $output) = @_;
		@last_groups = ($output =~ m/$value/m);
		return @last_groups;
	},
	'size_greater_than' => sub {
		my ($value, $output) = @_;
        my $size = length($output);
        return ($size > $value);
    },
	'is_error' => sub {
		my ($value, $output) = @_;
		my $matches;
		if ($content_type eq 'text/yaml') {
			$matches = ($output =~ m/!!map:Error/m);
		} elsif ($content_type eq 'text/json') {
			$matches = ($output =~ m/"status":"error"/);
		} else {
			$matches = ($output =~ m/<status>\s+<string>error<\/string>\s+<\/status>/m);
		}
		return (($value eq "yes") xor !$matches);
    },
);

my $failed_count = 0;

my $timestamp = time;
my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time + 86400);
$year += 1900;
my $tomorrow = sprintf("$year-%02d-%02d", $mon, $mday);

sub default_test_config {
	my %test_config = ();
	# valeurs par défaut.
	$test_config{'is_error'} = 'no';
	$test_config{'content_type'} = 'text/xml';
	return %test_config;
}

sub perform_test {
	my ($test, %test_config) = @_;
	#figure out the login and the password.
	my $identification_opt = "";
	if (defined($test_config{'application'})) {
		my $application = $test_config{'application'};
		if (defined($applications{$application})) {
			$identification_opt = " --digest --user $applications{$application} ";
		} else {
			print "$test\twarning: unknown application $test_config{'application'}\n";
		}
	}

	#figure out the url
	my $url = "";
	if (defined($test_config{'url'})) {
		$url = $test_config{'url'};
	} else {
		print "$test\terror: url is not defined.\n";
		exit 1;
	}

	# Perform replacement.
	my $post_data;
	if (defined($test_config{'post_data'})) {
		$post_data = $test_config{'post_data'};
	} else {
		$post_data = 0;
	}
	if (@last_groups) {
		my $nb_groups = $#last_groups;
		for (my $index = 0; $index <= $nb_groups; $index++) {
			my $indexGr = $index + 1;
			$url =~ s/GROUP_$indexGr/$last_groups[$index]/g;
			if ($post_data) {
				$post_data =~ s/GROUP_$index/$last_groups[$index]/g;
			}
		}
	}
	if ($url =~ m/GROUP_([0-9]+)/) {
		print "$test warning: GROUP $1 in url was not substituted\n";
	}
	if ($post_data && $post_data  =~ m/GROUP_([0-9]+)/) {
		print "$test warning: GROUP $1 in post_data was not substituted\n";
        }
	
	#execute CURL command
	my $data_binary_opt = "";
	if ($post_data) {
		$data_binary_opt = "--data-binary '$post_data'";
	}
	$content_type = $test_config{'content_type'};
	my $command = "curl --silent $identification_opt --header \"Content-Type: $content_type;charset=UTF-8\" --header \"Accept: $content_type\" $data_binary_opt '$server/OS/rest/$url' -o -";
	$command =~ s/\n//g;

	open CURL, "$command |" or die "Error with curl for $test ($?)\n";
	my $curl_output = join '', <CURL>;
	close CURL or die "Error with curl for $test ($?, close)\n";

	if ($verbose) {
		print "$test command: $command\n";
	}

	# Perform the tests.
	my $failed = 0;
	foreach my $test_name (keys %tests) {
		if (defined($test_config{$test_name})) {
			my $result = $tests{$test_name}($test_config{$test_name}, $curl_output);
			if ($result) {
				if (!$quiet) {
					print "$test.$test_name: OK\n";
				}
			} else {
				print "$test.$test_name: FAILED\n";
				$failed = 1;
				$failed_count++;
			}
		}
	}

	if ((!$quiet && $failed) || $verbose) {
		print "$test output:\n----\n$curl_output\n----\n";
	}
}

sub run_test {
	my ($test) = @_;
	my %configs = ();
	my $config_name = 'root';
	my %root_config = default_test_config();
	my $test_config = \%root_config;
	my @config_list = ();
	open(FILE, $path . "/" . $test) || die("Inexisting file.");
	while(<FILE>) {
		my $line = $_;
		if ($line =~ m/^([^\s]+?)\s?=\s?(.*?)\n?$/) {
			my $key = $1;
			my $value = $2;
			$value =~ s/TIMESTAMP/$timestamp/g;
			$value =~ s/TOMORROW/$tomorrow/g;
			$$test_config{$key} = $value;
		} elsif ($line =~ m/^\[(.*?)\]\n?$/) {
			$configs{$config_name} = $test_config;
			my %new_config = ();
			foreach my $config_slot (keys %root_config) {
				$new_config{$config_slot} = $root_config{$config_slot};
			}
			$test_config = \%new_config;
			$config_name = $1;
			push @config_list, $config_name;
		} elsif (!($line =~ m/\s*(\#.*)?\n/)) {
			print "$test configuration error:\n$line\n";
		}
	}
	close(FILE);

	if ($#config_list >= 0) {
		# Enregisrement de la dernière config.
		$configs{$config_name} = $test_config;

		foreach $config_name (@config_list) {
			perform_test("$test:$config_name", %{$configs{$config_name}});
		}
	} else {
		perform_test($test, %{$test_config});
	}
}

if ($#ARGV < 0) { 
	my @directory = <*.test>;
	foreach(@directory) {
		my $test = $_;
		run_test($test);
	}
} else {
	foreach (@ARGV) {
		my $test = $_;
		run_test($test);
	}
}

if ($failed_count > 0) {
	exit 2;
} else {
	exit 0;
}
