package com.winterchen.dao;

import com.winterchen.model.Columns;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ColumnsMapper extends Mapper<Columns> , MySqlMapper<Columns> {
}