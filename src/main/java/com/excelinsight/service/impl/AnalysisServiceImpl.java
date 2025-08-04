package com.excelinsight.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.excelinsight.dto.AnalysisResult;
import com.excelinsight.dto.ChartConfig;
import com.excelinsight.entity.AnalysisTask;
import com.excelinsight.entity.ExcelFile;
import com.excelinsight.mapper.AnalysisTaskMapper;
import com.excelinsight.service.AnalysisService;
import com.excelinsight.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据分析服务实现类
 */
@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Resource
    private AnalysisTaskMapper analysisTaskMapper;

    @Resource
    private ExcelFileService excelFileService;

    @Override
    public AnalysisTask createTask(ChartConfig chartConfig) {
        AnalysisTask task = new AnalysisTask();
        task.setTaskName(chartConfig.getTitle());
        task.setExcelFileId(chartConfig.getExcelFileId()); // 设置Excel文件ID
        task.setChartType(chartConfig.getChartType());
        task.setDimensionFields(JSON.toJSONString(chartConfig.getDimensionFields()));
        task.setValueFields(JSON.toJSONString(chartConfig.getValueFields()));
        task.setFilterConditions(JSON.toJSONString(chartConfig.getFilterConditions()));
        task.setChartConfig(JSON.toJSONString(chartConfig));
        task.setStatus(0); // 待处理
        
        analysisTaskMapper.insert(task);
        return task;
    }

    @Override
    public AnalysisResult executeAnalysis(Long taskId) {
        AnalysisTask task = getTaskById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        try {
            // 更新任务状态为处理中
            analysisTaskMapper.updateStatus(taskId, 1);

            // 获取Excel文件
            ExcelFile excelFile = excelFileService.getById(task.getExcelFileId());
            if (excelFile == null) {
                throw new RuntimeException("Excel文件不存在");
            }

            // 解析Excel数据
            List<Map<String, Object>> data = parseExcelData(excelFile.getFilePath());
            
            // 应用筛选条件
            List<Map<String, Object>> filteredData = applyFilters(data, task.getFilterConditions());
            
            // 生成分析结果
            AnalysisResult result = generateAnalysisResult(filteredData, task);
            
            // 保存分析结果
            analysisTaskMapper.updateResult(taskId, JSON.toJSONString(result), 2);
            
            log.info("分析任务执行完成: {}", taskId);
            return result;
            
        } catch (Exception e) {
            log.error("分析任务执行失败: {}", taskId, e);
            analysisTaskMapper.updateErrorMessage(taskId, e.getMessage(), 3);
           // throw new RuntimeException("分析任务执行失败: " + e.getMessage());
            throw new RuntimeException("分析任务执行失败: " + e, e);

        }
    }

    @Override
    public AnalysisResult getAnalysisResult(Long taskId) {
        AnalysisTask task = getTaskById(taskId);
        if (task == null || task.getAnalysisResult() == null) {
            return null;
        }
        // 先整体反序列化
        AnalysisResult result = JSON.parseObject(task.getAnalysisResult(), AnalysisResult.class);

     
        // 直接从 task 对象取这三个字段
        if (task.getDimensionFields() != null && !task.getDimensionFields().isEmpty()) {
            result.setDimensionFields(JSON.parseArray(task.getDimensionFields(), String.class));
        } else {
            result.setDimensionFields(new ArrayList<>());
        }

        // 解析 valueFields
        if (task.getValueFields() != null && !task.getValueFields().isEmpty()) {
            result.setValueFields(JSON.parseArray(task.getValueFields(), String.class));
        } else {
            result.setValueFields(new ArrayList<>());
        }

        // 解析 filterConditions
        if (task.getFilterConditions() != null && !task.getFilterConditions().isEmpty()) {
            result.setFilterConditions(JSON.parseArray(task.getFilterConditions(), ChartConfig.FilterCondition.class));
        } else {
            result.setFilterConditions(new ArrayList<>());
        }
        return result;
    }

    @Override
    public AnalysisTask getTaskById(Long taskId) {
        return analysisTaskMapper.selectById(taskId);
    }

    @Override
    public List<AnalysisTask> getAllTasks() {
        return analysisTaskMapper.selectAll();
    }

    @Override
    public boolean deleteTask(Long taskId) {
        return analysisTaskMapper.deleteById(taskId) > 0;
    }

    /**
     * 解析Excel数据
     */
    private List<Map<String, Object>> parseExcelData(String filePath) {
        List<Map<String, Object>> data = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        EasyExcel.read(filePath, new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                headers.clear();
                for (int i = 0; i < headMap.size(); i++) {
                    headers.add(headMap.get(i));
                }
                System.out.println("解析到的表头: " + headers);
            }

            @Override
            public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    String value = rowData.get(i);
                    row.put(headers.get(i), value);
                }
                System.out.println("解析到的数据行: " + row);
                data.add(row);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 解析完成
            }
        }).sheet().doRead();

        return data;
    }

    /**
     * 应用筛选条件
     */
    private List<Map<String, Object>> applyFilters(List<Map<String, Object>> data, String filterConditionsJson) {
        if (filterConditionsJson == null || filterConditionsJson.trim().isEmpty()) {
            return data;
        }

        List<ChartConfig.FilterCondition> filters = JSON.parseArray(filterConditionsJson, ChartConfig.FilterCondition.class);
        
        return data.stream().filter(row -> {
            for (ChartConfig.FilterCondition filter : filters) {
                if (!matchesFilter(row.get(filter.getField()), filter)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 检查行数据是否匹配筛选条件
     */
    private boolean matchesFilter(Object value, ChartConfig.FilterCondition filter) {
        if (value == null) {
            return false;
        }

        String strValue = value.toString();
        String filterValue = filter.getValue().toString();

        switch (filter.getOperator()) {
            case "eq":
                return strValue.equals(filterValue);
            case "ne":
                return !strValue.equals(filterValue);
            case "like":
                return strValue.contains(filterValue);
            case "gt":
                return compareNumbers(strValue, filterValue) > 0;
            case "lt":
                return compareNumbers(strValue, filterValue) < 0;
            case "gte":
                return compareNumbers(strValue, filterValue) >= 0;
            case "lte":
                return compareNumbers(strValue, filterValue) <= 0;
            default:
                return true;
        }
    }

    /**
     * 数字比较
     */
    private int compareNumbers(String value1, String value2) {
        try {
            double num1 = Double.parseDouble(value1);
            double num2 = Double.parseDouble(value2);
            return Double.compare(num1, num2);
        } catch (NumberFormatException e) {
            return value1.compareTo(value2);
        }
    }

    /**
     * 生成分析结果
     */
    private AnalysisResult generateAnalysisResult(List<Map<String, Object>> data, AnalysisTask task) {
        AnalysisResult result = new AnalysisResult();
        // 解析配置
        ChartConfig config = JSON.parseObject(task.getChartConfig(), ChartConfig.class);
        List<String> dimensionFields = Optional.ofNullable(JSON.parseArray(task.getDimensionFields(), String.class)).orElse(new ArrayList<>());
        List<String> valueFields = Optional.ofNullable(JSON.parseArray(task.getValueFields(), String.class)).orElse(new ArrayList<>());
        List<ChartConfig.FilterCondition> filters = JSON.parseArray(task.getFilterConditions(), ChartConfig.FilterCondition.class);

        List<Map<String, Object>> safeData = Optional.ofNullable(data).orElse(new ArrayList<>());

        // 日志打印字段名
        System.out.println("dimensionFields: " + dimensionFields);
        System.out.println("valueFields: " + valueFields);
        // 日志打印每一行数据
        for (Map<String, Object> row : safeData) {
            System.out.println("row: " + row);
        }
    
        // 设置到 result
        result.setDimensionFields(dimensionFields);
        result.setValueFields(valueFields);
        result.setFilterConditions(filters);

        result.setChartType(config.getChartType());
        result.setTitle(config.getTitle());

        // 生成图表数据
        AnalysisResult.ChartData chartData = new AnalysisResult.ChartData();

        if ("pie".equals(config.getChartType())) {
            // 饼图数据处理
            Map<String, Double> groupedData = new HashMap<>();
            for (Map<String, Object> row : safeData) {
                if (row == null) continue;
                String key = dimensionFields.stream()
                    .map(field -> row.get(field))
                    .map(obj -> obj == null ? "" : obj.toString())
                    .collect(Collectors.joining("-"));
                Double value = valueFields.stream()
                    .mapToDouble(field -> parseDouble(row.get(field)))
                    .sum();
                System.out.println("分组key: " + key + ", value: " + value);
                groupedData.merge(key, value, Double::sum);
            }
            chartData.setCategories(new ArrayList<>(groupedData.keySet()));
            AnalysisResult.Series series = new AnalysisResult.Series();
            series.setName("数据");
            series.setType("pie");
            series.setData(new ArrayList<>(groupedData.values()));
            chartData.setSeries(Collections.singletonList(series));
        } else {
            // 柱状图、折线图等处理
            Map<String, Map<String, Double>> groupedData = new HashMap<>();
            for (Map<String, Object> row : safeData) {
                if (row == null) continue;
                String dimensionKey = dimensionFields.stream()
                    .map(field -> row.get(field))
                    .map(obj -> obj == null ? "" : obj.toString())
                    .collect(Collectors.joining("-"));
                if (!groupedData.containsKey(dimensionKey)) {
                    groupedData.put(dimensionKey, new HashMap<>());
                }
                for (String valueField : valueFields) {
                    Double value = parseDouble(row.get(valueField));
                    System.out.println("分组: " + dimensionKey + ", valueField: " + valueField + ", value: " + value);
                    groupedData.get(dimensionKey).merge(valueField, value, Double::sum);
                }
            }
            chartData.setCategories(new ArrayList<>(groupedData.keySet()));
            List<AnalysisResult.Series> seriesList = new ArrayList<>();
            for (String valueField : valueFields) {
                AnalysisResult.Series series = new AnalysisResult.Series();
                series.setName(valueField);
                series.setType(config.getChartType());
                List<Object> seriesData = groupedData.values().stream()
                    .map(group -> group.getOrDefault(valueField, 0.0))
                    .collect(Collectors.toList());
                System.out.println("seriesData for " + valueField + ": " + seriesData);
                series.setData(seriesData);
                seriesList.add(series);
            }
            chartData.setSeries(seriesList);
        }
        result.setData(chartData);
        // 生成统计信息
        AnalysisResult.Statistics statistics = new AnalysisResult.Statistics();
        statistics.setTotalCount((long) safeData.size());
        if (!valueFields.isEmpty()) {
            List<Double> allValues = safeData.stream()
                .filter(Objects::nonNull)
                .flatMap(row -> valueFields.stream().map(field -> parseDouble(row.get(field))))
                .collect(Collectors.toList());
            System.out.println("allValues: " + allValues);
            statistics.setTotalValue(allValues.stream().mapToDouble(Double::doubleValue).sum());
            statistics.setAverageValue(allValues.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
            statistics.setMaxValue(allValues.stream().mapToDouble(Double::doubleValue).max().orElse(0.0));
            statistics.setMinValue(allValues.stream().mapToDouble(Double::doubleValue).min().orElse(0.0));
        }
        result.setStatistics(statistics);
        return result;
    }

    /**
     * 解析数字
     */
    private Double parseDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
} 