@echo off
title Student Project and Skill Tracking System - Foundations of Data Science
color 0E
echo ===============================================================================
echo   VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY (AUTONOMOUS)
echo   DEPARTMENT OF INFORMATION TECHNOLOGY - REGULATION R2025
echo   COURSE: 25ITC31 - FOUNDATIONS OF DATA SCIENCE
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
    pause
    exit /b 1
)

echo [*] Using Python Engine: %PY_CMD%
echo.
echo ===============================================================================
echo  SELECT EXECUTION MODE:
echo    [1] Interactive Analytics Workbench - Manually filter data, inspect skills, score rubrics
echo    [2] Automated EDA Pipeline          - Run full statistical analysis & update data_science_output.txt
echo ===============================================================================
set "MODE=1"
set /p "MODE=Enter Choice [1 or 2] (Default=1): "

if "%MODE%"=="2" (
    echo.
    echo [*] Running Automated EDA Pipeline...
    "%PY_CMD%" data_analysis.py --demo
) else (
    echo.
    echo [*] Starting Interactive Analytics Workbench...
    "%PY_CMD%" data_analysis.py
)

echo.
echo ===============================================================================
echo [SUCCESS] Foundations of Data Science session finished.
echo ===============================================================================
pause
