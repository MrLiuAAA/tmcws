package com.qqd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqd.Const;
import com.qqd.dao.PushNewsDao;
import com.qqd.model.PushNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liujianyang on 2017/3/27.
 */
@Service
public class PushNewsServiceImp implements PushNewsService {

    @Autowired
    public PushNewsDao pushNewsDao;

    @Override
    public PushNews save(PushNews news) {

        pushNewsDao.save(news);
        return news;
    }

    @Override
    public PageInfo<PushNews> findPushNewsByPage(String page) {

        com.github.pagehelper.Page<PushNews> pageHelper =  PageHelper.startPage(new Integer(page), Const.PAGE_SIZE);

        return new PageInfo<PushNews>(pushNewsDao.findPushNews());

    }

    @Override
    public Boolean deletePushNews(String id) {
        Integer r = pushNewsDao.deleteById(id);
        return r>0;
    }

    @Override
    public PushNews findPushNewsById(String id) {
        return pushNewsDao.findPushNewsById(id);
    }
}
