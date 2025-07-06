@echo off
chcp 65001 >nul
title Excel Insight Pro 停止脚本

echo =================================
echo Excel Insight Pro 停止脚本
echo =================================

REM 检查PowerShell是否可用
powershell -Command "Write-Host '正在停止 Excel Insight Pro...'" >nul 2>&1
if errorlevel 1 (
    echo 错误: PowerShell 不可用
    pause
    exit /b 1
)

REM 运行PowerShell脚本
echo 正在执行停止脚本...
powershell -ExecutionPolicy Bypass -File "stop.ps1"

echo.
echo 按任意键退出...
pause >nul 