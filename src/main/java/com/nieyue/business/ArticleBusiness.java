package com.nieyue.business;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nieyue.service.DailyDataService;
import com.nieyue.util.DateUtil;

/**
 * 文章业务
 * @author 聂跃
 * @date 2017年8月8日
 */
@Configuration
public class ArticleBusiness {
	@Resource
	private DailyDataService dailyDataService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Value("${myPugin.projectName}")
	String projectName;
  /**
   * 获取阅读文章的奖励积分
   * @param type 类型
   * @param articleId 文章ID
   * @param acountId 推广人ID
   * @param readingNumber 阅读数
   * @return money 积分数
   */
	public Double getReadingArticleMoney(
			String type,
			Integer articleId,
			Integer acountId,
			Integer readingNumber){
		Double money=0.0;
		if(type.equals("推广")){
			money=1.0;
		}else{
			Integer step = getReadingNumberStep(readingNumber);
			//第一阶段 5~10 8~15
			if(step.equals(1)){
				money=Math.ceil(Math.random()*7)+8;
			}else if(step.equals(2)){
			//第二阶段 30~50 28~58
				money=Math.ceil(Math.random()*30)+28;
			}else if(step.equals(3)){
			//第二阶段 100~300 88~108
				money=Math.ceil(Math.random()*20)+88;
			}
		}
		
		return money;
	}
	
	/**
	 * 第几级阶段
	 * @param readingNumber 阅读数
	 * @return step
	 */
	public Integer getReadingNumberStep(
			Integer readingNumber){
		Integer step=0;//默认0阶段
		//第一阶段 5~10 8~15
		if(readingNumber>=5 && readingNumber<=10){
			step=1;
		}else if(readingNumber>=30 && readingNumber<=50){
		//第二阶段 30~50 28~58
			step=2;
		}else if(readingNumber>=100 && readingNumber<=300){
		//第二阶段 100~300 88~108
			step=3;
		}
		return step;
	}
	/**
	 * 检查是否能获得阅读文章的奖励积分
	 * @param type 类型
	 * @param articleId 文章ID
	 * @param acountId 推广人ID
	 * @return b true/false
	 */
	public boolean checkReadingArticleMoney(
			String type,
			Integer articleId,
			Integer acountId,
			Integer readingNumber
			){
		boolean b=false;
		if(type.equals("推广")){
			b=true;
		}else{
			if(Math.random()>0.5){//0.5的概率
			Integer step = getReadingNumberStep(readingNumber);	
			//dailyDataService.browsePagingDailyData(new Date(), articleId, acountId, 1, Integer.MAX_VALUE, "daily_data_id", "asc");
			BoundSetOperations<String, String> bsoadataturn= stringRedisTemplate.boundSetOps(projectName+"AcountId"+acountId+"Data"+DateUtil.getImgDir()+"Turn");
			BoundSetOperations<String, String> bsoaadataturn = stringRedisTemplate.boundSetOps(projectName+"AcountId"+acountId+"ArtiticlId"+articleId+"Data"+DateUtil.getImgDir()+"Turn");
			bsoaadataturn.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);
			Iterator<String> iterator = bsoadataturn.members().iterator();
		        while(iterator.hasNext()){
		            String it = iterator.next();
		            //相同文章
		            if(it.equals(articleId.toString())){//1100 ==1100
		            	//
		            	if(step>0){
		            		Integer oldArticle = bsoaadataturn.size().intValue();
		            		bsoaadataturn.add(step.toString());
		            		Integer newArticle = bsoaadataturn.size().intValue();
		            		if(newArticle>oldArticle){
		            			//新进的转发文章奖励
		            			b=true;
		            		}
		            	}
		            }
		        }
			}

		}
		
		return b;
	}
	public static void main(String[] args) {
		ArticleBusiness ab=new ArticleBusiness();
		String type="推广3";
		Integer articleId=1000;
		Integer acountId=100;
		Integer readingNumber=6;
		System.out.println("4:"+ab.getReadingArticleMoney(type, articleId, acountId, 4));//0
		System.out.println("5:"+ab.getReadingArticleMoney(type, articleId, acountId, 5));//0
		System.out.println("6:"+ab.getReadingArticleMoney(type, articleId, acountId, 6));//0
		System.out.println("10:"+ab.getReadingArticleMoney(type, articleId, acountId, 10));
		System.out.println("11:"+ab.getReadingArticleMoney(type, articleId, acountId, 11));
		System.out.println("29:"+ab.getReadingArticleMoney(type, articleId, acountId, 29));
		System.out.println("30:"+ab.getReadingArticleMoney(type, articleId, acountId, 30));
		System.out.println("36:"+ab.getReadingArticleMoney(type, articleId, acountId, 36));
		System.out.println("50:"+ab.getReadingArticleMoney(type, articleId, acountId, 50));
		System.out.println("51:"+ab.getReadingArticleMoney(type, articleId, acountId, 51));
		System.out.println("99:"+ab.getReadingArticleMoney(type, articleId, acountId, 99));
		System.out.println("100:"+ab.getReadingArticleMoney(type, articleId, acountId, 100));
		System.out.println("122:"+ab.getReadingArticleMoney(type, articleId, acountId, 122));
		System.out.println("300:"+ab.getReadingArticleMoney(type, articleId, acountId, 300));
		System.out.println("301:"+ab.getReadingArticleMoney(type, articleId, acountId, 301));
		
		/*for (int i = 0; i < 10000; i++) {
			double a=Math.ceil(Math.random()*30)+28;
			if(a>=58){
				
			System.out.println(a);
			}
		}*/
		String aaa="1100&1";
		System.out.println(aaa.indexOf("&"));
		System.out.println(aaa.substring(0,aaa.indexOf("&")));
		System.out.println(aaa.substring(aaa.indexOf("&")+1));
		System.out.println(new Integer("22"));
	}
}
