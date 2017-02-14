FROM payara/server-full
MAINTAINER kpoland kyle.poland@oncorellc.com

# TODO pull these if they don't prove necessary
#RUN apt-get -qq update && \
#    apt-get install -qqy less --no-install-recommends && \
#    apt-get install -qqy vim --no-install-recommends && \
#    apt-get install -qqy mysql-client --no-install-recommends
#RUN echo "set -o vi" >> /root/.bashrc
#RUN echo "US/Pacific-New" > /etc/timezone && dpkg-reconfigure --frontend noninteractive tzdata

# one lib and one module into glassfish in the container
# TODO this was needed in glassfish, assume it is necessary in payara41
COPY ../libs/mysql/mysql-connector-java-5.1.38-bin.jar /opt/payara41/glassfish/lib/

# log4j.properties into the glassfish domain
# TODO not sure which of these domains we are using
COPY ../docker/log4j.properties /opt/payara41/glassfish/domains/domain1/lib/classes/
COPY ../docker/log4j.properties /opt/payara41/glassfish/domains/payaradomain/lib/classes/

# placeholder ENV variables for database connections to override at runtime
ENV databasename somedatabase
ENV databaseuser someuser
ENV databasepassword somepassword

# entrypoint start script that starts glassfish, installs the EAR, stops glassfish, then starts glassfish
COPY ../docker/start_payara.sh /opt/payara41/deployments/
# make sure it has unix line endings and is executable
RUN /bin/sed -i -e 's/\r$//' /opt/payara41/deployments/start_payara.sh && /bin/chmod +x /opt/payara41/deployments/start_payara.sh

# copy built WARs into the container
COPY dist/CalOrdersRest.war /opt/payara41/deployments/

RUN /bin/mkdir /home/oncore && /bin/ln -s /opt/payara41/ /home/oncore/payara41

EXPOSE 4848
EXPOSE 8080

CMD ["/opt/payara41/deployments/start_payara.sh"]
