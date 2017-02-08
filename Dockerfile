FROM kpoland/glassfish:4.1.1
MAINTAINER kpoland kyle.poland@oncorellc.com

RUN apt-get -qq update && \
    apt-get install -qqy less --no-install-recommends && \
    apt-get install -qqy vim --no-install-recommends && \
    apt-get install -qqy mysql-client --no-install-recommends
RUN echo "set -o vi" >> /root/.bashrc
RUN echo "US/Pacific-New" > /etc/timezone && dpkg-reconfigure --frontend noninteractive tzdata

# one lib and one module into glassfish in the container
COPY libs/mysql/mysql-connector-java-5.1.38-bin.jar /usr/local/glassfish-4.1.1/glassfish/lib/

# log4j.properties into the glassfish domain
# TODO do we need this
# COPY docker/log4j.properties /usr/local/glassfish-4.1.1/glassfish/domains/domain1/lib/classes/

# placeholder ENV variables for database connections to override at runtime
ENV databasename somedatabase
ENV databaseuser someuser
ENV databasepassword somepassword

# entrypoint start script that starts glassfish, installs the EAR, stops glassfish, then starts glassfish
COPY docker/start_glassfish.sh /
# make sure it has unix line endings and is executable
RUN /bin/sed -i -e 's/\r$//' /start_glassfish.sh && /bin/chmod +x /start_glassfish.sh

# copy built WARs into the container
COPY TempusUsers/dist/TempusUsers.war /

RUN /bin/mkdir /home/oncore && /bin/ln -s /usr/local/glassfish-4.1.1/ /home/oncore/glassfish-4.1.1

EXPOSE 4848
EXPOSE 8080

CMD ["/start_glassfish.sh"]