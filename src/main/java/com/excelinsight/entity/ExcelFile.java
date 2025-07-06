package com.excelinsight.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Excel文件实体类
 */
@Data
public class ExcelFile {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 原始文件名
     */
    private String originalFileName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
    
    /**
     * 状态：0-临时文件，1-已处理，2-已删除
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 