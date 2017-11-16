package com.nieyue.bean;

import java.io.Serializable;

/**
 * 财务数据
 * @author 聂跃
 * @date 2017年7月25日
 */
public class FinanceDataDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 总余额
	 */
	private Double money;
	/**
	 * 总充值金额
	 */
	private Double recharge;
	/**
	 * 总消费金额
	 */
	private Double consume;
	/**
	 * 总提现金额
	 */
	private Double withdrawals;
	/**
	 * 总自身收益
	 */
	private Double  selfProfit;
	/**
	 * 总合伙人收益
	 */
	private Double  partnerProfit;
	/**
	 * 总基准收益
	 */
	private Double  baseProfit;
	/**
	 * 账户所有者Id
	 */
	private Integer  acountId;
	public FinanceDataDTO() {
		super();
	}
	public FinanceDataDTO(Double money, Double recharge, Double consume, Double withdrawals, Double selfProfit,
			Double partnerProfit, Double baseProfit, Integer acountId) {
		super();
		this.money = money;
		this.recharge = recharge;
		this.consume = consume;
		this.withdrawals = withdrawals;
		this.selfProfit = selfProfit;
		this.partnerProfit = partnerProfit;
		this.baseProfit = baseProfit;
		this.acountId = acountId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getRecharge() {
		return recharge;
	}
	public void setRecharge(Double recharge) {
		this.recharge = recharge;
	}
	public Double getConsume() {
		return consume;
	}
	public void setConsume(Double consume) {
		this.consume = consume;
	}
	public Double getWithdrawals() {
		return withdrawals;
	}
	public void setWithdrawals(Double withdrawals) {
		this.withdrawals = withdrawals;
	}
	public Double getSelfProfit() {
		return selfProfit;
	}
	public void setSelfProfit(Double selfProfit) {
		this.selfProfit = selfProfit;
	}
	public Double getPartnerProfit() {
		return partnerProfit;
	}
	public void setPartnerProfit(Double partnerProfit) {
		this.partnerProfit = partnerProfit;
	}
	public Double getBaseProfit() {
		return baseProfit;
	}
	public void setBaseProfit(Double baseProfit) {
		this.baseProfit = baseProfit;
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
