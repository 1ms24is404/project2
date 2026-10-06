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
                sh 'docker build -t $DOCKER_IMAGE:latest .'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push $DOCKER_IMAGE:latest'
            }
        }
    }
}
