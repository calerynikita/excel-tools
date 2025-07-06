package com.excelinsight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Excel Insight Pro 主启动类
 * 
 * @author ExcelInsight
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.excelinsight.mapper")
@EnableScheduling
public class ExcelInsightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelInsightApplication.class, args);
        System.out.println("=================================");
        System.out.println("Excel Insight Pro 启动成功!");
        System.out.println("访问地址: http://localhost:8080/excel-insight");
        System.out.println("Druid监控: http://localhost:8080/excel-insight/druid");
        System.out.println("=================================");
    }
} 