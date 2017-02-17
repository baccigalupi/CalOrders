node {
    stage('Checkout') {
        git url: 'https://github.com/OncoreLLC/CalOrders.git'
    }

    stage('Clean and Build JET Tier') {
        // clear web subdirectory from previous run if it exists
        sh "rm -Rf CalOrdersJET/web"
        // before running grunt, change localhost reference in ServiceEndPoints.js to jenkins server IP
        // TODO this won't work for prod, needs to change after it goes into container
        sh "sed -i.bak 's/localhost/calorderstest.oncorellc.com/' CalOrdersJET/src/js/common/ServiceEndPoints.js"
        // calorders-rest 8080 is visible on jenkins VM at 9010
        sh "sed -i.bak2 's/8080/9080/' CalOrdersJET/src/js/common/ServiceEndPoints.js"
        // build the JET tier with grunt
        // TODO build:release minification or something releated broke behavior, just using build right now
        sh "cd CalOrdersJET;grunt build"
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
    archiveArtifacts 'CalOrdersRest/dist/*.war'

    stage('Unit Test REST Services Tier') {
        sh "ant \
            -buildfile CalOrdersRest/build.xml \
            -Duser.properties.file=/home/oncore/build.properties \
            test"
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
        sh "mysql -uroot -pPassw0rd -h 172.17.0.4 -P 3306 --database calordersdb < DB_Scripts/CalOrders_DDL.sql"
        sh "mysql -uroot -pPassw0rd -h 172.17.0.4 -P 3306 --database calordersdb < DB_Scripts/referencedata/Reference_Data_Inserts.sql"
    }
    stage('Load Test Data') {
        sh "mysql -uroot -pPassw0rd -h 172.17.0.4 -P 3306 --database calordersdb < DB_Scripts/testdata/TestData_Inserts.sql"
    }
    
    stage('Start REST Services Tier Container') {
        sh 'docker stop calorders-rest || echo "docker container calorders-rest is not currently running, no need to stop it"'
        sh 'docker rm calorders-rest || echo "docker image calorders-rest is not present, no need to remove it"'
        sh 'docker run \
            --name=calorders-rest \
            --link=calorders-mysql:mysqlserver \
            --env-file=docker/calorders.env \
            --publish=9080:8080 \
            --detach \
            kpoland/calorders-rest:$BUILD_ID'
        echo "Wait 30 seconds for payara to start and deploy CalOrdersRest.war in the domain"
        sleep 30
    }
    
    stage('Start JET Tier Container') {
        sh 'docker stop calorders-JET || echo "docker container calorders-rest is not currently running, no need to stop it"'
        sh 'docker rm calorders-JET || echo "docker image calorders-rest is not present, no need to remove it"'
        sh 'docker run \
            --name=calorders-JET \
            --publish=8080:80 \
            --volume=${PWD}/CalOrdersJET/web:/usr/local/apache2/htdocs/ \
            --detach \
            httpd'        
    }
/*
    stage('Push to DockerHub') {
        sh 'docker push kpoland/calorders-rest:$BUILD_ID'
        sh 'docker tag kpoland/calorders-rest:$BUILD_ID kpoland/calorders-rest:latest'
        sh 'docker push kpoland/calorders-rest:latest'
    }
*/
}
