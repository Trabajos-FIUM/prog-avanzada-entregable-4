@echo off

set JAR=target\playlist-pipeline-0.0.1-SNAPSHOT.jar

echo Killing old process on port 8080...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080"') do taskkill /PID %%a /F

echo Starting Spring Boot detached...
cscript //B scripts\runDetached.vbs

echo ============================================
echo App started detached on http://localhost:8080
echo ============================================
