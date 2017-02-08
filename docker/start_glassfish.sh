#!/bin/sh

/usr/local/glassfish-4.1.1/bin/asadmin start-domain
/usr/local/glassfish-4.1.1/bin/asadmin -u admin deploy --force=true /TempusUsers.war
/bin/bash -c "while true; do sleep 60; done"