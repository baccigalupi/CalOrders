#!/bin/sh

/opt/payara41/customize_domainXML.pl /opt/payara41/glassfish/domains/payaradomain/config/domain.xml
/opt/payara41/bin/asadmin start-domain payaradomain
/opt/payara41/bin/asadmin -u admin deploy --force=true /CalOrdersRest.war
/bin/bash -c "while true; do sleep 60; done"
