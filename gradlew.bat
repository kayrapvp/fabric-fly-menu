@echo off
setlocal
set DIR=%~dp0
cd /d "%DIR%"
where gradle >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Gradle is not installed or not on PATH.
    echo Install Gradle and try again: https://gradle.org/install/
    exit /b 1
)
call gradle %*
