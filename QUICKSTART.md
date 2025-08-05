# Excel Insight Pro - 快速开始指南

## 🚀 5分钟快速启动

### 环境要求
- Windows 10/11 或 Linux/Mac
- Docker Desktop（已安装并运行）
- 至少 4GB 内存

### Windows 用户（推荐）

1. **下载项目**
   ```bash
   git clone https://github.com/calerynikita/excel-tools.git
   cd excel-insight-pro
   ```

2. **一键启动**
   - 双击 `start.bat` 文件
   - 或在 PowerShell 中运行：`.\start.ps1`

3. **访问应用**
   - 打开浏览器访问：http://localhost:8080/excel-insight
   - 开始使用！

### Linux/Mac 用户

1. **下载项目**
   ```bash
   git clone <repository-url>
   cd excel-insight-pro
   ```

2. **启动服务**
   ```bash
   docker compose up -d
   ```

3. **访问应用**
   - 打开浏览器访问：http://localhost:8080/excel-insight
   - 开始使用！

## 📋 使用流程

### 1. 上传Excel文件
1. 点击"文件管理"
2. 拖拽或选择Excel文件（.xlsx或.xls格式）
3. 等待上传完成

### 2. 创建分析任务
1. 点击"数据分析"
2. 选择已上传的Excel文件
3. 选择图表类型（柱状图、折线图、饼图等）
4. 选择维度字段和数值字段
5. 点击"生成图表"

### 3. 查看结果
1. 点击"任务管理"
2. 查看任务状态
3. 点击"查看结果"查看图表

## 🔧 常用命令

### Windows PowerShell
```powershell
# 启动服务
.\start.ps1

# 停止服务
.\stop.ps1

# 查看日志
docker compose logs -f excel-insight

# 重启服务
docker compose restart
```

### Linux/Mac
```bash
# 启动服务
docker compose up -d

# 停止服务
docker compose down

# 查看日志
docker compose logs -f excel-insight

# 重启服务
docker compose restart
```

## 🆘 常见问题

### Q: 启动失败怎么办？
A: 检查以下几点：
1. Docker Desktop 是否已启动
2. 端口 8080 是否被占用
3. 内存是否足够（至少4GB）

### Q: 无法访问应用？
A: 尝试以下步骤：
1. 等待1-2分钟让服务完全启动
2. 检查防火墙设置
3. 尝试访问：http://127.0.0.1:8080/excel-insight

### Q: 上传文件失败？
A: 检查文件：
1. 文件格式是否为 .xlsx 或 .xls
2. 文件大小是否超过 50MB
3. 文件是否损坏

### Q: 如何清理数据？
A: 使用以下命令：
```bash
# 停止并删除所有数据
docker compose down -v

# 删除上传的文件
rm -rf uploads/* temp/* logs/*
```

## 📞 获取帮助

- 查看完整文档：README.md
- 查看日志：`docker compose logs -f excel-insight`
- 访问监控页面：http://localhost:8080/excel-insight/druid

## 🎯 下一步

1. 熟悉基本功能
2. 尝试不同的图表类型
3. 探索高级筛选功能
4. 查看监控和日志
5. 根据需要调整配置 