@echo off
title Student Project and Skill Tracking System - DBMS Execution
color 0B
echo ===============================================================================
echo   VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS)
echo   DEPARTMENT OF INFORMATION TECHNOLOGY - REGULATION R2025
echo   COURSE: 25ITT31 - DATABASE MANAGEMENT SYSTEMS
echo   SCENARIO 2: STUDENT PROJECT AND SKILL TRACKING SYSTEM
echo   REGISTER NUMBERS: 2, 32, 62, 92, L2
echo ===============================================================================
echo.

cd /d "%~dp0"

set "PY_CMD="
where python >nul 2>&1 && set "PY_CMD=python"
if not defined PY_CMD if exist "%LOCALAPPDATA%\Python\bin\python.exe" set "PY_CMD=%LOCALAPPDATA%\Python\bin\python.exe"
if not defined PY_CMD if exist "%LOCALAPPDATA%\Programs\Python\Python311\python.exe" set "PY_CMD=%LOCALAPPDATA%\Programs\Python\Python311\python.exe"
if not defined PY_CMD if exist "%LOCALAPPDATA%\Programs\Python\Python312\python.exe" set "PY_CMD=%LOCALAPPDATA%\Programs\Python\Python312\python.exe"
if not defined PY_CMD where py >nul 2>&1 && set "PY_CMD=py"

if not defined PY_CMD (
    echo [ERROR] Python was not found in PATH or standard AppData directories.
    echo Please install Python 3 or add it to PATH.
    pause
    exit /b 1
)

echo [*] Using Python Engine: %PY_CMD%
echo.
echo ===============================================================================
echo  SELECT EXECUTION MODE:
echo    [1] Interactive DBMS Console - Manually insert data & execute ad-hoc SQL
echo    [2] Automated Benchmark      - Run complete SQL suite & update dbms_output.txt
echo ===============================================================================
set "MODE=1"
set /p "MODE=Enter Choice [1 or 2] (Default=1): "

if "%MODE%"=="2" (
    echo.
    echo [*] Running Automated Benchmark Mode...
    "%PY_CMD%" dbms_executor.py --demo
) else (
    echo.
    echo [*] Starting Interactive DBMS Console...
    "%PY_CMD%" dbms_executor.py
)

echo.
echo ===============================================================================
echo [SUCCESS] DBMS session finished.
echo ===============================================================================
pause
