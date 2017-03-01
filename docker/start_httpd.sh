#!/bin/sh
sed -i.bak "s/myserviceshostname/$SERVICESHOSTNAME/" /usr/local/apache2/htdocs/js/main.js
httpd-foreground
