package com.qqd.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.qqd.push.ios.IOSUnicast;
import com.xiaomi.xmpush.server.Result;

public class App {
	private PushClient client = new PushClient();
	
	/**
	 * 推送 IOS 消息 （使用的是Umeng的推送）
	 * @param token
	 * @param badge
	 * @param message
	 * @param sound
	 * @throws Exception
	 */
	public void sendIOSUnicast(String token,int badge,String message,String url,String sound) throws Exception {
		
		IOSUnicast unicast = new IOSUnicast("57986553e0f55aa398001edf","yzif8xgqa2k4xth5c5mejhry0mz3cskq");
		unicast.setDeviceToken(token);
		unicast.setAlert(message);
		unicast.setBadge(badge);
		unicast.setSound(sound);
//		unicast.setTestMode();
		/// 正式环境的时候 使用
		unicast.setProductionMode();
		unicast.setCustomizedField("web_url",url);
		client.send(unicast);
	}
	
	/**
	 * 推送 Android消息   使用的 小米 华为 信鸽 等三个推送
	 * @param token
	 * @param message
	 * @param sound
	 * @throws Exception
	 */
	public void sendAndroidUnicast(String token,String message,String url,String sound)  {

//		////  信鸽推送
//		XGPush xgPush = new XGPush();
//		xgPush.pushNotification(token, message, url,sound);
//

		System.out.println("推送  token:"+token +"\n message:"+message+"\n sound:"+sound);

		/// 个推
		GTPush gtPush = new GTPush();
		IPushResult gtResult = gtPush.pushNotification(token, message, sound);

		System.out.println("《个推平台》推送结果："+gtResult.getResponse().toString());


		////  小米推送
		XMPush xmPush = new XMPush();
		Result r = xmPush.pushNotification(token, message,url, sound);

		System.out.println("《小米》推送结果："+r);
		
	////  华为推送
			HWPush hwPush = new HWPush();
			hwPush.pushNotification(token, message, url,sound);

		///  Umeng 推送
//		AndroidUnicast unicast = new AndroidUnicast("579830c6e0f55abb040007e4","sthht2mmizia7qugruip4kvrpza8bufu");
//		unicast.setDeviceToken(token);
//		///通知栏提示文字
//		unicast.setTicker("警报");
//		///通知标题
//		unicast.setTitle("警报");
//		///通知文字描述
//		unicast.setText(message);
//		unicast.setSound(sound);
//		unicast.goAppAfterOpen();
//		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
//
//		unicast.setProductionMode();
////		unicast.setTestMode();
//		client.send(unicast);
	}
}
