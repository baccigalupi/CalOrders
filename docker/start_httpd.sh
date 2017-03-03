#!/bin/sh
sed -i.bak -e "s/myserviceshostname/$SERVICESHOSTNAME/" -e "s/myservicesport/$SERVICESPORT/" /usr/local/apache2/htdocs/js/main.js
httpd-foreground
