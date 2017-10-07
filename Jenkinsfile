node {

    def mvnTool = tool 'maven'

    stage("Checking out") {
        checkout scm
    }

    stage('Unit testing') {
        sh "${mvnTool}/bin/mvn clean test"
        junit '**/*/target/surefire-reports/*.xml'
    }

    stage('Building WARs') {
        sh "${mvnTool}/bin/mvn -DskipTests clean package"
        archive '**/*/target/*.war'
    }

}