package com.project.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.project.base.BaseModel;
import com.project.base.BaseService;

public class DataFetch<T extends BaseModel> {
	
	private Class<?> classt;
	private Logger log;
	
	private BaseService<T> baseService;
	private List<T> dataList = null;		//获取的数据
	private Random random;					
	private Integer randomCount ;		//随机数产生的范围
	
	private int len = 1;		//数据长度
	private String hql ;		//hql语句
	
	
	@SuppressWarnings("unchecked")
	public DataFetch(BaseService<T> baseService, Class classt) {
		log = Logger.getLogger("DataFetch");
		try {
			this.baseService = baseService;
		
			this.classt = classt;

			hql = " from " + classt.getSimpleName();
			random = new Random(47);
			randomCount = 200;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage());
		}
	}
	
	public DataFetch( BaseService<T> baseService, Class classt, String hql, int len ) {
		this(baseService, classt);
		this.hql = hql;
		this.len = len;
	}
	
	public T getOneRandomObject(int randCount) {
		if (dataList == null) {
			dataList = getSeveralObject(randCount);
		} 
		
		return dataList.get(random.nextInt(dataList.size()));
	}
	
	public T getOneRandomObject() {
		return this.getOneRandomObject(randomCount);
	}
	
	/**
	 * 获取第一个数据
	 */
	public T getOneObject()  {
		T result = null;
		try {
			result = getSeveralObject(1).get(0);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 取某些数据
	 */
	public	List<T> getSeveralObject()  {
		return getSeveralObject(this.len);
	}
	
	public List<T> getSeveralObject(int len)  {
		List<T> list = baseService.findByHql(hql, new HashMap<String, Object>(), new PageBean(len));
		if (list.size() == 0) {
			log.error("ERROR：请先添加 "+classt.getSimpleName()+" 表的数据");
		}
		return list;
	}
	

	public Integer getRandomCount() {
		return randomCount;
	}

	public void setRandomCount(Integer randomCount) {
		this.randomCount = randomCount;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}
	
	
}
