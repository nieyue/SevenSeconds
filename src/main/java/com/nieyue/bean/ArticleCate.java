package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章类型
 * @author yy
 *
 */
public class ArticleCate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文章类型id
	 */
	private Integer articleCateId;
	
	/**
	 * 文章类型名
	 */
	private String name;
	/**
	 * 持有者，默认为0公共，1平台方，2企业号，3个人号
	 */
	private Integer holder;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	public ArticleCate() {
		super();
	}
	
	public ArticleCate(Integer articleCateId, String name, Integer holder, Date updateDate) {
		super();
		this.articleCateId = articleCateId;
		this.name = name;
		this.holder = holder;
		this.updateDate = updateDate;
	}

	public Integer getarticleCateId() {
		return articleCateId;
	}
	public void setarticleCateId(Integer articleCateId) {
		this.articleCateId = articleCateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getHolder() {
		return holder;
	}
	public void setHolder(Integer holder) {
		this.holder = holder;
	}

	@Override
	public String toString() {
		return "ArticleCate [articleCateId=" + articleCateId + ", name=" + name + ", holder=" + holder + ", updateDate="
				+ updateDate + "]";
	}
	
}
