package com.qqd.service;

import com.qqd.dao.SerialsNumberDao;
import com.qqd.model.SerialsNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liujianyang on 2016/12/3.
 */
@Service
public class SerialsNumberServiceImp implements  SerialsNumberService {
    @Autowired
    SerialsNumberDao serialsNumberDao;

    @Override
    public SerialsNumber findSerialsNumber(String number) {

        return serialsNumberDao.findSerialsNumber(number);
    }


    /**
     *  * 验证sn
     * 标签编码，BW15010001 Q1W，其中BW是车辆种类代号，分BW(批发)、BC（淘宝网销）、BT(天猫网销)、BD（直销），尾号三位是识别码（详见EXCEL表）所以在APP添加的时候，
     * 1，判断是不是13位；
     * 2，是不是BW\BC\BT\BD中其中一个；
     * 3，跟识别码有没有对应
     * 真实机器码是2015010001，20是代表电动车的，10代表汽车版，这个是车辆代号，在后台暂时不需要判断。
     *
     * @param sn
     * @return
     */
    @Override
    public Boolean validateSN(String sn) {

        if (sn!=null
                &&sn.length()==13) {
            String type = sn.substring(0, 2);
            if ("BW".equalsIgnoreCase(type)
                    ||"BC".equalsIgnoreCase(type)
                    ||"BT".equalsIgnoreCase(type)
                    ||"BD".equalsIgnoreCase(type)) {

                SerialsNumber serialsNumber = serialsNumberDao.findSerialsNumber(sn.substring(0, 10));

                String code = serialsNumber.getCode();
                System.out.println("code:"+serialsNumber.getCode());

                if (code!=null
                        &&code.equalsIgnoreCase(sn.substring(10))) {

                    return true;
                }

            }


        }

        return false;
    }
}
