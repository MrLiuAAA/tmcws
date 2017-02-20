package com.qqd.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by liujianyang on 2017/1/7.
 */
public class TMCWSToken extends UsernamePasswordToken {
    private String from;  /// 来源


    public TMCWSToken(final String username, final String password,final String from) {
        //this(username, password != null ? password.toCharArray() : null, false, null);

        super(username,password);
        this.from = from;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
