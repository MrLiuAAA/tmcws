package com.qqd.dao;

import com.qqd.model.SerialsNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liujianyang on 2016/12/3.
 */
@Mapper
public interface SerialsNumberDao {

    public SerialsNumber findSerialsNumber(@Param("serialsnumber") String number);
}
