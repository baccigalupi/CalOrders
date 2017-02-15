#!/usr/bin/perl -w
use strict;
use File::Copy qw(move);

my $domainFile=$ARGV[0];

my $databaseName=$ENV{databasename};
my $databaseUser=$ENV{databaseuser};
my $databasePassword=$ENV{databasepassword};
my $databaseHostname=$ENV{databasehostname};
my $databasePort=$ENV{databaseport};

move "$domainFile", "${domainFile}.bak";
open INPUT, "<${domainFile}.bak";
open OUTPUT, ">$domainFile";

while (<INPUT>)
{
    s/name="DatabaseName" value="calordersdb"/name="DatabaseName" value="$databaseName"/g;
    s/name="User" value="CalOrdersDba"/name="User" value="$databaseUser"/g;
    s/name="Password" value="Passw0rd"/name="Password" value="$databasePassword"/g;
    # use # as the substitution delimeter so as not to confuse the / characters
    s#name="URL" value="jdbc:mysql://localhost:3306/calordersdb"#name="URL" value="jdbc:mysql://$databaseHostname:$databasePort/$databaseName"#g;
    s#name="Url" value="jdbc:mysql://localhost:3306/calordersdb"#name="Url" value="jdbc:mysql://$databaseHostname:$databasePort/$databaseName"#g;
    print OUTPUT $_;
}

close INPUT;
close OUTPUT;
