package com.project.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 *
 * @author 朱源
 */
public class MessageSend {
	static Logger log = Logger.getLogger(MessageSend.class);
	private static Map<String,LinkedList<Long>> ipMap = new ConcurrentHashMap<String,LinkedList<Long>>();
	private static Map<String,Integer> blacklistMap = new ConcurrentHashMap<String,Integer>();
	private static final long IP_TIME = 86400000;
	private static final int SMS_TIMES = 10;
    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    //通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    // 发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    private static String apiKey = "18223cf0207e6bd47451622715cd0d9d";

    /**
     * 通用接口发短信
     *
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String sendSms(String text, String mobile) throws IOException {
    	log.info("sendSms begin...");
    	log.info("text---"+text);
    	log.info("mobile---"+mobile);
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apiKey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }
    
    /**
     * 动态登录验证
     * @param verificationCode	六位验证码
     * @param mobile	电话号码
     * @return
     * @throws IOException
     */
    public static String sendDynamicVerification(String verificationCode, String mobile, String ip) throws IOException {

		Date date = new Date();
		long time = date.getTime();
		log.info("动态登录验证 IP:"+ip+"  Mobile:"+mobile+"  Time:"+date.toString());
		LinkedList timeList = ipMap.get(ip);
		//把ip加入map,用來防护恶意短信验证
		if (timeList == null) {
			timeList = new LinkedList<Long>();
			timeList.add(time);
			ipMap.put(ip, timeList);
		} else {
			timeList.add(time);
			if (timeList.size() > SMS_TIMES) {
				long latest = (Long) timeList.removeFirst();
				//一天內最多只能申请十次发送短信
				if (time - latest < IP_TIME) {
					log.info("拒绝发送短信  IP:"+ip+"  Mobile:"+mobile+"  Time:"+date.toString());
					return "error";
				}
			}
		}
		//防止內存泄漏,每次清除超过三天的過期名單
		Iterator<Map.Entry<String,LinkedList<Long>>> it = ipMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,LinkedList<Long>> s = it.next();
			if(s.getValue()!=null){
				if(s.getValue().getLast()!=null && time-s.getValue().getLast()>IP_TIME*3)
					it.remove();
			}
		}
		String text = "【娱学科技】"+ "您的动态验证码是" + verificationCode + "，如非本人操作，请忽略本短信。";
		return sendSms(text, mobile);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
    /**
     * 生成验证码
     * @return
     */
    public static String getVerificationCode(){
    	Random random = new Random();
    	String code = String.valueOf(random.nextLong());
    	return code.substring(1, 7);
    }

}