@echo off
SET MY_NABPATH=C:\eclipse\Nab2_svn\Nab2\
SET IN_CMD_EXPAND
IF ERRORLEVEL 1 GOTO end_fork
cd %MY_NABPATH%
SET CLASSPATH=.;%MY_NABPATH%;%MY_NABPATH%\test\;%MY_NABPATH%build\WEB-INF\classes\
cd %MY_NABPATH%lib\
FOR %%f IN (*.JAR) DO SET CLASSPATH=!CLASSPATH!;%MY_NABPATH%lib\%%f
REG ADD "HKEY_CURRENT_USER\Environment" /v CLASSPATH /t REG_SZ /d %CLASSPATH% /f
REG ADD "HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v CLASSPATH /t REG_SZ /d %CLASSPATH% /f
cd %MY_NABPATH%
@echo on
java net.violet.platform.message.VirtualRabbit %*
GOTO end
:end_fork
@echo off
SET IN_CMD_EXPAND=1
cd %MY_NABPATH%scripts\
CMD.EXE /V /C "startVirtualRabbit.bat %*"
SET IN_CMD_EXPAND=
@echo on
:end
