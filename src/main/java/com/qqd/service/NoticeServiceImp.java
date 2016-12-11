package com.qqd.service;

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
}
