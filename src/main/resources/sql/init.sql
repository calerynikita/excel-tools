-- 创建数据库
CREATE DATABASE IF NOT EXISTS excel_insight DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE excel_insight;

-- Excel文件表
CREATE TABLE IF NOT EXISTS excel_file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    original_file_name VARCHAR(255) NOT NULL COMMENT '原始文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_size BIGINT NOT NULL COMMENT '文件大小（字节）',
    file_type VARCHAR(50) NOT NULL COMMENT '文件类型',
    upload_time TIMESTAMP NOT NULL COMMENT '上传时间',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-临时文件，1-已处理，2-已删除',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_file_name (file_name),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Excel文件表';

-- 分析任务表
CREATE TABLE IF NOT EXISTS analysis_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    task_name VARCHAR(255) NOT NULL COMMENT '任务名称',
    excel_file_id BIGINT NOT NULL COMMENT 'Excel文件ID',
    chart_type VARCHAR(50) NOT NULL COMMENT '图表类型：bar-柱状图，line-折线图，pie-饼图，scatter-散点图',
    dimension_fields TEXT COMMENT '维度字段（JSON格式）',
    value_fields TEXT COMMENT '数值字段（JSON格式）',
    filter_conditions TEXT COMMENT '筛选条件（JSON格式）',
    chart_config TEXT COMMENT '图表配置（JSON格式）',
    analysis_result LONGTEXT COMMENT '分析结果（JSON格式）',
    status INT NOT NULL DEFAULT 0 COMMENT '任务状态：0-待处理，1-处理中，2-已完成，3-失败',
    error_message TEXT COMMENT '错误信息',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_excel_file_id (excel_file_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time),
    FOREIGN KEY (excel_file_id) REFERENCES excel_file(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分析任务表';

-- 插入测试数据（可选）
INSERT INTO excel_file (file_name, original_file_name, file_path, file_size, file_type, upload_time, status) VALUES
('test_sales_data.xlsx', '销售数据.xlsx', './uploads/test_sales_data.xlsx', 1024000, 'xlsx', NOW(), 1);

INSERT INTO analysis_task (task_name, excel_file_id, chart_type, dimension_fields, value_fields, status) VALUES
('销售数据分析', 1, 'bar', '["地区", "产品类别"]', '["销售额", "数量"]', 2); 