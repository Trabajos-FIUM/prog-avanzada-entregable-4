pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Kill Old Java') {
            steps {
                script {
                    if (isUnix()) {
                        sh "fuser -k 8080/tcp || true"
                    } else {
                        bat """
                        echo Killing ALL Java processes...
                        taskkill /F /IM java.exe 2>nul || echo No java running
                        """
                    }
                }
            }
        }

        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) { sh 'mvn clean package -DskipTests=false' }
                    else { bat 'mvn clean package -DskipTests=false' }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) { sh 'mvn test' }
                    else { bat 'mvn test' }
                }
            }
            post { always { junit '**/target/surefire-reports/*.xml' } }
        }

        stage('Deploy') {
            steps {
                script {
                    if (isUnix()) { sh 'bash scripts/deploy_mac.sh' }
                    else { bat 'scripts\\deploy_windows.bat' }
                }
            }
        }
    }
}
