package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到
 * @author yy
 *
 */
public class Sign implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 签到id
	 */
	private Integer signId;
	/**
	 * 等级默认1，最大7
	 */
	private Integer level;
	/**
	 * 积分
	 */
	private Double money;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 任务人ID
	 */
	private Integer acountId;
	
	public Sign() {
		super();
	}

	public Sign(Integer signId, Integer level, Double money, Date createDate, Integer acountId) {
		super();
		this.signId = signId;
		this.level = level;
		this.money = money;
		this.createDate = createDate;
		this.acountId = acountId;
	}

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
