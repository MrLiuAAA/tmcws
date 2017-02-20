package com.qqd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qqd.Const;
import com.qqd.dao.NoticeDao;
import com.qqd.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liujianyang on 2016/12/4.
 */
@Service
public class NoticeServiceImp implements NoticeService {

    @Autowired
    NoticeDao noticeDao;

    @Override
    public List<Notice> findNoticesByUserName(String username) {

        return noticeDao.findNoticesByUserName(username);
    }


    public PageInfo<Notice> findNoticesByUserNameByPage(String userName, String pageNum){

        com.github.pagehelper.Page<Notice> pageHelper =  PageHelper.startPage(new Integer(pageNum), Const.PAGE_SIZE);

        return new PageInfo<Notice>(noticeDao.findNoticesByUserName(userName));
    }

    public boolean deleteNoitce(String noticeid){

        return noticeDao.deleteById(noticeid)>0;
    }

    public boolean deleteAllNoitceByUserName(String username){

        return noticeDao.deleteByUserName(username)>0;
    }
}
