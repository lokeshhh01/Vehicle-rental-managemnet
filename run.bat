@echo off
title CityDrive - Vehicle Rental Management System (REVA University)
color 0B
echo ==============================================================================
echo   REVA UNIVERSITY - B.Sc. (BSTCs) Semester V Java Programming Practical
echo   Student: AVULU LOKESH (SRN: R24SA007)
echo   Starting Vehicle Rental Management System...
echo ==============================================================================

:: Check if java is in PATH, if not, scan common JDK install paths
where java >nul 2>&1
if %ERRORLEVEL% neq 0 (
    for /d %%i in ("%ProgramFiles%\Eclipse Adoptium\jdk*") do if exist "%%i\bin\java.exe" set "PATH=%%i\bin;%PATH%"
    for /d %%i in ("%ProgramFiles%\Java\jdk*") do if exist "%%i\bin\java.exe" set "PATH=%%i\bin;%PATH%"
    for /d %%i in ("%ProgramFiles%\Microsoft\jdk*") do if exist "%%i\bin\java.exe" set "PATH=%%i\bin;%PATH%"
)

where java >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] 'java' runtime was not found in PATH or standard Program Files directories.
    echo Please make sure Java/JDK is installed.
    echo.
    pause
    exit /b 1
)

if not exist "bin\com\reva\rental\Main.class" (
    echo [INFO] Binaries not found in bin/. Compiling source files first...
    where javac >nul 2>&1
    if %ERRORLEVEL% neq 0 (
        for /d %%i in ("%ProgramFiles%\Eclipse Adoptium\jdk*") do if exist "%%i\bin\javac.exe" set "PATH=%%i\bin;%PATH%"
        for /d %%i in ("%ProgramFiles%\Java\jdk*") do if exist "%%i\bin\javac.exe" set "PATH=%%i\bin;%PATH%"
    )
    if not exist "bin" mkdir bin
    javac -d bin -sourcepath src src/com/reva/rental/Main.java
    if %ERRORLEVEL% neq 0 (
        echo [ERROR] Compilation failed.
        pause
        exit /b %ERRORLEVEL%
    )
)

echo Launching Console Application...
echo.
java -cp bin com.reva.rental.Main
pause
