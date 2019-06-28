package com.winterchen.dao;

import com.winterchen.UpdateListMapper;
import com.winterchen.model.Data;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DataMapper extends Mapper<Data> , MySqlMapper<Data>, UpdateListMapper<Data> {
}