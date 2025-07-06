package com.excelinsight.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 图表配置DTO
 */
@Data
public class ChartConfig {
    
    /**
     * Excel文件ID
     */
    private Long excelFileId;
    
    /**
     * 图表类型
     */
    private String chartType;
    
    /**
     * 图表标题
     */
    private String title;
    
    /**
     * 维度字段列表
     */
    private List<String> dimensionFields;
    
    /**
     * 数值字段列表
     */
    private List<String> valueFields;
    
    /**
     * 筛选条件
     */
    private List<FilterCondition> filterConditions;
    
    /**
     * 图表选项
     */
    private ChartOptions options;
    
    /**
     * 筛选条件内部类
     */
    @Data
    public static class FilterCondition {
        private String field;
        private String operator; // eq, ne, gt, lt, gte, lte, in, notIn, like
        private Object value;
    }
    
    /**
     * 图表选项内部类
     */
    @Data
    public static class ChartOptions {
        private String theme;
        private Boolean showLegend;
        private Boolean showTooltip;
        private Map<String, Object> style;
    }
} 