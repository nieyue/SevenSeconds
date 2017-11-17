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
		//第一次：阅读3篇文章  +28积分
		if(frequency==1){
			money=28.0;
		}else
		//第二次：阅读3篇文章；转发一篇文章  +68积分
		if(frequency==2){
			money=68.0;
		}else
		//第三次：阅读3篇文章；分享至朋友圈收徒  +28积分
		if(frequency==3){
			money=28.0;
		}else
		//第四次：阅读3篇文章；评论3篇文章  +68积分
		if(frequency==4){
			money=68.0;
		}else
		//第五次：阅读5篇文章  +88积分
		if(frequency==5){
			money=88.0;
		}else
		//第零次: 获得一名徒弟  +108积分（这个任务为达成条件任务，无需排序）
		if(frequency==0){
			money=108.0;
		}else{
			money=0.0;
		}
		return money;
	}
}
