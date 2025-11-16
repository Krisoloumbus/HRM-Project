@echo off
setlocal enableextensions
rem ==== Đi vào thư mục chứa .bat (thường là thư mục release) ====
cd /d "%~dp0"

rem ==== Kiểm tra JDK/JRE ====
where java >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Khong tim thay 'java' trong PATH. Cai JDK/JRE hoac dat JAVA_HOME.
  pause & exit /b 1
)

rem ==== Kiểm tra jar chính ====
if not exist "hrm-app.jar" (
  echo [ERROR] Thieu file hrm-app.jar trong thu muc: %cd%
  pause & exit /b 1
)

rem ==== Thư mục chứa db.env (đặt cạnh jar) ====
set "HRM_ENV_DIR=%cd%"

rem ==== Cảnh báo nếu thiếu db.env ====
if not exist "%HRM_ENV_DIR%\db.env" (
  echo [WARN] Khong thay db.env canh jar. Se thu doc tu classpath (neu co).
)

rem ==== Tuỳ chọn ghi log (comment/dỡ comment tuỳ ý) ====
set "JAVA_OPTS=-Dhrm.env.dir=%HRM_ENV_DIR% -Dfile.encoding=UTF-8"

echo Starting HRM...
rem --- Nếu muốn xem log realtime trên console thì dùng dòng dưới và bỏ redirect ---
rem java %JAVA_OPTS% -jar "hrm-app.jar" %*
rem --- Mặc định: ghi log vào run.log ---
java %JAVA_OPTS% -jar "hrm-app.jar" %* 1>>run.log 2>&1
set "RC=%ERRORLEVEL%"

if not "%RC%"=="0" (
  echo [ERROR] App loi code %RC%. Mo run.log de xem chi tiet.
  if exist run.log notepad.exe run.log
  pause
)

endlocal
