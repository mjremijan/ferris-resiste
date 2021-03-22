@Echo OFF
Echo.

Echo LAUNCH DIRECTORY
Echo ----------------
Echo "%~dp0"
Echo. 

Echo CURRENT DIRECTORY
Echo -----------------
Echo "%CD%"
Echo. 

Set PD=%~dp0..
Set JAVA_BIN=%PD%\jre\bin\java.exe
Set JAVAW_BIN=%PD%\jre\bin\javaw.exe
Echo JAVA BIN
Echo --------
Echo "%JAVA_BIN%"
Echo. 

Echo JAVA VERSION
Echo ------------
%JAVA_BIN% -version
Echo.

Echo DERBY SYSTEM HOME
Echo -----------------
Set DERBY_SYSTEM_HOME=-Dderby.system.home=%PD%\data
Echo "%DERBY_SYSTEM_HOME%"
Echo.

Echo START RESISTE
Echo -------------
%JAVA_BIN% %DERBY_SYSTEM_HOME% -jar %PD%\lib\ferris-resiste-derby-user-1.2.0.0-windows.jar
