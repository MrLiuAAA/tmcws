package com.qqd.push;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class XMPush {

	 private String MY_PACKAGE_NAME="com.example.taimu";
	 private Sender sender;
	 
//    public static void main(String[] args) throws JSONException, IOException, ParseException {
//        XMPush t = new XMPush();
////        //Android单推
//        System.out.println(t.pushNotification("gB1H5pRUqGtdSPdwWF2qpgSMOejfPbpRRQKVc6/EGFo=","来自小米的推送消息的内容来自小米的推送消息的内容来自小米的推送消息的内容来自小米的推送消息的内容",""));
//    }
//
    public XMPush() {
    	Constants.useOfficial();
	    sender = new Sender("+n55w9DDfw2GHY8xofS+Sw==");
    }
    public Result pushNotification(String regId,String content,String url,String sound){
    	
    	try {
    	     String messagePayload= content;
    	     String title = "天目车卫士消息";
    	     String description = content;
    	     Message message = new Message.Builder()
    	                .title(title)
    	                .description(description).payload(messagePayload)
    	                .restrictedPackageName(MY_PACKAGE_NAME)
    	                .passThrough(0)  //消息使用通知栏方式
    	                .notifyType(1)     // 使用默认提示音提示
    	                .extra(Constants.EXTRA_PARAM_SOUND_URI,"android.resource://" + MY_PACKAGE_NAME + "/raw/"+sound)
//    	                .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_LAUNCHER_ACTIVITY)
					 	.extra("web_url",url)
    	                .build();
    	     
    	     	//根据regID，发送消息到指定设备上，不重试。
				return  sender.send(message, regId, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	
    	return null;
    	
	}
    
    
   


}
