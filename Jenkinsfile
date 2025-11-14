pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Kill old 8080 process') {
            steps {
                bat """
                echo Checking port 8080...
                for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do (
                    echo Killing PID %%a
                    taskkill /PID %%a /F
                )
                """
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
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

        stage('Deploy - Start JAR') {
            steps {
                bat """
                echo Starting Spring Boot app...
                start "" /B cmd /c "java -jar target\\playlist-pipeline-0.0.1-SNAPSHOT.jar > app.log 2>&1"
                timeout /t 3 >nul
                echo ======================================
                echo App running at http://localhost:8080
                echo Logs: C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\mi-playlist-pipeline\\app.log
                echo ======================================
                """
            }
        }
    }
}
