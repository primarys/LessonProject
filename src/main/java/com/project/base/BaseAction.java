package com.project.base;

import com.project.common.WebApplication;
import com.project.utils.JsonValueFormat;
import com.project.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Dobility
 *
 * @param <T>
 */
public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T>, Preparable, ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
	protected T model;
	protected List<T> models = new ArrayList<T>();

	protected HttpSession session;
	protected HttpServletResponse response;
	protected HttpServletRequest request;

	protected Logger log;
	protected Class<?> clazz;
	/*******************************************************************/
	protected int defaultSize = 10; // 每页默认大小
	/* protected int currentPage = 1; // 当前页 */
	/*******************************************************************/
	// action查询条件
	protected Map<String, Object> paramMap = new HashMap<String, Object>();
	protected PageBean pageBean = new PageBean(defaultSize);

	// 封装json参数
	protected Map<String, Object> dataMap = new HashMap<String, Object>();
	/*******************************************************************/
	protected final static String TYPE_REDIRECT = "redirect";
	protected final static String TYPE_CHAIN = "chain";
	protected final static String TYPE_REDIRECT_ACTION = "redirectAction";

	/*******************************************************************/

	/**
	 * 获取项目根路径
	 */
	public String getWebRoot() {
		request = WebApplication.getRequest();
		return request.getScheme() + "://" 											// http
				+ request.getServerName()											// localhost
				+ (request.getLocalPort() == 80 ? "" : ":" + request.getLocalPort())// 端口号
				+ request.getContextPath() 											// LessonProject
				+ (request.getContextPath().equals("") ? "" : "/");					// 补偿结尾的'/'
	}

	@SuppressWarnings("unchecked")
	public BaseAction() {
		try {
			ParameterizedType type;
			type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			clazz = (Class<?>) type.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
			log = Logger.getLogger(clazz);
			session = WebApplication.getSession();
			request = WebApplication.getRequest();
			response = WebApplication.getResponse();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void prepare() {
	};

	/******************************* 重写getParameter方法 begin ************************************/
	/**
	 *
	 * @param arg
	 *            变量名
	 * @param defaultValue
	 *            默认值
	 * @return String型
	 */
	public String getParameter(String arg, String defaultValue) {
		request = WebApplication.getRequest();
		String temp = request.getParameter(arg);
		if (StringUtils.isEmpty(temp)) {
			return defaultValue;
		} else {
			return temp.trim();
		}
	}
	public void addActionError(String anErrorMessage){
		String s=anErrorMessage;
		log.info(s);
	}
	public void addActionMessage(String aMessage){
		String s=aMessage;
		//System.out.println(s);
		log.info(s);
	}
	public void addFieldError(String fieldName, String errorMessage){
		String s=errorMessage;
		String f=fieldName;
		//System.out.println(s);
		//System.out.println(f);
		log.info(s+" "+f);
	}
	/**
	 *
	 * @param arg
	 *            变量名
	 * @return String型
	 */
	public String getParameter(String arg) {
		return getParameter(arg, "");
	}

	/**
	 * 获取Integer变量 如果为null,返回null
	 * @param arg 变量名
	 */
	public Integer getIntegerParameter(String arg) {
		String temp = request.getParameter(arg);
		if (temp == null) {
			return null;
		} else {
			temp = temp.trim();

			try {
				return Integer.parseInt(temp);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
	}

	/**
	 *获取Double变量 如果为空,则返回空
	 * @param arg 变量名
	 */
	public Double getDoubleParameter(String arg) {
		String temp = request.getParameter(arg);
		if (temp == null) {
			return null;
		} else {
			temp = temp.trim();
			try {
				return Double.parseDouble(temp);
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}
	}

	/**
	 *
	 * @param arg
	 *            变量名
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public int getIntParameter(String arg, int defaultValue) {
		request = WebApplication.getRequest();
		String temp = request.getParameter(arg);
		if (temp == null) {
			return defaultValue;
		} else {
			temp = temp.trim();
			try {
				return Integer.parseInt(temp);
			} catch (Exception e) {
				log.error(e.getMessage());
				return defaultValue;
			}
		}
	}

	/**
	 * @param arg 变量名
	 * @return
	 */
	public int[] getIntParameters(String arg) {
		request = WebApplication.getRequest();
		if (getParameter(arg).equals("")) {
			return new int[0];
		} else {
			String[] temp = getParameter(arg).split(",");
			int[] array = new int[temp.length];
			for (int i = 0; i < temp.length; i++) {
				array[i] = Integer.parseInt(temp[i]);
			}
			return array;
		}
	}


	/******************************* 重写getParameter方法 end ************************************/
	/******************************* 向前台返回json格式数据 begin ************************************/
	public void writeStringToResponse(String content) {
		try {
			// 设置字符集
			response.setCharacterEncoding("utf-8");
			// 获取向前台的打印流
			PrintWriter pw = response.getWriter();
			pw.write(content);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeJsonToResponse(List<?> list) {
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "addTime"/*, "childAreas","suppliers"*/}); // 除去addTime属性
		JSONArray json = JSONArray.fromObject(list, config);
		writeStringToResponse(json.toString());
	}

	/**
	 * 过滤excludes中的字段
	 * @param list
	 * @param excludes
	 */
	public void writeJsonToResponse(List<?> list, String[] excludes) {
		JsonConfig config = new JsonConfig();
		config.setExcludes(excludes/*"childAreas","suppliers"*/); // 除去addTime属性
		JSONArray json = JSONArray.fromObject(list, config);
		writeStringToResponse(json.toString());
	}

	public void writeJsonToResponse(Object o, Map<String, Object> otherParams) {
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "addTime" });// 除去dept属性
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		if(o != null){
            tempMap.put("Model", o);
        }
		if (otherParams != null) {
			Iterator<Entry<String, Object>> iter = otherParams.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				tempMap.put(entry.getKey(), entry.getValue());
			}
		}
		JSONObject json = JSONObject.fromObject(tempMap, config);
		writeStringToResponse(json.toString());
	}


	/**
	 * 把Object转抱成Json时更改数据的默认转换格式
	 * @param o
	 * @param otherParams 其它参数
	 * @param excludes 不进行序列化属性的名称
	 */
	public void writeJsonToResponseWithDataFormat(Object o, Map<String, Object> otherParams, String excludes[]) {
		JsonConfig config = new JsonConfig();
		//更改Date类型转成json数据的格式化形式
		config.registerJsonValueProcessor(Date.class, new JsonValueFormat());
		config.setExcludes(excludes);// 除去dept属性
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("Model", o);
		if (otherParams != null) {
			Iterator<Entry<String, Object>> iter = otherParams.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Entry<String, Object> entry = iter.next();
				tempMap.put(entry.getKey(), entry.getValue());
			}
		}
		JSONObject json = JSONObject.fromObject(tempMap, config);
		writeStringToResponse(json.toString().replaceAll("null", "[]"));

	}

	/******************************* 向前台返回json格式数据 end ************************************/
	// ---------------getter and setter begin--------------------
	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<T> getModels() {
		return models;
	}

	public void setModels(List<T> models) {
		this.models = models;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

}
