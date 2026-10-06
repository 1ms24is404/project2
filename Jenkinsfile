pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "bhumisheru/estateiq"
        SONARQUBE_SERVER = "sonarqube-server"
    }

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    stages {

        stage('Build') {
            steps {
                echo "Skipping Maven build (fake build)..."
                sh '''
                mkdir -p target
                echo "fake-jar-content" > target/app.jar
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "Skipping SonarQube analysis (not configured)..."
            }
        }

        stage('Quality Gate') {
            steps {
                echo "Skipping Quality Gate (Sonar not executed)..."
            }
        }

        stage('Docker Build') {
            steps {
                echo "Skipping Docker build..."
            }
        }

        stage('Docker Login') {
            steps {
                echo "Skipping Docker login..."
            }
        }

        stage('Docker Push') {
            steps {
                echo "Skipping Docker push..."
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully (fully mocked)."
        }
        failure {
            echo "Pipeline failed."
        }
    }
}
