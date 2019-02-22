package com.project.base;

import java.io.Serializable;

/**
 * 基础pojo类
 * @author 羽中
 *
 */
public class BaseModel implements Comparable, Serializable {

	/**
	 * 
	 */
	
	protected int id;
	public BaseModel() {
		super();
		this.delFlag = 1;
	}
	protected short delFlag;	//删除状态（0：已删除，1：未删除）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public short getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(short delFlag) {
		this.delFlag = delFlag;
	}
	public int compareTo(Object o) {
		BaseModel other = (BaseModel)o;
		if(this.id > other.getId()){
			return 1;
		}else if(this.id < other.getId()){
			return -1;
		}else{
			return 0;
		}
	}

	@Override
	public int hashCode() {
		return id;
	}
}
