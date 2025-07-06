package com.excelinsight.service;

import com.excelinsight.entity.ExcelFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * Excel文件服务接口
 */
public interface ExcelFileService {
    
    /**
     * 上传Excel文件
     */
    ExcelFile uploadFile(MultipartFile file) throws Exception;
    
    /**
     * 根据ID查询Excel文件
     */
    ExcelFile getById(Long id);
    
    /**
     * 查询所有Excel文件
     */
    List<ExcelFile> getAllFiles();
    
    /**
     * 根据状态查询Excel文件
     */
    List<ExcelFile> getFilesByStatus(Integer status);
    
    /**
     * 更新文件状态
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 删除Excel文件
     */
    boolean deleteFile(Long id);
    
    /**
     * 清理过期文件
     */
    void cleanExpiredFiles();
    
    /**
     * 解析Excel文件表头
     */
    List<String> parseHeaders(String filePath) throws Exception;
} 