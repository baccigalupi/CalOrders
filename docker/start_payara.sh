#!/bin/sh

/opt/payara41/bin/asadmin start-domain
/opt/payara41/bin/asadmin -u admin deploy --force=true /CalOrdersRest.war
/bin/bash -c "while true; do sleep 60; done"
