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

        stage('Build & Test') {
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

        stage('Deploy Windows') {
            when {
                expression { isWindows() }
            }
            steps {
                bat """
                if not exist h2-data mkdir h2-data
                docker run -d --name playlist-pipeline ^
                  -p 8080:8080 ^
                  -v %cd%\\h2-data:/app/data ^
                  playlist-pipeline:latest
                """
            }
        }

        stage('Deploy Mac/Linux') {
            when {
                expression { !isWindows() }
            }
            steps {
                sh '''
                mkdir -p h2-data
                docker stop playlist-pipeline 2>/dev/null || true
                docker rm playlist-pipeline 2>/dev/null || true

                docker run -d --name playlist-pipeline \
                  -p 8080:8080 \
                  -v $(pwd)/h2-data:/app/data \
                  playlist-pipeline:latest
                '''
            }
        }
    }
}

// Helper
def isWindows() {
    return java.lang.System.getProperty('os.name').toLowerCase().contains('win')
}
