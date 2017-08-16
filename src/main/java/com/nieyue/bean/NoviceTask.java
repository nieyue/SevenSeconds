package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 新手任务
 * @author yy
 *
 */
public class NoviceTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 新手任务id
	 */
	private Integer noviceTaskId;
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
	
	public NoviceTask() {
		super();
	}

	public NoviceTask(Integer noviceTaskId, Integer frequency, Double money, Date createDate, Integer acountId) {
		super();
		this.noviceTaskId = noviceTaskId;
		this.frequency = frequency;
		this.money = money;
		this.createDate = createDate;
		this.acountId = acountId;
	}

	public Integer getNoviceTaskId() {
		return noviceTaskId;
	}

	public void setNoviceTaskId(Integer noviceTaskId) {
		this.noviceTaskId = noviceTaskId;
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


}
