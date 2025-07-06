package com.excelinsight.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 分析结果DTO
 */
@Data
public class AnalysisResult {
    
    /**
     * 图表类型
     */
    private String chartType;
    
    /**
     * 图表标题
     */
    private String title;
    
    /**
     * 图表数据
     */
    private ChartData data;
    
    /**
     * 图表配置
     */
    private Map<String, Object> options;
    
    /**
     * 统计信息
     */
    private Statistics statistics;
    
    /**
     * 图表数据内部类
     */
    @Data
    public static class ChartData {
        private List<String> categories; // X轴类别
        private List<Series> series;     // 数据系列
    }
    
    /**
     * 数据系列内部类
     */
    @Data
    public static class Series {
        private String name;
        private String type;
        private List<Object> data;
        private Map<String, Object> itemStyle;
    }
    
    /**
     * 统计信息内部类
     */
    @Data
    public static class Statistics {
        private Long totalCount;
        private Double totalValue;
        private Double averageValue;
        private Double maxValue;
        private Double minValue;
        private Map<String, Object> summary;
    }
} 