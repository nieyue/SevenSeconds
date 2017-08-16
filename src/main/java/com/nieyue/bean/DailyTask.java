package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 日常任务
 * @author yy
 *
 */
public class DailyTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日常任务id
	 */
	private Integer dailyTaskId;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 次数
	 */
	private Integer frequency;
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
	
	public DailyTask() {
		super();
	}

	public Integer getDailyTaskId() {
		return dailyTaskId;
	}

	public void setDailyTaskId(Integer dailyTaskId) {
		this.dailyTaskId = dailyTaskId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
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

	public DailyTask(Integer dailyTaskId, String type, Integer frequency, Double money, Date createDate,
			Integer acountId) {
		super();
		this.dailyTaskId = dailyTaskId;
		this.type = type;
		this.frequency = frequency;
		this.money = money;
		this.createDate = createDate;
		this.acountId = acountId;
	}


}
