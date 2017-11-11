package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签
 * @author yy
 *
 */
public class Label implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标签id
	 */
	private Integer labelId;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 标签名
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	
	public Label() {
		super();
	}

	public Label(Integer labelId, String type, String name, Date createDate, Date updateDate) {
		super();
		this.labelId = labelId;
		this.type = type;
		this.name = name;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
}
