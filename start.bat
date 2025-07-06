@echo off
chcp 65001 >nul
title Excel Insight Pro 启动脚本

echo =================================
echo Excel Insight Pro 启动脚本
echo =================================

REM 检查PowerShell是否可用
powershell -Command "Write-Host '正在启动 Excel Insight Pro...'" >nul 2>&1
if errorlevel 1 (
    echo 错误: PowerShell 不可用
    pause
    exit /b 1
)

REM 运行PowerShell脚本
echo 正在执行启动脚本...
powershell -ExecutionPolicy Bypass -File "start.ps1"

echo.
echo 按任意键退出...
pause >nul 