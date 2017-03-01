node {
    stage('Checkout') {
        git url: 'https://github.com/OncoreLLC/CalOrders.git'
    }

    stage('Clean and Build JS Tier') {
        // clear web subdirectory from previous run if it exists
        sh "rm -Rf CalOrdersJET/web"
        // services run on 9080
        sh "sed -i.bak 's/8080/9080/' CalOrdersJET/src/js/common/ServiceEndPoints.js"
        // before running grunt, change localhost reference in ServiceEndPoints.js to a placeholder for the services hostname
        sh "sed -i.bak 's/localhost/myserviceshostname/' CalOrdersJET/src/js/common/ServiceEndPoints.js"
        // build the JET tier with grunt
        // TODO switch back to build if build:release isn't working
        sh "cd CalOrdersJET;grunt build:release"
    }

    stage('Clean and Build REST Services Tier') {
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            clean"
        // TODO get javadoc working and then can use default target instead of dist
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            dist"
    }

    stage('Unit Test REST Services Tier') {
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            test"
    }

    stage('Dockerize JS Tier') {
        sh 'docker build \
            --rm=true \
            -t kpoland/calorders-jet:$BUILD_ID \
            -f Dockerfile.CalOrdersJET \
            .'
    }

    stage('Dockerize REST Services Tier') {
        sh 'docker build \
            --rm=true \
            -t kpoland/calorders-rest:$BUILD_ID \
            -f Dockerfile.CalOrdersRest \
            .'
    }

    // calorders-mysql container is already running at this IP:port with the given username/password
    stage('Build Test Database') {
        sh "mysql -uroot -pPassw0rd -h 172.17.0.2 -P 3306 --database calordersdb < DB_Scripts/CalOrders_DDL.sql"
        sh "mysql -uroot -pPassw0rd -h 172.17.0.2 -P 3306 --database calordersdb < DB_Scripts/referencedata/Reference_Data_Inserts.sql"
        sh "mysql -uroot -pPassw0rd -h 172.17.0.2 -P 3306 --database calordersdb < DB_Scripts/testdata/TestData_Inserts.sql"
    }

    stage('Start JS Tier Container') {
        sh 'docker stop calorders-jet || echo "docker container calorders-rest is not currently running, no need to stop it"'
        sh 'docker rm calorders-jet || echo "docker image calorders-rest is not present, no need to remove it"'
        sh 'docker run \
            --name=calorders-jet \
            --env="SERVICESHOSTNAME=calorderstest.oncorellc.com" \
            --publish=8080:80 \
            --detach \
            kpoland/calorders-jet:$BUILD_ID'
        sh 'docker ps --latest'
    }

    stage('Start REST Services Tier Container') {
        sh 'docker stop calorders-rest || echo "docker container calorders-rest is not currently running, no need to stop it"'
        sh 'docker rm calorders-rest || echo "docker image calorders-rest is not present, no need to remove it"'
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

    stage('Publish SwaggerUI') {
        sh 'docker stop calorders-swaggerui || echo "docker container calorders-swaggerui is not currently running, no need to stop it"'
        sh 'docker rm calorders-swaggerui || echo "docker image calorders-swaggerui is not present, no need to remove it"'
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
        sh 'docker tag kpoland/calorders-jet:$BUILD_ID kpoland/calorders-rest:latest'
        sh 'docker push kpoland/calorders-jet:latest'
    }
}
