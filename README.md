# Excel Insight Pro

专业的Excel数据分析与图表生成工具，支持本地部署和云端部署。

## 🎯 产品概述

Excel Insight Pro 是一个基于Spring Boot + MyBatis + MySQL + EasyExcel的Excel数据分析平台，提供以下核心功能：

- **Excel文件上传与管理**：支持.xlsx和.xls格式文件上传，自动识别表头字段
- **智能数据分析**：支持多维度数据筛选、聚合分析
- **丰富图表类型**：支持柱状图、折线图、饼图、散点图等多种图表类型
- **灵活部署方案**：支持本地部署和云端部署，满足不同场景需求

## 🚀 技术栈

### 后端技术
- **Spring Boot 2.7.18**：主框架
- **MyBatis 2.3.1**：ORM框架
- **MySQL 8.0.33**：数据库
- **EasyExcel 3.3.2**：Excel处理
- **Druid 1.2.20**：数据库连接池
- **FastJSON 2.0.43**：JSON处理

### 前端技术
- **Bootstrap 5.1.3**：UI框架
- **ECharts 5.4.3**：图表库
- **Thymeleaf**：模板引擎

## 📋 功能特性

### 1. Excel文件管理
- 支持拖拽上传和点击上传
- 文件格式验证（.xlsx, .xls）
- 文件大小限制（50MB）
- 自动识别表头字段
- 文件状态管理

### 2. 数据分析
- 多维度字段选择
- 数值字段聚合
- 灵活筛选条件
- 实时数据预览

### 3. 图表生成
- **柱状图**：适合分类数据对比
- **折线图**：适合趋势分析
- **饼图**：适合占比分析
- **散点图**：适合相关性分析

### 4. 任务管理
- 分析任务创建
- 任务状态跟踪
- 结果查看和导出
- 任务历史管理

### 5. 数据安全
- **本地部署**：数据完全本地化存储
- **云端部署**：自动清理临时数据
- 文件访问控制
- 操作日志记录

## 🛠️ 快速开始

### 环境要求
- JDK 8+
- MySQL 8.0+
- Maven 3.6+

### 1. 克隆项目
```bash
git clone https://github.com/calerynikita/excel-tools.git
cd excel-insight-pro
```

### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE excel_insight DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 执行初始化脚本
source src/main/resources/sql/init.sql
```

### 3. 启动应用
```bash
# 编译项目
mvn clean package

# 运行应用
java -jar target/excel-insight-pro-1.0.0.jar
```

### 4. 访问应用
- 应用地址：http://localhost:8080/excel-insight
- Druid监控：http://localhost:8080/excel-insight/druid

## 📖 使用指南

### 1. 文件上传
1. 访问"文件管理"页面
2. 拖拽或选择Excel文件上传
3. 系统自动识别表头字段
4. 查看文件状态和字段信息

### 2. 数据分析
1. 访问"数据分析"页面
2. 选择要分析的Excel文件
3. 配置图表类型和标题
4. 选择维度字段和数值字段
5. 添加筛选条件（可选）
6. 点击"生成图表"

### 3. 任务管理
1. 访问"任务管理"页面
2. 查看所有分析任务
3. 查看任务详情和状态
4. 查看分析结果
5. 删除不需要的任务

## 🏗️ 项目结构

```
excel-insight-pro/
├── src/
│   ├── main/
│   │   ├── java/com/excelinsight/
│   │   │   ├── controller/          # 控制器层
│   │   │   ├── service/             # 服务层
│   │   │   ├── mapper/              # 数据访问层
│   │   │   ├── entity/              # 实体类
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   ├── common/              # 公共类
│   │   │   └── schedule/            # 定时任务
│   │   └── resources/
│   │       ├── templates/           # 页面模板
│   │       ├── mapper/              # MyBatis映射文件
│   │       ├── sql/                 # SQL脚本
│   │       └── application.yml      # 配置文件
│   └── test/                        # 测试代码
├── pom.xml                          # Maven配置
└── README.md                        # 项目文档
```

## 🚀 部署方案

### 本地部署
1. 在内网服务器上部署应用
2. 配置MySQL数据库
3. 修改配置文件中的数据库连接信息
4. 启动应用服务

### 云端部署
1. 部署到云服务器
2. 配置数据库（可使用云数据库）
3. 配置域名和SSL证书
4. 设置定时清理任务

### Docker部署

#### 方式一：使用Docker Compose（推荐）

**Linux/Mac:**
```bash
# 启动所有服务
docker compose up -d

# 查看服务状态
docker compose ps

# 查看日志
docker compose logs -f excel-insight

# 停止服务
docker compose down
```

**Windows:**
```powershell
# 方式1: 使用PowerShell脚本（推荐）
.\start.ps1

# 方式2: 使用批处理文件
start.bat

# 方式3: 直接使用docker compose
docker compose up -d

# 停止服务
.\stop.ps1
# 或
stop.bat
# 或
docker compose down
```

#### 方式二：单独运行容器

**Linux/Mac:**
```bash
# 构建镜像
docker build -t excel-insight-pro .

# 运行容器
docker run -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/excel_insight \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  excel-insight-pro
```

**Windows PowerShell:**
```powershell
# 构建镜像
docker build -t excel-insight-pro .

# 运行容器（PowerShell格式）
docker run -d -p 8080:8080 `
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/excel_insight `
  -e SPRING_DATASOURCE_USERNAME=root `
  -e SPRING_DATASOURCE_PASSWORD=password `
  excel-insight-pro
```

**Windows CMD:**
```cmd
# 构建镜像
docker build -t excel-insight-pro .

# 运行容器（CMD格式）
docker run -d -p 8080:8080 ^
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/excel_insight ^
  -e SPRING_DATASOURCE_USERNAME=root ^
  -e SPRING_DATASOURCE_PASSWORD=password ^
  excel-insight-pro
```


## 🔒 安全特性

### 数据安全
- 文件上传验证
- 文件类型限制
- 文件大小限制
- 临时文件自动清理

### 访问控制
- 文件访问权限控制
- 操作日志记录
- 异常处理机制



## 📞 联系方式
- 问题反馈：[Issues]
- 邮箱：calerynikita@163.com

## ❓ 常见问题

### Q: 服务启动失败？
A: 检查以下几点：
1. 确保Docker和Docker Compose已正确安装
2. 检查端口8080和3306是否被占用
3. 查看日志：`docker compose logs -f excel-insight`

### Q: 上传文件失败？
A: 检查以下几点：
1. 确保文件格式为.xlsx或.xls
2. 检查文件大小是否超过限制（默认50MB）
3. 确保uploads目录有写入权限

### Q: 上传文件后显示为"临时文件"？
A: 这是正常现象，文件上传后会自动处理并更新状态为"已处理"。如果长时间显示为临时文件，请检查：
1. 应用日志中是否有错误信息
2. 数据库连接是否正常
3. 文件格式是否正确

### Q: 数据分析页面选择不到Excel文件？
A: 确保：
1. 文件已上传并状态为"已处理"
2. 文件格式正确且包含数据
3. 刷新页面重新加载文件列表

### Q: 页面出现乱码？
A: 解决方案：
1. 确保浏览器编码设置为UTF-8
2. 清除浏览器缓存
3. 检查应用是否正确设置了字符编码

### Q: 如何生成测试数据？
A: 使用提供的测试数据生成脚本：
```bash
# 安装依赖
pip install pandas numpy openpyxl

# 生成测试数据
python create-test-data.py
```
生成的Excel文件将保存在uploads目录中，可直接上传测试。