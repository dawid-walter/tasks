call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openwebbrowser
echo.
echo runcrud has errors - breaking work
goto fail

#:openwebbrowser
start firefox http://localhost:8080/crud/v1/task/getTasks

echo.
echo There were errors
