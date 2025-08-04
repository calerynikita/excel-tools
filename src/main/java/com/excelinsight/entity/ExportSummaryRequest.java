package com.excelinsight.entity;

import lombok.Data;



@Data
public class ExportRequest {
    private String fileId;
    private List<String> dimensionFields;
    private List<String> valueFields;
    private List<ChartConfig.FilterCondition> filterConditions;

}