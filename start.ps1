# Excel Insight Pro - Windows PowerShell 启动脚本
# 作者: ExcelInsight Team
# 版本: 1.0.0

Write-Host "=================================" -ForegroundColor Green
Write-Host "Excel Insight Pro 启动脚本" -ForegroundColor Green
Write-Host "=================================" -ForegroundColor Green

# 检查Docker是否安装
try {
    docker --version | Out-Null
    Write-Host "✓ Docker 已安装" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker 未安装，请先安装 Docker Desktop" -ForegroundColor Red
    exit 1
}

# 检查Docker是否运行
try {
    docker info | Out-Null
    Write-Host "✓ Docker 正在运行" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker 未运行，请启动 Docker Desktop" -ForegroundColor Red
    exit 1
}

# 检查docker compose是否可用
try {
    docker compose version | Out-Null
    Write-Host "✓ Docker Compose 可用" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker Compose 不可用，请检查安装" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "正在启动 Excel Insight Pro..." -ForegroundColor Yellow

# 创建必要的目录
$directories = @("uploads", "temp", "logs")
foreach ($dir in $directories) {
    if (!(Test-Path $dir)) {
        New-Item -ItemType Directory -Path $dir | Out-Null
        Write-Host "✓ 创建目录: $dir" -ForegroundColor Green
    }
}

# 启动服务
Write-Host ""
Write-Host "正在启动 MySQL 和 Excel Insight Pro 服务..." -ForegroundColor Yellow
docker compose up -d

# 等待服务启动
Write-Host ""
Write-Host "等待服务启动..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# 检查服务状态
Write-Host ""
Write-Host "检查服务状态..." -ForegroundColor Yellow
docker compose ps

# 显示访问信息
Write-Host ""
Write-Host "=================================" -ForegroundColor Green
Write-Host "Excel Insight Pro 启动成功!" -ForegroundColor Green
Write-Host "=================================" -ForegroundColor Green
Write-Host "应用地址: http://localhost:8080/excel-insight" -ForegroundColor Cyan
Write-Host "Druid监控: http://localhost:8080/excel-insight/druid" -ForegroundColor Cyan
Write-Host "监控账号: admin/admin" -ForegroundColor Cyan
Write-Host ""
Write-Host "常用命令:" -ForegroundColor Yellow
Write-Host "  查看日志: docker compose logs -f excel-insight" -ForegroundColor White
Write-Host "  停止服务: docker compose down" -ForegroundColor White
Write-Host "  重启服务: docker compose restart" -ForegroundColor White
Write-Host "=================================" -ForegroundColor Green 