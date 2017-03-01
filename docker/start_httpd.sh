#!/bin/sh
sed -i.bak 's/myserviceshostname/$serviceshostname/' /usr/local/apache2/htdocs/js/main.js
httpd-foreground
