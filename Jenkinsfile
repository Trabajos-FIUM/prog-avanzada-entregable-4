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
                for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do taskkill /PID %%a /F
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

        stage('Deploy') {
            steps {
                echo "Starting Spring Boot in background..."

                bat """
                cd C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\mi-playlist-pipeline

                echo Checking port 8080...

                for /F "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do (
                    if NOT %%a==0 (
                        echo Killing PID %%a
                        taskkill /PID %%a /F
                    )
                )

                echo Starting new instance...

                start "" /MIN cmd /c "java -jar target\\playlist-pipeline-0.0.1-SNAPSHOT.jar > app.log 2>&1"
                """
            }
        }

    }
}
