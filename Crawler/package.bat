@echo off
echo *******************************
echo *********begin package*********
set curDir=%~dp0
echo %curDir%
set targetDir=%curDir%dir
echo *********begin package*********
if exist %targetDir% (
	echo  %targetDir% exist, need to Delelte
	rd /s /q %targetDir%
)
echo %targetDir% success and mkdir %targetDir% 
md %targetDir%\config\javascript\
copy %curDir%\src\main\resources\*.txt %targetDir%\config
copy %curDir%\src\main\resources\*.yml %targetDir%\config
copy %curDir%\src\main\resources\*.xml %targetDir%\config
copy %curDir%\src\main\resources\javascript\*.js %targetDir%\config\javascript\
call mvn clean package -Dmaven.test.skip=true
copy %curDir%\target\*.jar %targetDir%\
copy %curDir%\*.sh %targetDir%\
pause