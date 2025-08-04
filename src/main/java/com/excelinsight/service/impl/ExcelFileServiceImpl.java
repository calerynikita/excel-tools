package com.excelinsight.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.excelinsight.entity.ExcelFile;
import com.excelinsight.mapper.ExcelFileMapper;
import com.excelinsight.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Excel文件服务实现类
 */
@Slf4j
@Service
public class ExcelFileServiceImpl implements ExcelFileService {

    @Resource
    private ExcelFileMapper excelFileMapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.allowed-extensions}")
    private String allowedExtensions;

    @Value("${file.upload.max-size}")
    private Long maxSize;

    @Override
    public ExcelFile uploadFile(MultipartFile file) throws Exception {
        // 验证文件
        validateFile(file);
        
        // 生成唯一文件名
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;
        
        // 确保上传目录存在
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 保存文件
        String filePath = uploadPath + fileName;
        File destFile = new File(filePath);
        file.transferTo(destFile);
        
        // 保存文件信息到数据库
        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName(fileName);
        excelFile.setOriginalFileName(originalFileName);
        excelFile.setFilePath(filePath);
        excelFile.setFileSize(file.getSize());
        excelFile.setFileType(fileExtension);
        excelFile.setUploadTime(LocalDateTime.now());
        excelFile.setStatus(0); // 临时文件
        
        excelFileMapper.insert(excelFile);
        
        // 更新文件状态为已处理
        excelFileMapper.updateStatus(excelFile.getId(), 1);
        excelFile.setStatus(1);
        
        log.info("文件上传成功: {}", originalFileName);
        return excelFile;
    }

    @Override
    public ExcelFile getById(Long id) {
        return excelFileMapper.selectById(id);
    }

    @Override
    public List<ExcelFile> getAllFiles() {
        return excelFileMapper.selectAll();
    }

    @Override
    public List<ExcelFile> getFilesByStatus(Integer status) {
        return excelFileMapper.selectByStatus(status);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        return excelFileMapper.updateStatus(id, status) > 0;
    }

    @Override
    public boolean deleteFile(Long id) {
        ExcelFile excelFile = getById(id);
        if (excelFile != null) {
            // 删除物理文件
            File file = new File(excelFile.getFilePath());
            if (file.exists()) {
                file.delete();
            }
            // 删除数据库记录
            return excelFileMapper.deleteById(id) > 0;
        }
        return false;
    }

    @Override
    public void cleanExpiredFiles() {
        // 删除24小时前的临时文件
        int deletedCount = excelFileMapper.deleteExpiredFiles(0);
        log.info("清理过期文件: {} 个", deletedCount);
    }

    @Override
    public List<String> parseHeaders(String filePath) throws Exception {
        List<String> headers = new ArrayList<>();
    EasyExcel.read(filePath)
        .sheet()
        .headRowNumber(1)
        .registerReadListener(new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                // 不处理数据行
            }
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                // 只处理表头
                for (int i = 0; i < headMap.size(); i++) {
                    headers.add(headMap.get(i));
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {}
        }).doRead();

       return headers;
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("文件不能为空");
        }
        
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new Exception("文件名不能为空");
        }
        
        String fileExtension = getFileExtension(originalFileName);
        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new Exception("不支持的文件类型: " + fileExtension);
        }
        
        if (file.getSize() > maxSize) {
            throw new Exception("文件大小超过限制: " + (maxSize / 1024 / 1024) + "MB");
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
} 