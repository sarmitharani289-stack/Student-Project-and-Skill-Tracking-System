@echo off
title Student Project and Skill Tracking System - Java Application
color 0A
echo ===============================================================================
echo   VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS)
echo   DEPARTMENT OF INFORMATION TECHNOLOGY - REGULATION R2025
echo   COURSE: 25ITT32 - JAVA PROGRAMMING
echo   SCENARIO 2: STUDENT PROJECT AND SKILL TRACKING SYSTEM
echo   REGISTER NUMBERS: 2, 32, 62, 92, L2
echo ===============================================================================
echo.

cd /d "%~dp0"

if not exist "bin" mkdir "bin"

echo [*] Compiling Java source files...
javac -d bin src\com\tracking\exception\*.java src\com\tracking\model\*.java src\com\tracking\service\*.java src\com\tracking\util\*.java src\com\tracking\Main.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo [SUCCESS] Compilation successful!
echo.
echo ===============================================================================
echo  SELECT EXECUTION MODE:
echo    [1] Interactive Mode   - Manually enter students, skills, projects, marks
echo    [2] Automated Demo     - Run complete showcase & update java_output.txt
echo ===============================================================================
set "MODE=1"
set /p "MODE=Enter Choice [1 or 2] (Default=1): "

if "%MODE%"=="2" (
    echo.
    echo [*] Running Automated Demonstration...
    java -cp bin com.tracking.Main --demo
) else (
    echo.
    echo [*] Starting Interactive Console...
    java -cp bin com.tracking.Main
)

echo.
echo ===============================================================================
echo [SUCCESS] Java program finished execution.
echo ===============================================================================
pause
