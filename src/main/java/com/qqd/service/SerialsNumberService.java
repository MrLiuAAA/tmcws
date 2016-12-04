package com.qqd.service;

import com.qqd.model.SerialsNumber;
import org.springframework.stereotype.Service;

/**
 * Created by liujianyang on 2016/12/3.
 */

public interface SerialsNumberService {


    /**
     *   查找 序列号
     * @param number
     * @return
     */
    public SerialsNumber findSerialsNumber(String number);

    Boolean validateSN(String sn);
}
