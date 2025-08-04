package com.excelinsight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 */
@Controller
public class PageController {

    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 文件管理页面
     */
    @GetMapping("/files")
    public String files() {
        return "files";
    }

    /**
     * 数据分析页面
     */
    @GetMapping("/analysis")
    public String analysis() {
        return "analysis";
    }

    /**
     * 任务管理页面
     */
    @GetMapping("/tasks")
    public String tasks() {
        return "tasks";
    }
    
    @GetMapping("/analysis-result")
    public String analysisResult() {
        // 返回模板名，不带.html后缀
        return "analysis-result";
    }

} 