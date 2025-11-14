pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Kill 8080') {
            steps {
                bat """
                echo Checking port 8080...

                for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do (
                    echo Killing PID %%a
                    taskkill /PID %%a /F
                )

                rem --- prevent Jenkins from failing if nothing was killed ---
                exit /b 0
                """
            }
        }

        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests=false'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat """
                echo Starting Spring Boot App...

                start "" /B cmd /c "java -jar target\\playlist-pipeline-0.0.1-SNAPSHOT.jar > app.log 2>&1"

                echo ============================================
                echo App launched at http://localhost:8080
                echo Logs in workspace/app.log
                echo ============================================
                """
            }
        }
    }
}
