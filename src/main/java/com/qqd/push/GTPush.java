package com.qqd.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GTPush {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "8dMEirLcnN8AUrFt0Wk2B3";
    private static String appKey = "buq3EP5D019fCF1Zsehfw6";
    private static String masterSecret = "21VsIobX4n8NexGTboNR98";

//    static String CID = "e605a0db5ce3cca9b76b012978064940";
  //别名推送方式
   // static String Alias = "";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    
    
//    public static void main(String[] args) {
//		GTPush gtPush = new GTPush();
//
//		IPushResult gtResult = gtPush.pushNotification("c208371729b005ec2f9312457e63c25c", "测试个推 123啦啦啦", "ring.caf");
//
//		System.out.println("《个推平台》推送结果："+gtResult.getResponse().toString());
//	}
    
    
    public GTPush() {
    	
	    
    }
    
    public IPushResult pushNotification(String cid,String content,String sound){
    	IGtPush push = new IGtPush(host, appKey, masterSecret);
//        LinkTemplate template = linkTemplateDemo(content);
        
        TransmissionTemplate template = constructClientTransMsg(content,sound);
        
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        
       
        
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        //target.setAlias(Alias);
        
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
        return ret;
    }
    
    private static TransmissionTemplate constructClientTransMsg(String content,String sound) {

        SingleMessage message = new SingleMessage();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        String msg = "{\"notification_title\":\"天目车卫士消息\",\"notification_content\":\""+content+"\",\"raw\":\""+sound+"\"}";
        
        template.setTransmissionContent(msg);
        template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app
        
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1 * 1000);

        return template;
    }

    public static LinkTemplate linkTemplateDemo(String content) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 设置通知栏标题与内容
        template.setTitle("天目车卫士消息");
       
        template.setText(content);
        
        
        // 配置通知栏图标
//        template.setLogo("icon.png");
        // 配置通知栏网络图标，填写图标URL地址
//        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
//        template.setUrl("http://www.baidu.com");
        return template;
    }
}
