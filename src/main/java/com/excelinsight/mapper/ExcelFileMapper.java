package com.excelinsight.mapper;

import com.excelinsight.entity.ExcelFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Excel文件Mapper接口
 */
@Mapper
public interface ExcelFileMapper {
    
    /**
     * 插入Excel文件记录
     */
    int insert(ExcelFile excelFile);
    
    /**
     * 根据ID查询Excel文件
     */
    ExcelFile selectById(@Param("id") Long id);
    
    /**
     * 根据文件名查询Excel文件
     */
    ExcelFile selectByFileName(@Param("fileName") String fileName);
    
    /**
     * 查询所有Excel文件
     */
    List<ExcelFile> selectAll();
    
    /**
     * 根据状态查询Excel文件
     */
    List<ExcelFile> selectByStatus(@Param("status") Integer status);
    
    /**
     * 更新Excel文件状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 删除Excel文件记录
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除过期文件
     */
    int deleteExpiredFiles(@Param("status") Integer status);
} 