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
}
