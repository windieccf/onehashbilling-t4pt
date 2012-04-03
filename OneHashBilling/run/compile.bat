
rem CONFIGURATION FILE SETTING
set "RUN_CONF=setenv.bat"

rem CALLING CONFIGURATION
call %RUN_CONF%

cd..
set OLDDIR=%CD%
set SOURCE_FILE_PATH=%OLDDIR%\src\
set CLASSES_FILE_PATH=%OLDDIR%\classes\
echo %SOURCE_FILE_PATH%


dir %SOURCE_FILE_PATH%*.java /B/S > javasrc.tmp~

@"%JAVA_HOME%\bin\javac.exe" -d %CLASSES_FILE_PATH% @javasrc.tmp~

del javasrc.tmp~

