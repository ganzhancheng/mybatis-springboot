package com.winterchen.dao;

import com.winterchen.model.Lottery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LotteryMapper extends Mapper<Lottery> {

    @Select("SELECT * FROM lottery ORDER BY issue_no DESC LIMIT #{page},10")
    List<Lottery> selectLottery(@Param("page") int page);
}