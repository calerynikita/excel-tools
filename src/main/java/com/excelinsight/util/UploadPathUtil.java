package com.excelinsight.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author ccwang
 * @version 1.0
 * @date 2025/8/5 9:49
 */
@Component
public class UploadPathUtil {

    private static String configUploadDir;

    @Value("${file.upload.dir:}")
    private String uploadDirConfig;

    @PostConstruct
    public void init() {
        configUploadDir = uploadDirConfig;
    }

    public static String getUploadDir() {
        String uploadPath;
        if (configUploadDir != null && !configUploadDir.trim().isEmpty()) {
            // 配置了绝对路径
            uploadPath = configUploadDir;
        } else {
            // 默认放到当前运行目录下的 uploads
            uploadPath = System.getProperty("user.dir") + File.separator + "uploads";
        }

        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return uploadPath;
    }
}
