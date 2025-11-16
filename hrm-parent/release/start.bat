@echo off
setlocal

REM --- Lấy thư mục hiện tại của file bat ---
set "DIR=%~dp0"
pushd "%DIR%"

REM --- Kiểm tra Java có trong PATH chưa ---
where java >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Khong tim thay 'java' trong PATH. Cai JDK/JRE hoac dat JAVA_HOME.
  echo Vi du: setx JAVA_HOME "C:\Program Files\Java\jdk-21"
  echo       add "%JAVA_HOME%\bin" vao PATH
  pause
  exit /b 1
)

REM --- Kiểm tra hai file jar co ton tai ---
if not exist "hrm-app.jar" (
  echo [ERROR] Thieu file hrm-app.jar trong thu muc: %CD%
  pause & exit /b 1
)
if not exist "updater.jar" (
  echo [WARN] Thieu updater.jar. Tinh nang Update se khong hoat dong.
)

REM --- Chay ung dung, ghi log de debug neu loi ---
echo Starting HRM...
java -jar "hrm-app.jar" %* 1>>run.log 2>&1

REM --- Hien loi neu co ---
set "RC=%ERRORLEVEL%"
if not "%RC%"=="0" (
  echo [ERROR] Ung dung thoat voi ma loi %RC%. Xem file run.log de biet chi tiet.
  notepad.exe run.log
  pause
)

popd
endlocal