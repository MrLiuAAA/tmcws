package com.qqd.dao;

import com.qqd.model.PushNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liujianyang on 2017/3/27.
 */
@Mapper
public interface PushNewsDao {

    public PushNews findPushNewsById(@Param("id") String id);

    public List<PushNews> findPushNews();

    public Integer deleteById(@Param("id") String id);

    public void save(PushNews news);

}
