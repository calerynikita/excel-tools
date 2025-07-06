# Excel Insight Pro - Windows PowerShell 停止脚本
# 作者: ExcelInsight Team
# 版本: 1.0.0

Write-Host "=================================" -ForegroundColor Yellow
Write-Host "Excel Insight Pro 停止脚本" -ForegroundColor Yellow
Write-Host "=================================" -ForegroundColor Yellow

# 检查Docker是否运行
try {
    docker info | Out-Null
    Write-Host "✓ Docker 正在运行" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker 未运行" -ForegroundColor Red
    exit 1
}

# 停止服务
Write-Host ""
Write-Host "正在停止 Excel Insight Pro 服务..." -ForegroundColor Yellow
docker compose down

Write-Host ""
Write-Host "=================================" -ForegroundColor Green
Write-Host "Excel Insight Pro 已停止!" -ForegroundColor Green
Write-Host "=================================" -ForegroundColor Green
Write-Host "所有服务已停止，数据已保存" -ForegroundColor Cyan
Write-Host ""
Write-Host "如需重新启动，请运行: .\start.ps1" -ForegroundColor White
Write-Host "=================================" -ForegroundColor Green 