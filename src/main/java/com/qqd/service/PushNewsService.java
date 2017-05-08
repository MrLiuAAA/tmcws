package com.qqd.service;

import com.github.pagehelper.PageInfo;
import com.qqd.model.PushNews;

/**
 * Created by liujianyang on 2017/3/27.
 */
public interface PushNewsService {

    public PushNews save(PushNews news);

    public PageInfo<PushNews> findPushNewsByPage(String page);

    public Boolean deletePushNews(String id);

    public PushNews findPushNewsById(String id);
}
