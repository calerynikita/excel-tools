package com.excelinsight.schedule;

import com.excelinsight.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时清理任务
 */
@Slf4j
@Component
public class CleanupTask {

    @Resource
    private ExcelFileService excelFileService;

    /**
     * 每天凌晨2点清理过期文件
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredFiles() {
        log.info("开始清理过期文件...");
        try {
            excelFileService.cleanExpiredFiles();
            log.info("过期文件清理完成");
        } catch (Exception e) {
            log.error("清理过期文件失败", e);
        }
    }
} 