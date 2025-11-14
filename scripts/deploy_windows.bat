@echo off
set JAR=target\playlist-pipeline-0.0.1-SNAPSHOT.jar

echo Building JAR...
call mvn -DskipTests package

echo Killing old process on port 8080...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do (
    echo Killing PID %%a
    taskkill /PID %%a /F
)

echo Starting Spring Boot in background...
start /B java -jar %JAR% > app.log 2>&1

echo ============================================
echo App is starting at http://localhost:8080
echo Logs written to app.log
echo ============================================
