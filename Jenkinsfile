pipeline {
    agent any
    tools {
        maven 'Maven 3.9.6'

    options {
        timestamps()
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/maximebaudoin/epsi-atelier-test-automatise-exo-5.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
    post {
        success {
            mail to: 'raphael.bailhet@ecoles-epsi.net',
                subject: "Reussite de la pipeline: ${currentBuild.fullDisplayName}",
                body: "La pipeline est bien passée: ${env.BUILD_URL}"
        }
        failure {
            mail to: 'raphael.bailhet@ecoles-epsi.net',
                subject: "La Pipeline a failed: ${currentBuild.fullDisplayName}",
                body: "La pipeline a malheureusement failed: ${env.BUILD_URL}"
        }
    }
}