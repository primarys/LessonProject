package com.project.utils;

/**
 * 
 * @author Dobility
 *
 */
public class Const {

	public static final String RANDOM_IMG = "rand";
	public static final short LOGIN_PASSWORD = 1;       // 密码登录
	public static final short LOGIN_DYNAMICCODE = 2;     // 验证码登录
	public static final String PHONE_VERIFICATION_CODE = "phone_verification_code";


	// null
	public static final String NULL = "_NULL";
	public static final String BLANK = "_BLANK";
	public static final String Rows = "Rows";
	public static final String Page = "Page";
	public static final String ERROR_TIP = "errTip";
	public static final String RAND_IMG = "rand";	// 验证码图片
	// 账号
	public static final String USER_ADMIN = "admin";		// 超级管理员
	public static final String USER_TEACHER = "teacher";		// 教师
	public static final String USER_ADMIN_NAME = "admin";		// 超级管理员
	public static final String USER_STUDENT = "student";		// 学生
	public static final String USER_TEACHER_PRE = "T";		// 教师用户名开头
	public static final String DUFAULT_PASSWORD = "123456";
	// 创建账号的上限
	public static final int USER_CREATE_MAX = 1000;			// 一个管理员最多可以创建多少个教师
	// student性别
	public static final short STUDENT_SEX_MALE = 1;			// 男性
	public static final short STUDENT_SEX_FEMALE = 2;		// 女性
	// 点名常量
	public static final short ROLLCALL_RANDOM = 1;		// 随机点名
	public static final short ROLLCALL_ALL = 2;				// 地毯式点名
	public static final short ROLLCALL_PRESENT = 1;				// 出席
	public static final short ROLLCALL_LEAVE = 2;				// 请假
	public static final short ROLLCALL_LATE = 3;				// 迟到
	public static final short ROLLCALL_ABSENT = 4;				// 缺席
	public static final short ROLLCALL_NOT = 5;                 // 未点名
	public static final short SCORE = 90;                       // 默认平时分
	// 课堂常量
	public static final short CLASS_BEGIN = 1;                   // 上课开始
	public static final long CLASS_LAST = 1000*60*60*2;           // 上课持续时间，两小时
	//public static final long CLASS_LAST = 1000*10;                 // 上课持续时间，测试用例10秒钟
	// json传输结果
	public static final String JSON_SUCCESS = "true";
	public static final String JSON_FAIL = "false";
	// 图片常量
	public static final int	   IMG_DEFAULT_NUMBER = 2;			// 默认图片数量
	public static final String IMG_DEFAULT_TEACHER_HEAD = "/LessonProject/resource/image/common/default_teacher_head.jpg";				// 默认teacher头像图片路径
	public static final String IMG_DEFAULT_LESSON_COVER = "/LessonProject/resource/image/common/default_lesson_cover.jpg";					// 默认lesson封面图片路径

}