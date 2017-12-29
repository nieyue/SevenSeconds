package com.nieyue.business;

import org.springframework.context.annotation.Configuration;

/**
 * 签到
 * @author 聂跃
 * @date 2017年8月8日
 */
@Configuration
public class SignBusiness {
  /**
   * 签到
   * @param level 等级1-7
   * @return money积分
   */
	public Double signTrigger(int level){
		Double money=0.0;
		//第一天领50积分；  
		if(level==1){
			money=50.0;
		}else
		//第二天领100积分；
		if(level==2){
			money=100.0;
		}else
		//第三天领100积分；
		if(level==3){
			money=100.0;
		}else
		//第四天领1500积分；
		if(level==4){
			money=1500.0;
		}else
		//第五天领150积分；
		if(level==5){
			money=150.0;
		}else
		//第六天领200积分；
		if(level==6){
			money=200.0;
		}else
		//第七天领5000积分；；
		if(level==7){
			money=5000.0;
		}else{
			money=0.0;
		}
		return money;
	}
}
