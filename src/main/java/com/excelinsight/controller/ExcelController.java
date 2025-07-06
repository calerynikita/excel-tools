package com.excelinsight.controller;

import com.excelinsight.common.Result;
import com.excelinsight.entity.ExcelFile;
import com.excelinsight.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Excel文件控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Resource
    private ExcelFileService excelFileService;

    /**
     * 上传Excel文件
     */
    @PostMapping("/upload")
    public Result<ExcelFile> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ExcelFile excelFile = excelFileService.uploadFile(file);
            return Result.success("文件上传成功", excelFile);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件列表
     */
    @GetMapping("/list")
    public Result<List<ExcelFile>> getFileList() {
        try {
            List<ExcelFile> files = excelFileService.getAllFiles();
            return Result.success(files);
        } catch (Exception e) {
            log.error("获取文件列表失败", e);
            return Result.error("获取文件列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取文件信息
     */
    @GetMapping("/{id}")
    public Result<ExcelFile> getFileById(@PathVariable Long id) {
        try {
            ExcelFile file = excelFileService.getById(id);
            if (file != null) {
                return Result.success(file);
            } else {
                return Result.error("文件不存在");
            }
        } catch (Exception e) {
            log.error("获取文件信息失败", e);
            return Result.error("获取文件信息失败: " + e.getMessage());
        }
    }

    /**
     * 解析Excel文件表头
     */
    @GetMapping("/{id}/headers")
    public Result<List<String>> getFileHeaders(@PathVariable Long id) {
        try {
            ExcelFile file = excelFileService.getById(id);
            if (file == null) {
                return Result.error("文件不存在");
            }
            
            List<String> headers = excelFileService.parseHeaders(file.getFilePath());
            return Result.success(headers);
        } catch (Exception e) {
            log.error("解析文件表头失败", e);
            return Result.error("解析文件表头失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteFile(@PathVariable Long id) {
        try {
            boolean success = excelFileService.deleteFile(id);
            if (success) {
                return Result.success("文件删除成功");
            } else {
                return Result.error("文件删除失败");
            }
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return Result.error("删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 更新文件状态
     */
    @PutMapping("/{id}/status")
    public Result<String> updateFileStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            boolean success = excelFileService.updateStatus(id, status);
            if (success) {
                return Result.success("状态更新成功");
            } else {
                return Result.error("状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新文件状态失败", e);
            return Result.error("更新文件状态失败: " + e.getMessage());
        }
    }
} 