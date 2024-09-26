start /B java -jar C:\ProgramData\Jenkins\.jenkins\workspace\inventarioPipeline\target\inventario-0.0.1-SNAPSHOT.jar
timeout /T 5 /NOBREAK
findstr /C:"Started" application.log
if errorlevel 1 (
    echo Application failed to start
    exit /B 1
)