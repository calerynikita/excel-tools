package com.excelinsight.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.read.listener.PageReadListener;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelUtil {
    // 读取Excel为List<Map>
    public static List<Map<String, Object>> readExcelAsMap(File file) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        EasyExcelFactory.read(file)
            .autoCloseStream(true)
            .sheet()
            .headRowNumber(0)
            .registerReadListener(new PageReadListener<Map<Integer, String>>(data -> {
                for (Map<Integer, String> row : data) {
                    Map<String, Object> map = new HashMap<>();
                    for (Map.Entry<Integer, String> entry : row.entrySet()) {
                        map.put("col" + entry.getKey(), entry.getValue());
                    }
                    dataList.add(map);
                }
            }))
            .doRead();
        return dataList;
    }

    // 过滤
    public static List<Map<String, Object>> filterData(List<Map<String, Object>> data, List<ChartConfig.FilterCondition> filters) {
        if (filters == null || filters.isEmpty()) return data;
        return data.stream().filter(row -> {
            for (ChartConfig.FilterCondition f : filters) {
                String v = String.valueOf(row.get(f.getField()));
                if (!ExcelUtil.matchCondition(v, f.getOperator(), f.getValue())) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    // 简单条件判断
    public static boolean matchCondition(String v, String op, String val) {
        switch (op) {
            case "eq": return v.equals(val);
            case "gt": return Double.parseDouble(v) > Double.parseDouble(val);
            case "lt": return Double.parseDouble(v) < Double.parseDouble(val);
            // 可扩展更多
            default: return true;
        }
    }

    // 分组聚合
    public static List<Map<String, Object>> groupAndAggregate(List<Map<String, Object>> data, List<String> dims, List<String> values) {
        if (dims == null || dims.isEmpty() || values == null || values.isEmpty()) return Collections.emptyList();
        Map<String, Map<String, Object>> groupMap = new LinkedHashMap<>();
        for (Map<String, Object> row : data) {
            String key = dims.stream().map(d -> String.valueOf(row.get(d))).collect(Collectors.joining("_"));
            Map<String, Object> group = groupMap.computeIfAbsent(key, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                for (String d : dims) m.put(d, row.get(d));
                for (String v : values) m.put(v, 0.0);
                return m;
            });
            for (String v : values) {
                double oldVal = Double.parseDouble(String.valueOf(group.get(v)));
                double addVal = Double.parseDouble(String.valueOf(row.get(v)));
                group.put(v, oldVal + addVal);
            }
        }
        return new ArrayList<>(groupMap.values());
    }

    // 构建表头
    public static List<List<String>> buildHead(List<String> dims, List<String> values) {
        List<List<String>> head = new ArrayList<>();
        for (String d : dims) head.add(Collections.singletonList(d));
        for (String v : values) head.add(Collections.singletonList(v));
        return head;
    }
}