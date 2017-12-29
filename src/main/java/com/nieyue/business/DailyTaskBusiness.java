package com.nieyue.business;

import org.springframework.context.annotation.Configuration;

/**
 * 日常业务
 * @author 聂跃
 * @date 2017年8月8日
 */
@Configuration
public class DailyTaskBusiness {
	//阅读资讯
	private  int readingInformationNumber=10;
	//分享资讯
	private  int shareInformationNumber=1;
	//阅读推送
	//private  int readingPushNumber=2;
	//评论资讯 
	//private  int commentInformationNumber=3;
	//分享朋友圈收徒
	private  int shareCircleRecruitApprenticeNumber=1;
	//分享微信群收徒
	private  int shareWechatGroupApprenticeNumber=1;
	//点赞
	//private  int clickFabulousNumber=20;
	//转发推广文章  有效阅读
	//private  int effectiveReadingNumber=3;
	//红包抽奖赚多多
	private  int redBagLotteryNumber=1;
	
  /**
   * 每日触发日常
   * @param type 任务类型
   * @param nowNumber 完成次数
   * @return money积分
   */
	public Double dailyTrigger(int type,int nowNumber){
		Double money=0.0;
		// 1.新闻分享   0/1        30积分
		if(type==1&& nowNumber>=shareInformationNumber){
			money=30.0;
		}else
		//2.阅读资讯   0/10        100积分
		if(type==2&& nowNumber>=readingInformationNumber){
			money=100.0;
		}else
		//3.分享朋友圈收徒  0/1     100积分
		if(type==3&& nowNumber>=shareCircleRecruitApprenticeNumber){
			money=100.0;
		}else
		// 4.分享到微信群收徒  0/1    100积分
		if(type==4 && nowNumber>=shareWechatGroupApprenticeNumber){
			money=100.0;
		}else
		// 5.红包抽奖赚多多   0/1     500积分
		if(type==5 && nowNumber>=redBagLotteryNumber){
			money=500.0;
		}else{
			money=0.0;
		}
		return money;
	}
	/**
	 * 徒弟回馈师傅积分 徒弟每天获得200积分奖励
	 * @param frequency 第几次
	 * @return money 积分
	 */
	public Double apprenticeNoviceTask(int frequency){
		Double money=0.0;
		//1.5000
		if(frequency==1){
			money=5000.0;
		}else
		//2.4000
		if(frequency==2){
			money=4000.0;
		}else
		//3.3000
		if(frequency==3){
			money=3000.0;
		}else
		//3.3000
		if(frequency==4){
			money=3000.0;
		}else
		//5.3000
		if(frequency==5){
			money=3000.0;
		}else
		//6.4000
		if(frequency==6){
			money=4000.0;
		}else
		//7.5000
		if(frequency==7){
			money=5000.0;
		}else{
			money=0.0;
		}
		return money;
	}
	/**
	 * 优质评论
	 * @param nowNumber 点赞次数
	 * @return money 积分
	 */
/*	public Double qualityComment(int nowNumber){
		Double money=0.0;
		// 10积分 （点赞20次）
		if(nowNumber>=clickFabulousNumber){
			money=10.0;
		}else{
			money=0.0;
		}
		return money;
	}*/
	/**
	 * 转发推广文章
	 * @param nowNumber 点赞次数
	 * @return money 积分
	 */
/*	public Double forwardingPromotionArticle(int nowNumber){
		Double money=0.0;
		// 10积分（获得3个有效阅读）
		if(nowNumber>=effectiveReadingNumber){
			money=10.0;
		}else{
			money=0.0;
		}
		return money;
	}*/
	
}
