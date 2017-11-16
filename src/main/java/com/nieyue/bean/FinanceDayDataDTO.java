package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 财务日数据
 * @author 聂跃
 * @date 2017年7月25日
 */
public class FinanceDayDataDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户收益（平台支出）
	 */
	private Double  userExpenditure;
	/**
	 * 用户消费（平台收益）
	 */
	private Double  platformRevenue;
	/**
	 * 日期
	 */
	private Date  createDate;
	public FinanceDayDataDTO(Double userExpenditure, Double platformRevenue, Date createDate) {
		super();
		this.userExpenditure = userExpenditure;
		this.platformRevenue = platformRevenue;
		this.createDate = createDate;
	}
	public FinanceDayDataDTO() {
		super();
	}
	public Double getUserExpenditure() {
		return userExpenditure;
	}
	public void setUserExpenditure(Double userExpenditure) {
		this.userExpenditure = userExpenditure;
	}
	public Double getPlatformRevenue() {
		return platformRevenue;
	}
	public void setPlatformRevenue(Double platformRevenue) {
		this.platformRevenue = platformRevenue;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
