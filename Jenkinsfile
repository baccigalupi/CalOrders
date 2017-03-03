node {
    stage('Checkout') {
        git url: 'https://github.com/OncoreLLC/CalOrders.git'
    }

    stage('Clean and Build JS Tier') {
        // clear web subdirectory from previous run if it exists
        sh "rm -Rf CalOrdersJET/web"
        // before running grunt, put placeholders in for the hostname and port which will be overridden by an env variable when the container is run
        sh "sed -i.bak -e 's/8080/myservicesport/' -e 's/localhost/myserviceshostname/' CalOrdersJET/src/js/common/ServiceEndPoints.js"
        // build the JET tier with grunt, build:release does the minification
        sh "cd CalOrdersJET;grunt build:release"
    }

    stage('Clean and Build REST Services Tier') {
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            clean"
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties "
    }

    stage('Unit Test REST Services Tier') {
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            test"
    }

    stage('Dockerize JS App') {
        sh 'docker build \
            --rm=true \
            -t kpoland/calorders-jet:$BUILD_ID \
            -f Dockerfile.CalOrdersJET \
            .'
    }

    stage('Dockerize REST App') {
        sh 'docker build \
            --rm=true \
            -t kpoland/calorders-rest:$BUILD_ID \
            -f Dockerfile.CalOrdersRest \
            .'
    }

    stage('Build Test Database') {
        // calorders-mysql container is already running at this IP:port with the given username/password
        // TODO run docker inspect calorders-mysql to determine the IP dynamically
        // TODO remove usernames and passwords from command line and Jenkinsfile
        sh "mysql -uroot -pPassw0rd -h \$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' calorders-mysql) -P 3306 --database calordersdb < DB_Scripts/CalOrders_DDL.sql"
        sh "mysql -uroot -pPassw0rd -h \$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' calorders-mysql) -P 3306 --database calordersdb < DB_Scripts/referencedata/Reference_Data_Inserts.sql"
        sh "mysql -uroot -pPassw0rd -h \$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' calorders-mysql) -P 3306 --database calordersdb < DB_Scripts/testdata/TestData_Inserts.sql"
    }

    stage('Start JS App Test Container') {
        sh 'docker rm $(docker stop calorders-jet) || echo "docker container calorders-rest is not currently running or present"'
        // env variablel SERVICESHOSTNAME is used by startup script to customize the service endpoint in the main.js file
        // run on port 8080 on the test server, jenkins is already on port 80
        sh 'docker run \
            --name=calorders-jet \
            --env="SERVICESHOSTNAME=calorderstest.oncorellc.com" \
            --env="SERVICESPORT=9080" \
            --publish=8080:80 \
            --detach \
            kpoland/calorders-jet:$BUILD_ID'
        sh 'docker ps --latest'
    }

    stage('Start REST App Test Container') {
        sh 'docker rm $(docker stop calorders-rest) || echo "docker container calorders-rest is not currently running or present"'
        // link database container host as "mysqlserver" in this container
        // env variables the container needs are in the .env file
        // expose services on the test box on 9080
        sh 'docker run \
            --name=calorders-rest \
            --link=calorders-mysql:mysqlserver \
            --env-file=docker/calorderstest.env \
            --publish=9080:8080 \
            --detach \
            kpoland/calorders-rest:$BUILD_ID'
        sh 'docker ps --latest'
        echo "Wait 30 seconds for payara to start and deploy CalOrdersRest.war in the domain"
        sleep 30
    }

    stage('Selenium Regression Tests') {
        sh "export DISPLAY=:0 && \
            ant \
            -buildfile CalOrdersSelenium/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            test"
    }

    stage('Publish SwaggerUI') {
        sh 'docker rm $(docker stop calorders-swaggerui) || echo "docker container calorders-swaggerui is not currently running or present"'
        // run a small httpd container and map the SwaggerUI source in to the apache document root
        sh 'docker run \
            -it \
            --name=calorders-swaggerui \
            --publish=9081:80 \
            --volume=${PWD}/CalOrdersSwaggerUI/web:/usr/local/apache2/htdocs/ \
            --detach \
            httpd:alpine'
        sh 'docker ps --latest'
    }

    stage('Push to DockerHub') {
        sh 'docker push kpoland/calorders-rest:$BUILD_ID'
        sh 'docker tag kpoland/calorders-rest:$BUILD_ID kpoland/calorders-rest:latest'
        sh 'docker push kpoland/calorders-rest:latest'
        sh 'docker push kpoland/calorders-jet:$BUILD_ID'
        sh 'docker tag kpoland/calorders-jet:$BUILD_ID kpoland/calorders-jet:latest'
        sh 'docker push kpoland/calorders-jet:latest'
    }

   stage('Pause for Approval') {
        input 'The build, unit tests, and regression tests have all passed. Ready to deploy to production on your mark.'
   }

   stage('Deploy to Prod') {
        // 1. JS front end to calorders.oncorellc.com
        // pull, stop, remove, then run the javascript app container on the prod server
        sh 'ssh kyle@calorders.oncorellc.com docker pull kpoland/calorders-jet:latest'
        sh 'ssh kyle@calorders.oncorellc.com docker rm $(docker stop calorders-jet) || echo "docker container calorders-jet is not currently running or present"'
        // set the env variables for the prod REST services URL and PORT, and run the JS app on port 80
        sh 'ssh kyle@calorders.oncorellc.com docker run \
            --name=calorders-jet \
            --env="SERVICESHOSTNAME=calorders-services.oncorellc.com" \
            --env="SERVICESPORT=80" \
            --publish=80:80 \
            --detach \
            kpoland/calorders-jet:latest'

        // 2. database refresh to calorders-services.oncorellc.com
        // TODO turn this off once database stabilizes otherwise it will clear out prod data at deploy time
        sh "mysql -uroot -pPassw0rd -h calorders-services.oncorellc.com -P 3306 --database calordersdb < DB_Scripts/CalOrders_DDL.sql"
        sh "mysql -uroot -pPassw0rd -h calorders-services.oncorellc.com -P 3306 --database calordersdb < DB_Scripts/referencedata/Reference_Data_Inserts.sql"
        sh "mysql -uroot -pPassw0rd -h calorders-services.oncorellc.com -P 3306 --database calordersdb < DB_Scripts/testdata/TestData_Inserts.sql"

        // REST services back end to calorders-services.oncorellc.com
        // get the prod environment file to the prod server
        sh 'scp docker/calorders.env kyle@calorders-services.oncorellc.com:docker/'
        // pull, stop, remove, then run the REST services app container on the prod server
        sh 'ssh kyle@calorders-services.oncorellc.com docker rm $(docker stop calorders-rest) || echo "docker container calorders-rest is not currently running or present"'
        // link the rest container to the mysql db container, load the prod env vars, and run services on 80
        sh 'ssh kyle@calorders-services.oncorellc.com docker run \
            --name=calorders-rest \
            --link=calorders-mysql:mysqlserver \
            --env-file=docker/calorders.env \
            --publish=80:8080 \
            --detach \
            kpoland/calorders-rest:latest'
        sh 'ssh kyle@calorders-services.oncorellc.com "docker images | head && docker ps -a"'
   }
}
