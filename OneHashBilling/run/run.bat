rem CONFIGURATION FILE SETTING
set "RUN_CONF=setenv.bat"

rem CALLING CONFIGURATION
call %RUN_CONF%

cd..
set OLDDIR=%CD%
set CLASSPATH=%OLDDIR%\classes

java -cp %CLASSPATH% com.onehash.view.OneHashGui
