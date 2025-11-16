@echo off
setlocal enableextensions
cd /d "%~dp0"
chcp 65001 >nul

rem === Tên jar chính ===
set "APP_JAR=hrm-app.jar"

rem === Tìm java.exe ===
set "JAVA_EXE="
where java >nul 2>&1 && for /f "delims=" %%J in ('where java') do set "JAVA_EXE=%%~fJ"
if not defined JAVA_EXE if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
if not defined JAVA_EXE if exist "%cd%\jre\bin\java.exe" set "JAVA_EXE=%cd%\jre\bin\java.exe"
if not defined JAVA_EXE (
  echo [ERROR] Khong tim thay Java trong PATH/JAVA_HOME/jre\bin.
  pause & exit /b 1
)

echo [INFO] Java: "%JAVA_EXE%"
"%JAVA_EXE%" -version 2>&1

rem === Kiểm tra jar ===
if not exist "%APP_JAR%" (
  echo [ERROR] Thieu %APP_JAR% trong "%cd%"
  dir
  pause & exit /b 1
)

rem === Truyền đường dẫn db.env (đặt cạnh jar) ===
set "HRM_ENV_DIR=%cd%"
if not exist "%HRM_ENV_DIR%\db.env" echo [WARN] Khong thay db.env canh jar. Se fallback classpath neu co.

set "JAVA_OPTS=-Dhrm.env.dir=%HRM_ENV_DIR% -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Ho_Chi_Minh"
echo [INFO] Starting app...
"%JAVA_EXE%" %JAVA_OPTS% -jar "%APP_JAR%" %*
set "RC=%ERRORLEVEL%"
echo [INFO] Exit code: %RC%

if not "%RC%"=="0" (
  if exist run.log notepad run.log
  pause
)
endlocal
