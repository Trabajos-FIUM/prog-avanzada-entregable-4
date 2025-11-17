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
                bat "docker build -t playlist-pipeline:latest ."
            }
        }

        stage('Docker Stop Running') {
            steps {
                bat """
                docker stop playlist-pipeline 2>NUL || exit 0
                docker rm playlist-pipeline 2>NUL || exit 0
                """
            }
        }

        stage('Docker Run') {
            steps {
                bat """
                docker run -d --name playlist-pipeline ^
                  -p 8080:8080 ^
                  -v C:/h2-data:/app/data ^
                  playlist-pipeline:latest
                """
            }
        }
    }
}


/*
# Script equivalente para Mac/Linux (solo documentación para el entregable)

sh '''
mkdir -p h2-data

docker stop playlist-pipeline 2>/dev/null || true
docker rm playlist-pipeline 2>/dev/null || true

docker run -d --name playlist-pipeline \
  -p 8080:8080 \
  -v $(pwd)/h2-data:/app/data \
  playlist-pipeline:latest
'''
*/