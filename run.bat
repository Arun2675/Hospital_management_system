@echo off
setlocal
cd /d "%~dp0"
if not exist bin mkdir bin
javac -d bin src\com\hospital\model\*.java src\com\hospital\service\*.java src\com\hospital\main\*.java
if errorlevel 1 (
    echo.
    echo Compilation failed.
    pause
    exit /b 1
)
java -cp bin com.hospital.main.HospitalApp
pause
