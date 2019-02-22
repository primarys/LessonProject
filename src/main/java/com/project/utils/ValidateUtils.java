package com.project.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author 羽中
 *
 */
public class ValidateUtils {

	//使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
	//所有组成验证码的字符
	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String email = " ab7925920@qq.com ";
//		String mobileNumber = " 0599-7925920 ";
//		String val = "";
//		System.out.println(ValidateUtils.checkVerificationCode(val));
		String url = "http://localhost:8080/LessonProject/manage/dashboard";
		String regex = ".*/manage/.*";
		System.out.println(ValidateUtils.check(regex, url));
		//System.out.println(ValidateTools.checkEmail(email));
		//checkValidTelephoeNo
	}
	/**
	 * 
	 * @param regex
	 * @param value
	 * @return
	 */
	public static boolean check(String regex,String value){
		boolean flag = false;
		try {
			if(StringUtils.isNotEmpty(value)){
				value = StringUtils.trim(value);
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(value);
				flag = m.matches();
			}
		} catch (Exception e) {
			System.out.println("正则表达式验证出错！123");
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return check(regex, email);
	}
	/**
	 * 验证固定电话或移动电话
	 * eg:0599-7925925,13388093155
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber){
		//String regex = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
		String regex = "^1[3|4|5|7|8]\\d{9}$";
		return check(regex, mobileNumber);
	}
	/**
	 * 匹配英文字母或汉字
	 * eg:"ShenZhen" "深圳"
	 * @param value
	 * @return
	 */
	public static boolean checkValidEnglishOrChinese(String value){
		String regex = "^[A-Za-z]*|[\u4E00-\u9FA5]*$";
		return check(regex, value);
	}
	////////////////////////////////
	public static boolean checkValidName(String name){
		String regex = "^([A-Za-z]+[\\s][A-Za-z]+)|[\u4E00-\u9FA5]*";
		return check(regex, name);
		
	}
	/**
	 * 匹配正整数
	 * 不包括0
	 * @param value
	 * @return
	 */
	public static boolean checkValidInteger(String value){
		String regex = "^[1-9]\\d*$";
		return check(regex, value);
	}
	/**
	 * 匹配验证码
	 * 六位正整数
	 * @param value
	 * @return
	 */
	public static boolean checkVerificationCode(String value){
		String regex = "^\\d{6}$";
		return check(regex, value);
	}
	/**
	 * 匹配整数
	 * 包括0
	 * @param value
	 * @return
	 */
	public static boolean checkValidNo(String value){
		String regex = "^-?\\d*$";
		return check(regex, value);
	}
	/**
	 * 验证非负整数(0+正整数)
	 * @param value
	 * @return
	 */
	public static boolean checkValidNonNegative(String value){
		String regex = "^\\d+$";
		return check(regex, value);
	}
	/**
	 * 匹配英文
	 * @param value
	 * @return
	 */
	public static boolean checkValidEnglish(String value){
		String regex = "^[A-Za-z]*$";
		return check(regex, value);
	}
	/**
	 * 匹配英文字母 或者汉字，数字 过滤特殊字符 
	 * @param value
	 * @return
	 */
	public static boolean checkValidNonSpecialChar(String value){
		String regex = "^[A-Za-z\u4E00-\u9FA5\\d]*$";
		return check(regex, value);
	}
	/**
	 * 验证用户名注册是否合法(由数字、26个英文字母或者下划线组成的字符串 )
	 * eg:jane_12306
	 * @param value
	 * @return
	 */
	public static boolean checkRegUserName(String value){
		String regex = "^\\w{6,}$";
		return check(regex, value);
	}
	//////////////////
	/**
	 * 验证输入密码长度(6-18位)
	 * @param value
	 * @return
	 */
	public static boolean checkPasswordLength(String value){
		String regex = "^\\S{6,18}$";
		return check(regex, value);
	}
	/**
	 * 验证手机号码 
	 * @param value telNo 电话号码字符串 130到139 和 150，152 ，157，158，159 ，186，189，187 
	 * @return
	 */
	public static boolean checkValidMobileNo(String value){
		String regex = "^((13[0-9])|(15[02789])|(18[679]))\\d{8}$";
		return check(regex, value);
	}
	//////////////
	/**
	 * 验证电话号码 
	 * @param value 
	 * @return
	 */
	public static boolean checkValidTelephoeNo(String value){
		String regex = "(0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7})$";
		return check(regex, value);
	}
	/**
	 * 验证15位或18位身份证号码
	 * @param idCard
	 * @return
	 */
	public static boolean checkValidIdCard(String idCard){
		String regex18 = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$";
		String regex15 = "^\\d{15}$";
		return check(regex18, idCard)||check(regex15, idCard);
	}
	
	/**
	 * 验证银行卡号
	 * @param cardId
	 * @return
	 */
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
    	if(bit == 'N'){
        	return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * @param nonCheckCodeCardId
	 * @return
	 */
	 public static char getBankCardCheckCode(String nonCheckCodeCardId){
	    if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
	            || !nonCheckCodeCardId.matches("\\d+")) {
	        //如果传的不是数据返回N
	    	return 'N';
	    }
	    char[] chs = nonCheckCodeCardId.trim().toCharArray();
	    int luhmSum = 0;
	    for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
	    	int k = chs[i] - '0';
	    	if(j % 2 == 0) {
	    		k *= 2;
	    		k = k / 10 + k % 10;
	    	}
	    	luhmSum += k;           
	    }
	    return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
	 }
	 
	 //姓名须纯汉字(无空格,2-6个)或纯英文(2-20个)
	 public static boolean checkName(String name){
		 String reg = "(([\u4E00-\u9FA5]{2,6})|([a-zA-Z0-9\\/ .]{2,20}))";
		 
		 return check(reg, name);
	 }
	 
	 //昵称为汉字、数字、字母、下划线组合（长度3-16）
	 public static boolean checkNickName(String name){
		 String reg = "[a-zA-Z0-9_\u4e00-\u9fa5_]{2,20}";
		 
		 return check(reg, name);
	 }

	/**
	 * 使用系统默认字符源生成验证码
	 * @param verifySize    验证码长度
	 * @return
	 */
	public static String generateVerifyCode(int verifySize){
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}

	/**
	 * 使用指定源生成验证码
	 * @param verifySize    验证码长度
	 * @param sources   验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources){
		 if(sources == null || sources.length() == 0){
			 sources = VERIFY_CODES;
		 }
		 int codesLen = sources.length();
		 Random rand = new Random(System.currentTimeMillis());
		 StringBuilder verifyCode = new StringBuilder(verifySize);
		 for(int i = 0; i < verifySize; i++){
			 verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
		 }
		 return verifyCode.toString();
	 }
}
