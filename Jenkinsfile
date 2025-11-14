pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests=false'
            }
        }

        stage('Docker Build') {
            steps {
                bat """
                docker build -t playlist-pipeline:latest .
                """
            }
        }

        stage('Docker Stop Running') {
            steps {
                bat """
                docker stop playlist-pipeline || true
                docker rm playlist-pipeline || true
                """
            }
        }

        stage('Docker Run') {
            steps {
                bat """
                docker run -d --name playlist-pipeline -p 8080:8080 playlist-pipeline:latest
                """
            }
        }

    }
}
