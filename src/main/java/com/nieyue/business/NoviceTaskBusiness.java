package com.nieyue.business;

import org.springframework.context.annotation.Configuration;

/**
 * 新手任务业务
 * @author 聂跃
 * @date 2017年8月8日
 */
@Configuration
public class NoviceTaskBusiness {
  /**
   * 新手任务一次性触发事件
   * @param frequency 第几次
   * @return money积分
   */
	public Double disposableTrigger(int frequency){
		Double money=0.0;
		//1.绑定手机号+2000积分  
		if(frequency==1){
			money=2000.0;
		}else
		//2.绑定微信+2000积分
		if(frequency==2){
			money=2000.0;
		}else
		//3.阅读资讯1分钟+2000积分
		if(frequency==3){
			money=2000.0;
		}else
		//4.观看视频1分钟+2000积分（延后开放）
		if(frequency==4){
			money=2000.0;
		}else
		//0.首次收徒+20000积分
		if(frequency==0){
			money=20000.0;
		}else{
			money=0.0;
		}
		return money;
	}
}
