
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

REM Detect Python launcher first
set "PY_CMD="

where py >nul 2>&1
if not errorlevel 1 set "PY_CMD=py"

REM Check python command if py is unavailable
if not defined PY_CMD (
    where python >nul 2>&1
    if not errorlevel 1 set "PY_CMD=python"
)

REM Stop if Python is unavailable
if not defined PY_CMD (
    echo [ERROR] Python was not found.
    echo Please install Python or add it to PATH.
    pause
    exit /b 1
)

echo [*] Using Python Engine: %PY_CMD%
echo.
echo ===============================================================================
echo  SELECT EXECUTION MODE:
echo    [1] Interactive DBMS Console - Manually insert data and execute SQL
echo    [2] Automated Benchmark - Run complete SQL suite
echo ===============================================================================
echo.

set "MODE=1"
set /p "MODE=Enter Choice [1 or 2] (Default=1): "

if "%MODE%"=="2" (
    echo.
    echo [*] Running Automated Benchmark Mode...
    %PY_CMD% dbms_executor.py --demo
) else (
    echo.
    echo [*] Starting Interactive DBMS Console...
    %PY_CMD% dbms_executor.py
)

echo.
echo ===============================================================================
echo [SUCCESS] DBMS session finished.
echo ===============================================================================
pause
