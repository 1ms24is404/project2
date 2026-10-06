pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/1ms24is404/project2.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t bhumisheru/estateiq .'
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push bhumisheru/estateiq'
            }
        }
    }
}