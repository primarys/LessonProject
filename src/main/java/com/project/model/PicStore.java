package com.project.model;

import com.project.base.BaseModel;

import java.io.Serializable;

/**
 * 图片存储
 * @author Dobility
 */
public class PicStore extends BaseModel implements Serializable {

	private static final long serialVersionUID = 3582050129739799596L;
	// 图片名称
	private String picName;
	// 收缩后的小图片位置
	private String url;
	// 原始图片位置
	private String oriUrl;

	public PicStore() {
		super();
	}

	public PicStore(String picName, String url, String oriUrl) {
		super();
		this.picName = picName;
		this.url = url;
		this.oriUrl = oriUrl;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOriUrl() {
		return oriUrl;
	}

	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}

}
