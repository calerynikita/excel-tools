package com.excelinsight.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分析任务实体类
 */
@Data
public class AnalysisTask {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * Excel文件ID
     */
    private Long excelFileId;
    
    /**
     * 图表类型：bar-柱状图，line-折线图，pie-饼图，scatter-散点图
     */
    private String chartType;
    
    /**
     * 维度字段（JSON格式）
     */
    private String dimensionFields;
    
    /**
     * 数值字段（JSON格式）
     */
    private String valueFields;
    
    /**
     * 筛选条件（JSON格式）
     */
    private String filterConditions;
    
    /**
     * 图表配置（JSON格式）
     */
    private String chartConfig;
    
    /**
     * 分析结果（JSON格式）
     */
    private String analysisResult;
    
    /**
     * 任务状态：0-待处理，1-处理中，2-已完成，3-失败
     */
    private Integer status;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 