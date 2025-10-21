@echo off
echo EDA Java Samples - Maven Commands
echo ===================================
echo.
echo 1. Compile project
echo 2. Run tests
echo 3. Clean and test
echo 4. Generate test report
echo 5. Clean project
echo.
set /p choice="Enter your choice (1-5): "

if "%choice%"=="1" (
    echo Running: mvn compile
    mvn compile
) else if "%choice%"=="2" (
    echo Running: mvn test
    mvn test
) else if "%choice%"=="3" (
    echo Running: mvn clean test
    mvn clean test
) else if "%choice%"=="4" (
    echo Running: mvn surefire-report:report
    mvn surefire-report:report
) else if "%choice%"=="5" (
    echo Running: mvn clean
    mvn clean
) else (
    echo Invalid choice. Please run the script again.
)

echo.
pause