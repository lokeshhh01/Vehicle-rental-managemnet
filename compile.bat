@echo off
title Compiling Vehicle Rental Management System...
echo ==============================================================================
echo   REVA UNIVERSITY - B.Sc. (BSTCs) Semester V Java Programming Practical
echo   Student: AVULU LOKESH (SRN: R24SA007)
echo   Compiling Vehicle Rental Management System...
echo ==============================================================================

:: Check if javac is in PATH, if not, scan common JDK install paths
where javac >nul 2>&1
if %ERRORLEVEL% neq 0 (
    for /d %%i in ("%ProgramFiles%\Eclipse Adoptium\jdk*") do if exist "%%i\bin\javac.exe" set "PATH=%%i\bin;%PATH%"
    for /d %%i in ("%ProgramFiles%\Java\jdk*") do if exist "%%i\bin\javac.exe" set "PATH=%%i\bin;%PATH%"
    for /d %%i in ("%ProgramFiles%\Microsoft\jdk*") do if exist "%%i\bin\javac.exe" set "PATH=%%i\bin;%PATH%"
)

where javac >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] 'javac' compiler was not found in PATH or standard Program Files directories.
    echo Please make sure JDK 17+ or JDK 21 is installed.
    echo.
    pause
    exit /b 1
)

if not exist "bin" mkdir bin

echo Compiling Java source files from src/...
javac -d bin -sourcepath src src/com/reva/rental/Main.java

if %ERRORLEVEL% equ 0 (
    echo.
    echo [OK] Compilation successful! Class files generated in bin/ directory.
) else (
    echo.
    echo [ERROR] Compilation failed with error code %ERRORLEVEL%.
)
echo.
pause
