package com.excelinsight.mapper;

import com.excelinsight.entity.AnalysisTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 分析任务Mapper接口
 */
@Mapper
public interface AnalysisTaskMapper {
    
    /**
     * 插入分析任务
     */
    int insert(AnalysisTask analysisTask);
    
    /**
     * 根据ID查询分析任务
     */
    AnalysisTask selectById(@Param("id") Long id);
    
    /**
     * 根据Excel文件ID查询分析任务
     */
    List<AnalysisTask> selectByExcelFileId(@Param("excelFileId") Long excelFileId);
    
    /**
     * 查询所有分析任务
     */
    List<AnalysisTask> selectAll();
    
    /**
     * 根据状态查询分析任务
     */
    List<AnalysisTask> selectByStatus(@Param("status") Integer status);
    
    /**
     * 更新分析任务状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新分析结果
     */
    int updateResult(@Param("id") Long id, @Param("analysisResult") String analysisResult, @Param("status") Integer status);
    
    /**
     * 更新错误信息
     */
    int updateErrorMessage(@Param("id") Long id, @Param("errorMessage") String errorMessage, @Param("status") Integer status);
    
    /**
     * 删除分析任务
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除过期任务
     */
    int deleteExpiredTasks(@Param("status") Integer status);
} 