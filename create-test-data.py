#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Excel Insight Pro - 测试数据生成脚本
生成示例Excel文件用于测试
"""

import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import os

def create_sales_data():
    """创建销售数据"""
    # 生成日期范围
    start_date = datetime(2024, 1, 1)
    end_date = datetime(2024, 12, 31)
    dates = pd.date_range(start_date, end_date, freq='D')
    
    # 生成数据
    np.random.seed(42)  # 确保结果可重现
    
    data = []
    regions = ['华东', '华南', '华北', '华中', '西南', '西北', '东北']
    products = ['笔记本电脑', '手机', '平板电脑', '耳机', '键盘', '鼠标', '显示器']
    
    for date in dates:
        for _ in range(np.random.randint(5, 15)):  # 每天5-15条记录
            region = np.random.choice(regions)
            product = np.random.choice(products)
            quantity = np.random.randint(1, 10)
            unit_price = np.random.uniform(100, 5000)
            total_amount = quantity * unit_price
            
            data.append({
                '日期': date.strftime('%Y-%m-%d'),
                '地区': region,
                '产品': product,
                '数量': quantity,
                '单价': round(unit_price, 2),
                '总金额': round(total_amount, 2),
                '销售员': f'销售员{np.random.randint(1, 11)}',
                '客户类型': np.random.choice(['个人', '企业', '政府']),
                '支付方式': np.random.choice(['现金', '信用卡', '银行转账', '支付宝', '微信'])
            })
    
    df = pd.DataFrame(data)
    return df

def create_employee_data():
    """创建员工数据"""
    departments = ['技术部', '销售部', '市场部', '人事部', '财务部', '运营部']
    positions = ['经理', '主管', '专员', '助理', '实习生']
    
    data = []
    for i in range(100):
        department = np.random.choice(departments)
        position = np.random.choice(positions)
        age = np.random.randint(22, 55)
        salary = np.random.randint(5000, 30000)
        experience = np.random.randint(0, 15)
        
        data.append({
            '员工ID': f'EMP{i+1:03d}',
            '姓名': f'员工{i+1}',
            '部门': department,
            '职位': position,
            '年龄': age,
            '工龄': experience,
            '基本工资': salary,
            '绩效奖金': round(salary * np.random.uniform(0.1, 0.3), 2),
            '入职日期': (datetime.now() - timedelta(days=np.random.randint(0, 365*5))).strftime('%Y-%m-%d'),
            '学历': np.random.choice(['高中', '大专', '本科', '硕士', '博士']),
            '性别': np.random.choice(['男', '女'])
        })
    
    df = pd.DataFrame(data)
    return df

def main():
    """主函数"""
    print("正在生成测试数据...")
    
    # 创建uploads目录
    if not os.path.exists('uploads'):
        os.makedirs('uploads')
    
    # 生成销售数据
    print("生成销售数据...")
    sales_df = create_sales_data()
    sales_file = 'uploads/销售数据.xlsx'
    sales_df.to_excel(sales_file, index=False, engine='openpyxl')
    print(f"✓ 销售数据已保存到: {sales_file}")
    print(f"  数据行数: {len(sales_df)}")
    print(f"  字段: {', '.join(sales_df.columns)}")
    
    # 生成员工数据
    print("\n生成员工数据...")
    employee_df = create_employee_data()
    employee_file = 'uploads/员工数据.xlsx'
    employee_df.to_excel(employee_file, index=False, engine='openpyxl')
    print(f"✓ 员工数据已保存到: {employee_file}")
    print(f"  数据行数: {len(employee_df)}")
    print(f"  字段: {', '.join(employee_df.columns)}")
    
    print("\n=================================")
    print("测试数据生成完成！")
    print("=================================")
    print("生成的文件:")
    print(f"1. {sales_file} - 销售数据（{len(sales_df)}行）")
    print(f"2. {employee_file} - 员工数据（{len(employee_df)}行）")
    print("\n使用说明:")
    print("1. 启动Excel Insight Pro应用")
    print("2. 访问文件管理页面")
    print("3. 上传这些Excel文件进行测试")
    print("4. 在数据分析页面选择文件并创建图表")
    print("=================================")

if __name__ == "__main__":
    main() 