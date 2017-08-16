package com.nieyue.business;

import org.springframework.context.annotation.Configuration;

/**
 * 合伙人业务
 * @author 聂跃
 * @date 2017年8月8日
 */
@Configuration
public class PartnerBusiness {
  /**
   * 合伙人进贡比比例
   * @param partnerNumber 合伙人数量
   * @return scale进贡比例
   */
	public Double getPartnerScale(int partnerNumber){
		Double scale=0.2;//默认最低0.2
		//合伙人数量1-5
		if(partnerNumber<=5){
			scale=0.2;
		}else
		//合伙人数量6-15
		if(partnerNumber>=6 && partnerNumber<=15){
			scale=0.3;
		}else
		//合伙人数量16-30
		if(partnerNumber>=16 && partnerNumber<=30){
			scale=0.4;
		}else
		//合伙人数量大于等于31
		if(partnerNumber>=31){
			scale=0.5;
		}
		return scale;
	}
}
