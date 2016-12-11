package com.qqd.service;

import com.qqd.model.Notice;

import java.util.List;

/**
 * Created by liujianyang on 2016/12/4.
 */
public interface NoticeService {


    public List<Notice> findNoticesByUserName(String username);
}
