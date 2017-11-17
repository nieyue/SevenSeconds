package com.nieyue.business;

import java.util.HashMap;
import java.util.Map;
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
	* 获取阅读文章的等级和奖励积分
   * @param type 类型
   * @param articleId 文章ID
   * @param acountId 推广人ID
   * @param readingNumber 阅读数
   * @return map,step等级  money 积分数
	 */
	public Map<String,Object> getReadingArticleMap(
			String type,
			Integer articleId,
			Integer acountId,
			Integer readingNumber){
		Integer step=0;//默认0阶段
		Double money=0.0;//积分
		Boolean isCheck=false;//是否检查当前阶段已经获得奖励，默认否
		Map<String,Object> map=new HashMap<String,Object>();
		if(type.equals("推广")){
			step=-1;//推广每个阅读5积分
			money=5.0;
			/*//第一阶段 5~15 15~20
			if(readingNumber>=5 && readingNumber<=15){
				step=1;
				money=Math.ceil(Math.random()*5)+15;
				if(readingNumber==15){
					isCheck=true;
				}
			}else if(readingNumber>=16 && readingNumber<=25){
			//第二阶段 16~25 15~20
				step=2;
				money=Math.ceil(Math.random()*5)+15;
				if(readingNumber==25){
					isCheck=true;
				}
			}else if(readingNumber>=26 && readingNumber<=45){
			//第二阶段 26~45 15~20
				step=3;
				money=Math.ceil(Math.random()*5)+15;
				if(readingNumber==45){
					isCheck=true;
				}
			}*/
			
		}else{
		//第一阶段 5~15 5~15
		if(readingNumber>=5 && readingNumber<=15){
			step=1;
			money=Math.ceil(Math.random()*10)+5;
			if(readingNumber==15){
				isCheck=true;
			}
		}else if(readingNumber>=20 && readingNumber<=30){
		//第二阶段 20~30 5~15
			step=2;
			money=Math.ceil(Math.random()*10)+5;
			if(readingNumber==30){
				isCheck=true;
			}
		}else if(readingNumber>=35 && readingNumber<=50){
		//第二阶段 35~50 5~15
			step=3;
			money=Math.ceil(Math.random()*10)+5;
			if(readingNumber==50){
				isCheck=true;
			}
		}
		}
		map.put("step", step);
		map.put("money",money);
		map.put("isCheck",isCheck);
		return map;
	}
	/**
	 * 检查是否能获得阅读文章的奖励积分
	 * @param type 类型
	 * @param articleId 文章ID
	 * @param acountId 推广人ID
	 * @return b true/false
	 */
	public Map<String,Object> checkReadingArticleMoney(
			String type,
			Integer articleId,
			Integer acountId,
			Integer readingNumber
			){
			Map<String, Object> map = getReadingArticleMap(type,articleId,acountId,readingNumber);	
			Integer step=(Integer) map.get("step");
			Boolean isCheck=(Boolean) map.get("isCheck");
			Double money=(Double) map.get("money");
			if(step==-1){//推广
				return map;
			}
			//dailyDataService.browsePagingDailyData(new Date(), articleId, acountId, 1, Integer.MAX_VALUE, "daily_data_id", "asc");
			BoundSetOperations<String, String> bsoadataturn= stringRedisTemplate.boundSetOps(projectName+"AcountId"+acountId+"Data"+DateUtil.getImgDir()+"Turn");
			BoundSetOperations<String, String> bsoaadataturn = stringRedisTemplate.boundSetOps(projectName+"AcountId"+acountId+"ArtiticlId"+articleId+"Data"+DateUtil.getImgDir()+"Turn");
			bsoaadataturn.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);
		            //相同文章
		            if(bsoadataturn.isMember(articleId.toString())){//1100 ==1100
		            	//普通
		            	if(step>0){
		            		if(isCheck){//需要检查当前阶段是否有奖励，没有奖励的话，需要补充
			            		//最后一个还没，则白分百成功
			            		if(!bsoaadataturn.isMember(step.toString())){
			            			bsoaadataturn.add(step.toString());
			            			return map;
			            		}
		            		}
		            		if(Math.random()>0.5){
		            			//新进的转发文章奖励
		            		if(!bsoaadataturn.isMember(step.toString())){
		            			bsoaadataturn.add(step.toString());
		            			return map;
		            		}
		            		}
		            	}		            			
		            }
		        map.put("step", 0);
		        map.put("money", 0.0);
		return map;
	}
	public static void main(String[] args) {
		ArticleBusiness ab=new ArticleBusiness();
		String type="推广3";
		Integer articleId=1000;
		Integer acountId=100;
		Integer readingNumber=6;	
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
