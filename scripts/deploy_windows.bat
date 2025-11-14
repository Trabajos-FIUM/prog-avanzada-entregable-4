@echo off
set JAR=target\playlist-pipeline-0.0.1-SNAPSHOT.jar

echo Building JAR...
call mvn -DskipTests package

echo Killing old process on port 8080...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do (
    echo Killing PID %%a
    taskkill /PID %%a /F
)

echo Starting new app in background...
start "" java -jar %JAR%

echo ============================================
echo App is starting at http://localhost:8080
echo ============================================
