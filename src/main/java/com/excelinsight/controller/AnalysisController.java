package com.excelinsight.controller;

import com.excelinsight.common.Result;
import com.excelinsight.dto.AnalysisResult;
import com.excelinsight.dto.ChartConfig;
import com.excelinsight.entity.AnalysisTask;
import com.excelinsight.service.AnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据分析控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Resource
    private AnalysisService analysisService;

    /**
     * 创建分析任务
     */
    @PostMapping("/task")
    public Result<AnalysisTask> createTask(@RequestBody ChartConfig chartConfig) {
        try {
            AnalysisTask task = analysisService.createTask(chartConfig);
            return Result.success("任务创建成功", task);
        } catch (Exception e) {
            log.error("创建分析任务失败", e);
            return Result.error("创建分析任务失败: " + e.getMessage());
        }
    }

    /**
     * 执行分析任务
     */
    @PostMapping("/task/{taskId}/execute")
    public Result<AnalysisResult> executeTask(@PathVariable Long taskId) {
        try {
            AnalysisResult result = analysisService.executeAnalysis(taskId);
            return Result.success("分析完成", result);
        } catch (Exception e) {
            log.error("执行分析任务失败", e);
            return Result.error("执行分析任务失败: " + e.getMessage());
        }
    }

    /**
     * 获取分析结果
     */
    @GetMapping("/task/{taskId}/result")
    public Result<AnalysisResult> getAnalysisResult(@PathVariable Long taskId) {
        try {
            AnalysisResult result = analysisService.getAnalysisResult(taskId);
            if (result != null) {
                return Result.success(result);
            } else {
                return Result.error("分析结果不存在");
            }
        } catch (Exception e) {
            log.error("获取分析结果失败", e);
            return Result.error("获取分析结果失败: " + e.getMessage());
        }
    }

    /**
     * 获取任务信息
     */
    @GetMapping("/task/{taskId}")
    public Result<AnalysisTask> getTaskById(@PathVariable Long taskId) {
        try {
            AnalysisTask task = analysisService.getTaskById(taskId);
            if (task != null) {
                return Result.success(task);
            } else {
                return Result.error("任务不存在");
            }
        } catch (Exception e) {
            log.error("获取任务信息失败", e);
            return Result.error("获取任务信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取所有分析任务
     */
    @GetMapping("/task/list")
    public Result<List<AnalysisTask>> getAllTasks() {
        try {
            List<AnalysisTask> tasks = analysisService.getAllTasks();
            return Result.success(tasks);
        } catch (Exception e) {
            log.error("获取任务列表失败", e);
            return Result.error("获取任务列表失败: " + e.getMessage());
        }
    }

    /**
     * 删除分析任务
     */
    @DeleteMapping("/task/{taskId}")
    public Result<String> deleteTask(@PathVariable Long taskId) {
        try {
            boolean success = analysisService.deleteTask(taskId);
            if (success) {
                return Result.success("任务删除成功");
            } else {
                return Result.error("任务删除失败");
            }
        } catch (Exception e) {
            log.error("删除任务失败", e);
            return Result.error("删除任务失败: " + e.getMessage());
        }
    }
} 