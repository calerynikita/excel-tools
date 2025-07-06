package com.excelinsight.service;

import com.excelinsight.dto.AnalysisResult;
import com.excelinsight.dto.ChartConfig;
import com.excelinsight.entity.AnalysisTask;

/**
 * 数据分析服务接口
 */
public interface AnalysisService {
    
    /**
     * 创建分析任务
     */
    AnalysisTask createTask(ChartConfig chartConfig);
    
    /**
     * 执行分析任务
     */
    AnalysisResult executeAnalysis(Long taskId);
    
    /**
     * 根据任务ID获取分析结果
     */
    AnalysisResult getAnalysisResult(Long taskId);
    
    /**
     * 根据任务ID获取任务信息
     */
    AnalysisTask getTaskById(Long taskId);
    
    /**
     * 获取所有分析任务
     */
    java.util.List<AnalysisTask> getAllTasks();
    
    /**
     * 删除分析任务
     */
    boolean deleteTask(Long taskId);
} 