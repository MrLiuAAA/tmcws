package com.qqd.service;

import com.github.pagehelper.PageInfo;
import com.qqd.model.Notice;

import java.util.List;

/**
 * Created by liujianyang on 2016/12/4.
 */
public interface NoticeService {


    public List<Notice> findNoticesByUserName(String username);


    public PageInfo<Notice> findNoticesByUserNameByPage(String userName,String pageNum);

    public boolean deleteNoitce(String noticeid);

    public boolean deleteAllNoitceByUserName(String username);
}
