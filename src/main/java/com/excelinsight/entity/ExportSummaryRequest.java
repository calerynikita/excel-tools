package com.excelinsight.entity;

import com.excelinsight.dto.ChartConfig;
import lombok.Data;

import java.util.List;


@Data
public class ExportSummaryRequest {
    private String fileId;
    private String fileName;
    private List<String> dimensionFields;
    private List<String> valueFields;
    private List<ChartConfig.FilterCondition> filterConditions;

}