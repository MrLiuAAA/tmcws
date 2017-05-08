package com.qqd.push;

import com.tencent.xinge.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class XGPush {
    private XingeApp xinge;

//    public static void main(String[] args) throws JSONException {
//        XGPush t = new XGPush();
////        //Android单推
//        System.out.println(t.pushNotification("6cd29803037c73444cf99e327d1b033c18592dd7","来自信鸽的 推送消息的内容","","ring"));
//    }
//
    public XGPush() {
        xinge = new XingeApp(2100246378, "3b1d357f4d6b0cdf34c3436119c9bf06");
    }
    public JSONObject pushNotification(String token,String content,String url,String sound) {
    	Message message = new Message();
    	message.setType(Message.TYPE_NOTIFICATION);
         Style style = new Style(1);
         style = new Style(3, 1, 0, 1, 0);
         style.setRingRaw(sound);
         ClickAction action = new ClickAction();
         action.setActionType(ClickAction.TYPE_ACTIVITY);

        action.setActivity("com.example.taimu.activity.WebViewActivity");
         Map<String, Object> custom = new HashMap<String, Object>();
         custom.put("web_url", url);
//         custom.put("key2", 2);
         message.setTitle("天目车卫士消息");
         message.setContent(content);
         message.setStyle(style);
         message.setAction(action);
         message.setCustom(custom);
         TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
         message.addAcceptTime(acceptTime1);
         return xinge.pushSingleDevice(token,message);
	}
    
    
   


}
