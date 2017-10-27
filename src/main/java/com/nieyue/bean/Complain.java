package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 投诉
 * @author yy
 *
 */
public class Complain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 投诉id
	 */
	private Integer complainId;
	/**
	 * 投诉类型，默认1非法政治言论，2情色暴力，3诱导诈骗
	 */
	private Integer type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 状态，默认审核1，采纳2
	 */
	private Integer status;
	/**
	 * 弹幕id
	 */
	private Integer barrageId;
	/**
	 * 投诉人ID
	 */
	private Integer acountId;
	
	public Complain() {
		super();
	}

	public Complain(Integer complainId, Integer type, String content, Date createDate, Date updateDate, Integer status,
			Integer barrageId, Integer acountId) {
		super();
		this.complainId = complainId;
		this.type = type;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
		this.barrageId = barrageId;
		this.acountId = acountId;
	}

	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBarrageId() {
		return barrageId;
	}

	public void setBarrageId(Integer barrageId) {
		this.barrageId = barrageId;
	}

	public Integer getAcountId() {
		return acountId;
	}

	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
