#!/usr/bin/perl -w
use strict;
use File::Copy qw(move);

my $serviceEndPointsFile=$ARGV[0];

my $restServicesURL=$ENV{restservicesURL};

move "$serviceEndPointsFile", "${serviceEndPointsFile}.bak";
open INPUT, "<${serviceEndPointsFile}.bak";
open OUTPUT, ">$serviceEndPointsFile";

while (<INPUT>)
{
    # use # as the substitution delimeter so as not to confuse the / characters
    s#var calOrdersHost = "http://localhost:8080"#var calOrdersHost = "$restServicesURL"#g;
    print OUTPUT $_;
}

close INPUT;
close OUTPUT;
