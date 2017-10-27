package com.nieyue.rabbitmq.confirmcallback;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nieyue.bean.Acount;
import com.nieyue.bean.Article;
import com.nieyue.bean.Barrage;
import com.nieyue.bean.Comment;
import com.nieyue.bean.Complain;
import com.nieyue.bean.DailyData;
import com.nieyue.bean.DailyTask;
import com.nieyue.bean.Data;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.bean.Finance;
import com.nieyue.bean.FlowWater;
import com.nieyue.bean.NoviceTask;
import com.nieyue.bean.Reply;
import com.nieyue.bean.Sign;
import com.nieyue.business.DailyTaskBusiness;
import com.nieyue.business.NoviceTaskBusiness;
import com.nieyue.service.AcountService;
import com.nieyue.service.ArticleService;
import com.nieyue.service.BarrageService;
import com.nieyue.service.CommentService;
import com.nieyue.service.ComplainService;
import com.nieyue.service.DailyDataService;
import com.nieyue.service.DailyTaskService;
import com.nieyue.service.DataService;
import com.nieyue.service.FinanceService;
import com.nieyue.service.FlowWaterService;
import com.nieyue.service.NoviceTaskService;
import com.nieyue.service.ReplyService;
import com.nieyue.service.SignService;
import com.nieyue.util.DateUtil;
import com.rabbitmq.client.Channel;

/**
 * 消息监听者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Configuration  
public class Listener {
	@Resource
	private ArticleService articleService;
	@Resource
	private FinanceService financeService;
	@Resource
	private DataService dataService;
	@Resource
	private DailyDataService dailyDataService;
	@Resource
	private AcountService acountService;
	@Resource
	private FlowWaterService flowWaterService;
	@Resource
	private SignService signService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private DailyTaskBusiness dailyTaskBusiness;
	@Resource
	private NoviceTaskBusiness noviceTaskBusiness;
	@Resource
	private NoviceTaskService noviceTaskService;
	@Resource
	private DailyTaskService dailyTaskService;
	@Resource
	private CommentService commentService;
	@Resource
	private ReplyService replyService;
	@Resource
	private BarrageService barrageService;
	@Resource
	private ComplainService complainService;
	@Value("${myPugin.projectName}")
	String projectName;
	//private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
	/**
	 * 文章点击
	 * @param channel
	 * @param dataRabbitmqDTO
	 * @param message
	 * @throws IOException
	 */
    @RabbitListener(queues="${myPugin.rabbitmq.ARTICLECLICK_DIRECT_QUEUE}") 
    public void articleClick(Channel channel, DataRabbitmqDTO dataRabbitmqDTO,Message message) throws IOException   {
           try {
        	  /**
        	   * 判断是否存在
        	   */
        	//如果文章不予统计  
       		Article inarticle = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
       		if(inarticle==null||inarticle.equals("")){
       		 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
       		 return;
       		}
       		//如果账户不存在则用，1000
       		Acount inacount = acountService.loadAcount(dataRabbitmqDTO.getAcountId());
       		if(inacount==null||inacount.equals("")){
       			inacount = acountService.loadAcount(1000);
       		}
        	   /**
        	    * 统计data
        	    */
        	   //统计当日当前文章文章每日ip(总统计，做区别ip,保证不同acountId同一IP)
        	   BoundSetOperations<String, String> bsodatips = stringRedisTemplate.boundSetOps(projectName+"ArticleId"+dataRabbitmqDTO.getArticleId()+"Data"+DateUtil.getImgDir()+"Ips");
        	   int isAddIp=0;//默认没增加
        	   int oldIPSize = bsodatips.members().size();
        	   bsodatips.add(dataRabbitmqDTO.getIp());//总ip
        	   int nowIPSize = bsodatips.members().size();
        	   if(nowIPSize>oldIPSize){
        		   isAddIp=1;//增加了
        	   }
        	   //统计当日当前人的当前文章每日ip
        	   BoundSetOperations<String, String> bsodataips = stringRedisTemplate.boundSetOps(projectName+"AcountId"+dataRabbitmqDTO.getAcountId()+"ArtiticlId"+dataRabbitmqDTO.getArticleId()+"Data"+DateUtil.getImgDir()+"Ips");
		        	   if(isAddIp==1){
		        	   bsodataips.add(dataRabbitmqDTO.getIp());//ip存入redis数据库
		        	   //bsodataips.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);//按天计算有用
		        	   }
		        	   //三段时间数据
        	   			Data realdata=new Data();
        	   			//时间是3段:0-8,8-16,16-24
        	   			realdata.setCreateDate(DateUtil.getDayPeriod(3));
        	   			realdata.setArticleId(dataRabbitmqDTO.getArticleId());
        	   			realdata.setAcountId(dataRabbitmqDTO.getAcountId());
        	   			dataService.saveOrUpdateData(realdata,dataRabbitmqDTO.getUv(), isAddIp,dataRabbitmqDTO.getReadingNumber());
        	   			
        	   			//日数据
        	   			DailyData realdailydata=new DailyData();
        	   			//时间是日
        	   			realdailydata.setCreateDate(DateUtil.getStartTime());
        	   			realdailydata.setArticleId(dataRabbitmqDTO.getArticleId());
        	   			realdailydata.setAcountId(dataRabbitmqDTO.getAcountId());
        	   			dailyDataService.saveOrUpdateDailyData(realdailydata, dataRabbitmqDTO.getUv(), isAddIp, dataRabbitmqDTO.getReadingNumber());
        	   			
        	  /**
        	   * 更新文章
        	   */
        	   //当前文章
        	   Article article = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
       		//如果消耗完则返回
       		if(article.getNowTotalPrice()>=article.getTotalPrice()){
       			article.setStatus("完成");
       			articleService.updateArticleClick(article);
       			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        		return;
       		}
        	   //任务完成，只收集数据 完成不长阅读和金额
        	   if(!article.getStatus().equals("正常")||article.getStatus().equals("完成")){
        		   articleService.updateArticleClick(article);//更新文章数据
	        	   channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        		   return;
        	   }
        	   article.setPvs(article.getPvs()+1);
        	   article.setUvs(article.getUvs()+dataRabbitmqDTO.getUv());
        	   article.setIps(article.getIps()+isAddIp);
        	   if(article.getModel()==4){
        		   //千次展示计费
        		  article.setTotalPrice(Double.valueOf(article.getPvs()/1000)*article.getUnitPrice());
        	   }else if(article.getModel()==3){
        		   //uv计费
        		   article.setTotalPrice(Double.valueOf(article.getUvs())*article.getUnitPrice());
        	   }else if(article.getModel()==2){
        		   //ip计费
        		   article.setTotalPrice(Double.valueOf(article.getIps())*article.getUnitPrice());
        	   }
        	   article.setContent(null);
        	   articleService.updateArticleClick(article);//更新文章数据
        	  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			 try {
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			} catch (IOException e1) {
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
				
				e1.printStackTrace();
			}
			//e.printStackTrace();
		} //确认消息成功消费 
    } 
    
    /**
     * 文章阅读
     * @param channel
     * @param dataRabbitmqDTO
     * @param message
     * @throws IOException
     */
	    @RabbitListener(queues="${myPugin.rabbitmq.ARTICLEREAD_DIRECT_QUEUE}") 
	    public void articleRead(Channel channel, DataRabbitmqDTO dataRabbitmqDTO,Message message) throws IOException   {
	           try {
	        	  /**
	        	   * 判断是否存在
	        	   */
	        	 //如果文章不予统计  
	       		Article inarticle = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
	       		if(inarticle==null||inarticle.equals("")){
	       		 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	       		 return;
	       		}
	       		//如果账户不存在则用，1000
	       		Acount inacount = acountService.loadAcount(dataRabbitmqDTO.getAcountId());
	       		if(inacount==null||inacount.equals("")){
	       			inacount = acountService.loadAcount(1000);
	       		}
	       		//如果账号被锁定，不统计
	       		if(inacount.getStatus().equals(1)){
	       			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	       			return;
	       		}
	       		//7秒一次,acountId=1000,不需限制
	       		if(dataRabbitmqDTO.getAcountId()!=1000){
	     	   BoundValueOperations<String, String> bvotworequest = stringRedisTemplate.boundValueOps(projectName+"AcountId"+dataRabbitmqDTO.getAcountId()+"DataSevenSecondsReuqest");
	     	  if(bvotworequest.size()>0){
	     		 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	       		 return;
	     	  }else{
	     		  bvotworequest.set("1", 7, TimeUnit.SECONDS);
	     	  }
	       		}
	     	  
	     	  
	     	   		/**
	        	    * 统计data
	        	    */
	     	  	int isAddIp=0;
	     	  	//三段时间数据
	   			Data realdata=new Data();
	   			//时间是3段:0-8,8-16,16-24
	   			realdata.setCreateDate(DateUtil.getDayPeriod(3));
	   			realdata.setArticleId(dataRabbitmqDTO.getArticleId());
	   			realdata.setAcountId(dataRabbitmqDTO.getAcountId());
	   			dataService.saveOrUpdateData(realdata,dataRabbitmqDTO.getUv(), isAddIp,dataRabbitmqDTO.getReadingNumber());
	   			
	   			//日数据
	   			DailyData realdailydata=new DailyData();
	   			//时间是日
	   			realdailydata.setCreateDate(DateUtil.getStartTime());
	   			realdailydata.setArticleId(dataRabbitmqDTO.getArticleId());
	   			realdailydata.setAcountId(dataRabbitmqDTO.getAcountId());
	   			dailyDataService.saveOrUpdateDailyData(realdailydata, dataRabbitmqDTO.getUv(), isAddIp, dataRabbitmqDTO.getReadingNumber());
	        	  /**
	        	   * 更新文章
	        	   */
	        	   //当前文章
	        	   Article article = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
	       		//如果消耗完则返回
	       		if(article.getNowTotalPrice()>=article.getTotalPrice()){
	       			article.setStatus("完成");
	       			articleService.updateArticleClick(article);
	       			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	        		return;
	       		}
	        	   //任务完成，只收集数据 完成不长阅读和金额
	        	   if(!article.getStatus().equals("正常")||article.getStatus().equals("完成")){
	        		   articleService.updateArticleClick(article);//更新文章数据
		        	   channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	        		   return;
	        	   }
	        	   article.setContent(null);
	        	   article.setReadingNumber(article.getReadingNumber()+1);
	        	   article.setUserNowTotalPrice(article.getReadingNumber()*article.getUserUnitPrice());
	        	   if(article.getModel()==1){
	        		   //阅读计费
	        		   article.setNowTotalPrice(article.getReadingNumber()*article.getUnitPrice());
	        		   
	        	   }
	        	   articleService.updateArticleClick(article);//更新文章数据
	        	   /**
	        	    * 自身财务、收益
	        	    */
	        	   //自身财务
	        	   List<Finance> financelist = financeService.browsePagingFinance(dataRabbitmqDTO.getAcountId(), 1, 1, "finance_id", "asc");
	        	   Finance selfFinance = financelist.get(0);
	        	  
	        	   //记录流水，阅读文章收益
	        	   FlowWater flowWater = new FlowWater();
	        	   flowWater.setAcountId(dataRabbitmqDTO.getAcountId());
	        	   flowWater.setCreateDate(new Date());
	        	   flowWater.setMoney(article.getUserUnitPrice());
	        	   flowWater.setRealMoney(0.0);
	        	   flowWater.setType(4);//4文章阅读
	        	   flowWater.setSubtype(1);
	        	   flowWaterService.addFlowWater(flowWater);
	        	   //自身总收益增加
	        	   selfFinance.setSelfProfit(selfFinance.getSelfProfit()+article.getUserUnitPrice());
	        	   //余额=增加
	        	   selfFinance.setMoney(selfFinance.getMoney()+article.getUserUnitPrice());
	        	   financeService.updateFinance(selfFinance);
	        	   
	        	   /**
	        	    * 上级账户收益、财务
	        	    */
	        	  Integer masterID = inacount.getMasterId();//获取父账户ID
	        	  if(masterID!=null&&!masterID.equals("")){ 
	        		  Acount masterAcount = acountService.loadAcount(masterID);
	        		  if(masterAcount!=null&&!masterAcount.equals("")&&masterAcount.getScale()>0.0){
	        			  //父账户财务
	        			  List<Finance> masterfinancelist = financeService.browsePagingFinance(masterAcount.getAcountId(), 1, 1, "finance_id", "asc");
	        			 if(masterfinancelist.size()>0){//有父账户
	        			Finance masterFinance = masterfinancelist.get(0);
	        			 //记录父账户流水，阅读文章收益
	    	        	   FlowWater parentFlowWater = new FlowWater();
	    	        	   parentFlowWater.setAcountId(masterID);
	    	        	   parentFlowWater.setCreateDate(new Date());
	    	        	   BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ScaleIncrement");//合伙人增量
		      	           double masterUserUnitPrice = article.getUserUnitPrice()*(masterAcount.getScale()+Double.valueOf(bvo.get()));//合伙人比例收益=合伙人单价*（合伙人收益比例+全局收益比例）
		      	           parentFlowWater.setMoney(masterUserUnitPrice);
		      	           parentFlowWater.setRealMoney(0.0);
		      	           parentFlowWater.setType(5);//5徒弟进贡
		      	           parentFlowWater.setSubtype(2);//文章阅读
	    	        	   flowWaterService.addFlowWater(parentFlowWater);
	    	        	   //合伙人总收益增加
	    	        	   masterFinance.setPartnerProfit(masterFinance.getPartnerProfit()+masterUserUnitPrice);
	    	        	   //余额=增加
	    	        	   masterFinance.setMoney(masterFinance.getMoney()+masterUserUnitPrice);
	    	        	   financeService.updateFinance(masterFinance);
	        			 }
	        		  }
	        	  }
	        	   
	        	  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 try {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
				} catch (IOException e1) {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
					
					e1.printStackTrace();
				}
				//e.printStackTrace();
			} //确认消息成功消费 
	    }  
	    
	    /**
	     * web文章阅读 ip==阅读
	     * @param channel
	     * @param dataRabbitmqDTO
	     * @param message
	     * @throws IOException
	     */
		    @RabbitListener(queues="${myPugin.rabbitmq.ARTICLEWEBREAD_DIRECT_QUEUE}") 
		    public void articleWebRead(Channel channel, DataRabbitmqDTO dataRabbitmqDTO,Message message) throws IOException   {
		           try {
		        	  /**
		        	   * 判断是否存在
		        	   */
		        	 //如果文章不予统计  
		       		Article inarticle = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
		       		if(inarticle==null||inarticle.equals("")){
		       		 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		       		 return;
		       		}
		       		//如果账户不存在则用，1000
		       		Acount tginacount = acountService.loadAcount(dataRabbitmqDTO.getAcountId());//推广账户
		       		Acount inacount = acountService.loadAcount(1000);//平台账户计费
		       		if(tginacount==null||tginacount.equals("")){
		       			tginacount = acountService.loadAcount(1000);
		       		}
		     	   		/**
		        	    * 统计data
		        	    */
		       			//统计当日当前文章文章每日ip(总统计，做区别ip,保证不同acountId同一IP)
		        	   BoundSetOperations<String, String> bsodatips = stringRedisTemplate.boundSetOps(projectName+"ArticleId"+dataRabbitmqDTO.getArticleId()+"Data"+DateUtil.getImgDir()+"Ips");
		        	   int isAddIp=0;//默认没增加
		        	   int oldIPSize = bsodatips.members().size();
		        	   bsodatips.add(dataRabbitmqDTO.getIp());//总ip
		        	   int nowIPSize = bsodatips.members().size();
		        	   if(nowIPSize>oldIPSize){
		        		   isAddIp=1;//增加了
		        	   }
		        	   //统计当日当前人的当前文章每日ip
		        	   BoundSetOperations<String, String> bsodataips = stringRedisTemplate.boundSetOps(projectName+"AcountId"+inacount.getAcountId()+"ArtiticlId"+dataRabbitmqDTO.getArticleId()+"Data"+DateUtil.getImgDir()+"Ips");
				        	   if(isAddIp==1){
				        	   bsodataips.add(dataRabbitmqDTO.getIp());//ip存入redis数据库
				        	   //bsodataips.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);//按天计算有用
				        	   }
		     	  	//三段时间数据
		   			Data realdata=new Data();
		   			//时间是3段:0-8,8-16,16-24
		   			realdata.setCreateDate(DateUtil.getDayPeriod(3));
		   			realdata.setArticleId(dataRabbitmqDTO.getArticleId());
		   			realdata.setAcountId(inacount.getAcountId());
		   			 dataService.saveOrUpdateData(realdata,dataRabbitmqDTO.getUv(), isAddIp,isAddIp);
		   			//日数据
		   			DailyData realdailydata=new DailyData();
		   			//时间是日
		   			realdailydata.setCreateDate(DateUtil.getStartTime());
		   			realdailydata.setArticleId(dataRabbitmqDTO.getArticleId());
		   			realdailydata.setAcountId(inacount.getAcountId());
		   			dailyDataService.saveOrUpdateDailyData(realdailydata, dataRabbitmqDTO.getUv(), isAddIp, isAddIp);
		        	  /**
		        	   * 更新文章
		        	   */
		        	   //当前文章
		        	   Article article = articleService.loadSmallArticle(dataRabbitmqDTO.getArticleId());
		       		//如果消耗完则返回
		       		if(article.getNowTotalPrice()>=article.getTotalPrice()){
		       			article.setStatus("完成");
		       			articleService.updateArticleClick(article);
		       			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		        		return;
		       		}
		        	   //任务完成，只收集数据 完成不长阅读和金额
		        	   if(!article.getStatus().equals("正常")||article.getStatus().equals("完成")){
		        		   articleService.updateArticleClick(article);//更新文章数据
			        	   channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		        		   return;
		        	   }
		        	   article.setContent(null);
		        	   article.setReadingNumber(article.getReadingNumber()+isAddIp);
		        	   article.setUserNowTotalPrice(article.getReadingNumber()*article.getUserUnitPrice());
		        	   article.setPvs(article.getPvs()+1);
		        	   article.setUvs(article.getUvs()+dataRabbitmqDTO.getUv());
		        	   article.setIps(article.getIps()+isAddIp);
		        	   if(article.getModel()==4){
		        		   //千次展示计费
		        		  article.setTotalPrice(Double.valueOf(article.getPvs()/1000)*article.getUnitPrice());
		        	   }else if(article.getModel()==3){
		        		   //uv计费
		        		   article.setTotalPrice(Double.valueOf(article.getUvs())*article.getUnitPrice());
		        	   }else if(article.getModel()==2){
		        		   //ip计费
		        		   article.setTotalPrice(Double.valueOf(article.getIps())*article.getUnitPrice());
		        	   }else if(article.getModel()==1){
		        		   //阅读计费
		        		   article.setNowTotalPrice(article.getReadingNumber()*article.getUnitPrice());
		        		   
		        	   }
		        	   articleService.updateArticleClick(article);//更新文章数据
		        	   /**
		        	    * 自身财务、收益
		        	    */
		        	   
		        	   
		        	   //推广账户  达人奖励 转发推广文章    10积分（获得3个有效阅读）
		        	   List<Finance> tgfinancelist = financeService.browsePagingFinance(dataRabbitmqDTO.getAcountId(), 1, 1, "finance_id", "asc");
		        	   Finance tgFinance = tgfinancelist.get(0);
		        	 //推广账户 的当前文章阅读次数
		        	   BoundValueOperations<String, String> tgbvo = stringRedisTemplate.boundValueOps(projectName+"AcountId"+dataRabbitmqDTO.getAcountId()+"ArtiticlId"+dataRabbitmqDTO.getArticleId()+"PromotionReading");
		        		  // List<FlowWater> tgfl = flowWaterService.browsePagingFlowWater(dataRabbitmqDTO.getAcountId(), 6, 1, null, 1, Integer.MAX_VALUE, "flow_water_id", "asc");
			        	   if(tgbvo.size()<=0){
			        		   if(isAddIp==1){
			        		   tgbvo.set("1");
			        		   }
			        		   //没有计算过。
			        		   financeService.updateFinance(tgFinance); 
			        	   }else if(Integer.valueOf(tgbvo.get())<2){
			        		   if(isAddIp==1){
			        		   tgbvo.set(Integer.valueOf(tgbvo.get())+1+"");
			        		   }
			        	   }else if(Integer.valueOf(tgbvo.get())==2){
			        		   if(isAddIp==1){
			        		   tgbvo.set(Integer.valueOf(tgbvo.get())+1+"");//保证至此一次
			        		   //记录流水，阅读文章收益
			        		   FlowWater tgflowWater = new FlowWater();
			        		   tgflowWater.setAcountId(dataRabbitmqDTO.getAcountId());
			        		   tgflowWater.setCreateDate(new Date());
			        		   tgflowWater.setMoney(dailyTaskBusiness.forwardingPromotionArticle(Integer.valueOf(tgbvo.get())));
			        		   tgflowWater.setRealMoney(0.0);
			        		   tgflowWater.setType(3);//3达人奖励
			        		   tgflowWater.setSubtype(7);//转发推广文章    10积分（获得3个有效阅读）
			        		   flowWaterService.addFlowWater(tgflowWater);
			        		   //自身总收益增加
			        		   tgFinance.setSelfProfit(tgFinance.getSelfProfit()+dailyTaskBusiness.forwardingPromotionArticle(Integer.valueOf(tgbvo.get())));
			        		   //余额=增加
			        		   tgFinance.setMoney(tgFinance.getMoney()+dailyTaskBusiness.forwardingPromotionArticle(Integer.valueOf(tgbvo.get())));
			        		   financeService.updateFinance(tgFinance);
			        		   }
			        	   }
			        	   
		        	   //自身财务 有ip才计费
		        	   if(isAddIp==1){
			        	List<Finance> financelist = financeService.browsePagingFinance(inacount.getAcountId(), 1, 1, "finance_id", "asc");
			        	Finance selfFinance = financelist.get(0);
		        	   //记录流水，阅读文章收益
		        	   FlowWater flowWater = new FlowWater();
		        	   flowWater.setAcountId(inacount.getAcountId());
		        	   flowWater.setCreateDate(new Date());
		        	   flowWater.setMoney(article.getUserUnitPrice());
		        	   flowWater.setRealMoney(0.0);
		        	   flowWater.setType(4);//4文章阅读
		        	   flowWater.setSubtype(1);
		        	   flowWaterService.addFlowWater(flowWater);
		        	   //自身总收益增加
		        	   selfFinance.setSelfProfit(selfFinance.getSelfProfit()+article.getUserUnitPrice());
		        	   //余额=增加
		        	   selfFinance.setMoney(selfFinance.getMoney()+article.getUserUnitPrice());
		        	   financeService.updateFinance(selfFinance);
		        	   
		        	   /**
		        	    * 上级账户收益、财务
		        	    */
		        	  Integer masterID = inacount.getMasterId();//获取父账户ID
		        	  if(masterID!=null&&!masterID.equals("")){ 
		        		  Acount masterAcount = acountService.loadAcount(masterID);
		        		  if(masterAcount!=null&&!masterAcount.equals("")&&masterAcount.getScale()>0.0){
		        			  //父账户财务
		        			  List<Finance> masterfinancelist = financeService.browsePagingFinance(masterAcount.getAcountId(), 1, 1, "finance_id", "asc");
		        			 if(masterfinancelist.size()>0){//有父账户
		        			Finance masterFinance = masterfinancelist.get(0);
		        			 //记录父账户流水，阅读文章收益
		    	        	   FlowWater parentFlowWater = new FlowWater();
		    	        	   parentFlowWater.setAcountId(masterID);
		    	        	   parentFlowWater.setCreateDate(new Date());
		    	        	   BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ScaleIncrement");//合伙人增量
			      	           double masterUserUnitPrice = article.getUserUnitPrice()*(masterAcount.getScale()+Double.valueOf(bvo.get()));//合伙人比例收益=合伙人单价*（合伙人收益比例+全局收益比例）
			      	           parentFlowWater.setMoney(masterUserUnitPrice);
			      	           parentFlowWater.setRealMoney(0.0);
			      	           parentFlowWater.setType(5);//5徒弟进贡
			      	           parentFlowWater.setSubtype(2);//文章阅读
		    	        	   flowWaterService.addFlowWater(parentFlowWater);
		    	        	   //合伙人总收益增加
		    	        	   masterFinance.setPartnerProfit(masterFinance.getPartnerProfit()+masterUserUnitPrice);
		    	        	   //余额=增加
		    	        	   masterFinance.setMoney(masterFinance.getMoney()+masterUserUnitPrice);
		    	        	   financeService.updateFinance(masterFinance);
		        			 }
		        		  }
		        	  }
		        	}
		        	   
		        	  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 try {
						channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
					} catch (IOException e1) {
						channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
						
						e1.printStackTrace();
					}
					//e.printStackTrace();
				} //确认消息成功消费 
		    }  
		    
		    /**
		     * 评论
		     * @param channel
		     * @param dataRabbitmqDTO
		     * @param message
		     * @throws IOException
		     */
			    @RabbitListener(queues="${myPugin.rabbitmq.COMMENT_DIRECT_QUEUE}") 
			    public void comment(Channel channel, Comment comment,Message message) throws IOException   {
			    	 try {
			    	 commentService.addComment(comment);
			    	channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	 } catch (Exception e) {
							// TODO Auto-generated catch block
							 try {
								channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
							} catch (IOException e1) {
								channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
								
								e1.printStackTrace();
							}
							//e.printStackTrace();
						} //确认消息成功消费 
			    }
			    
			    
			    /**
			     * 回复
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.REPLY_DIRECT_QUEUE}") 
			    public void reply(Channel channel, Reply reply,Message message) throws IOException   {
			    	try {
			    		replyService.addReply(reply);
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    
			    /**
			     * 弹幕
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.BARRAGE_DIRECT_QUEUE}") 
			    public void barrage(Channel channel, Barrage barrage,Message message) throws IOException   {
			    	try {
			    		barrageService.addBarrage(barrage);
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    
			    /**
			     * 投诉
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.COMPLAIN_DIRECT_QUEUE}") 
			    public void complain(Channel channel, Complain complain,Message message) throws IOException   {
			    	try {
			    		complainService.addComplain(complain);
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    
			    
			    /**
			     * 签到
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.SIGN_DIRECT_QUEUE}") 
			    public void sign(Channel channel, Sign sign,Message message) throws IOException   {
			    	try {
			    		boolean b = signService.addSign(sign);
			    		if(b){
			    			List<Finance> financelist = financeService.browsePagingFinance(sign.getAcountId(), 1, 1, "finance_id", "asc");
			    			Finance selfFinance = financelist.get(0);
			    			//记录流水，新手任务收益
			    			FlowWater flowWater = new FlowWater();
			    			flowWater.setAcountId(sign.getAcountId());
			    			flowWater.setCreateDate(new Date());
			    			flowWater.setMoney(sign.getLevel()*2.0);
			    			flowWater.setRealMoney(0.0);
			    			flowWater.setType(7);//7，签到
			    			flowWater.setSubtype(1);
			    			flowWaterService.addFlowWater(flowWater);
			    			//自身总收益增加
			    			selfFinance.setSelfProfit(selfFinance.getSelfProfit()+sign.getLevel()*2.0);
			    			//余额=增加
			    			selfFinance.setMoney(selfFinance.getMoney()+sign.getLevel()*2.0);
			    			financeService.updateFinance(selfFinance);
			    			
			    		}
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    
			    /**
			     * 新手任务
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.NOVICETASK_DIRECT_QUEUE}") 
			    public void noviceTask(Channel channel, NoviceTask noviceTask,Message message) throws IOException   {
			    	try {
			    		boolean b = noviceTaskService.addNoviceTask(noviceTask);
			    	if(b){
			    		List<Finance> financelist = financeService.browsePagingFinance(noviceTask.getAcountId(), 1, 1, "finance_id", "asc");
			        	Finance selfFinance = financelist.get(0);
		        	   //记录流水，新手任务收益
		        	   FlowWater flowWater = new FlowWater();
		        	   flowWater.setAcountId(noviceTask.getAcountId());
		        	   flowWater.setCreateDate(new Date());
		        	   flowWater.setMoney(noviceTaskBusiness.disposableTrigger(noviceTask.getFrequency()));
		        	   flowWater.setRealMoney(0.0);
		        	   flowWater.setType(1);//1新手任务
		        	   flowWater.setSubtype(noviceTask.getFrequency());
		        	   flowWaterService.addFlowWater(flowWater);
		        	   //自身总收益增加
		        	   selfFinance.setSelfProfit(selfFinance.getSelfProfit()+noviceTaskBusiness.disposableTrigger(noviceTask.getFrequency()));
		        	   //余额=增加
		        	   selfFinance.setMoney(selfFinance.getMoney()+noviceTaskBusiness.disposableTrigger(noviceTask.getFrequency()));
		        	   financeService.updateFinance(selfFinance);
		        	   
		        	   //如果为新手任务0收徒 或者非新手任务，则没有达人奖励。
		        	   if(noviceTask.getFrequency()<=0||noviceTask.getFrequency()>5){
		        		   channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		        		   return;
		        	   }
		        	   //达人奖励,师傅收益
		        	  Acount acount = acountService.loadAcount(noviceTask.getAcountId());
		        	  if(acount.getMasterId()!=null && !acount.getMasterId().equals("")){
		        		  List<Finance> masterfinancelist = financeService.browsePagingFinance(acount.getMasterId(), 1, 1, "finance_id", "asc");
				        	if(masterfinancelist.size()>0){
		        		  Finance masterFinance = masterfinancelist.get(0);
		        		  //师傅记录流水，新手任务收益
			        	   FlowWater daflowWater = new FlowWater();
			        	   daflowWater.setAcountId(acount.getMasterId());
			        	   daflowWater.setCreateDate(new Date());
			        	   daflowWater.setMoney(dailyTaskBusiness.apprenticeNoviceTask(noviceTask.getFrequency()));//达人奖励，回馈师傅积分
			        	   daflowWater.setRealMoney(0.0);
			        	   daflowWater.setType(3);//3达人奖励
			        	   daflowWater.setSubtype(noviceTask.getFrequency());
			        	   flowWaterService.addFlowWater(daflowWater);
			        	 //师傅自身收益增加
			        	   masterFinance.setSelfProfit((masterFinance.getSelfProfit()+dailyTaskBusiness.apprenticeNoviceTask(noviceTask.getFrequency())));
			        	   //师傅余额=增加
			        	   masterFinance.setMoney(masterFinance.getMoney()+dailyTaskBusiness.apprenticeNoviceTask(noviceTask.getFrequency()));
			        	   financeService.updateFinance(masterFinance);
				        	}
			        	   
		        	  }
		        	   
			    	}
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    
			    
			    /**
			     * 日常任务
			     * @param channel
			     * @param dataRabbitmqDTO
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.DAILYTASK_DIRECT_QUEUE}") 
			    public void dailyTask(Channel channel, DailyTask dailyTask,Message message) throws IOException   {
			    	try {
			    		boolean b = dailyTaskService.addDailyTask(dailyTask);
			    		if(b){
			    			
			    			List<Finance> financelist = financeService.browsePagingFinance(dailyTask.getAcountId(), 1, 1, "finance_id", "asc");
			    			Finance selfFinance = financelist.get(0);
			    			//记录流水，新手任务收益
			    			FlowWater flowWater = new FlowWater();
			    			flowWater.setAcountId(dailyTask.getAcountId());
			    			flowWater.setCreateDate(new Date());
			    			flowWater.setMoney(dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency()));
			    			flowWater.setRealMoney(0.0);
			    			flowWater.setType(2);//2日常任务
			    			flowWater.setSubtype(dailyTask.getType());
			    			flowWaterService.addFlowWater(flowWater);
			    			//自身总收益增加
			    			selfFinance.setSelfProfit(selfFinance.getSelfProfit()+dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency()));
			    			//余额=增加
			    			selfFinance.setMoney(selfFinance.getMoney()+dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency()));
			    			financeService.updateFinance(selfFinance);
			    			
			    			//师傅每日任务收益分成
			    			Acount acount = acountService.loadAcount(dailyTask.getAcountId());
			    			if(acount.getMasterId()!=null && !acount.getMasterId().equals("")){
			    				List<Finance> masterfinancelist = financeService.browsePagingFinance(acount.getMasterId(), 1, 1, "finance_id", "asc");
			    				if(masterfinancelist.size()>0){
			    					Finance masterFinance = masterfinancelist.get(0);
			    					//师傅记录流水，新手任务收益
			    					FlowWater daflowWater = new FlowWater();
			    					daflowWater.setAcountId(acount.getMasterId());
			    					daflowWater.setCreateDate(new Date());
			    					Acount masterAcount=acountService.loadAcount(acount.getMasterId());
				    	        	   BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ScaleIncrement");//合伙人增量
					      	           double finalscale = masterAcount.getScale()+Double.valueOf(bvo.get());//合伙人比例收益=合伙人单价*（合伙人收益比例+全局收益比例）
			    					daflowWater.setMoney(dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency())*finalscale);
			    					daflowWater.setRealMoney(0.0);
			    					daflowWater.setType(5);//5，徒弟进贡
			    					daflowWater.setSubtype(1);//日常任务
			    					flowWaterService.addFlowWater(daflowWater);
			    					//师傅合伙人收益增加
			    					masterFinance.setPartnerProfit(masterFinance.getPartnerProfit()+dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency())*finalscale);
			    					//师傅余额=增加
			    					masterFinance.setMoney(masterFinance.getMoney()+dailyTaskBusiness.dailyTrigger(dailyTask.getType(), dailyTask.getFrequency())*finalscale);
			    					System.err.println(masterFinance);
			    					financeService.updateFinance(masterFinance);
			    				}
			    				
			    			}
			    			
			    		}
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    /**
			     * 商品订单流水
			     * @param channel
			     * @param flowWater
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.MERORDERFLOWWATER_DIRECT_QUEUE}") 
			    public void merOrderFlowWater(Channel channel, FlowWater flowWater,Message message) throws IOException   {
			    	try {
			    		boolean b = flowWaterService.addFlowWater(flowWater);
			    		if(b){
			    			List<Finance> financelist = financeService.browsePagingFinance(flowWater.getAcountId(), 1, 1, "finance_id", "asc");
			    			Finance selfFinance = financelist.get(0);
			    			//自身消费增加 ,money为负数，所以减
			    			selfFinance.setConsume(selfFinance.getConsume()-flowWater.getMoney());
			    			//余额 减少，money为负数，所以加
			    			selfFinance.setMoney(selfFinance.getMoney()+flowWater.getMoney());
			    			financeService.updateFinance(selfFinance);
			    		}
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
			    /**
			     * 书订单流水
			     * @param channel
			     * @param flowWater
			     * @param message
			     * @throws IOException
			     */
			    @RabbitListener(queues="${myPugin.rabbitmq.BOOKORDERFLOWWATER_DIRECT_QUEUE}") 
			    public void bookOrderFlowWater(Channel channel, FlowWater flowWater,Message message) throws IOException   {
			    	try {
			    		boolean b = flowWaterService.addFlowWater(flowWater);
			    		if(b){
			    			List<Finance> financelist = financeService.browsePagingFinance(flowWater.getAcountId(), 1, 1, "finance_id", "asc");
			    			Finance selfFinance = financelist.get(0);
			    			//自身消费增加 ,money为负数，所以减
			    			selfFinance.setConsume(selfFinance.getConsume()-flowWater.getMoney());
			    			//余额 减少，money为负数，所以加
			    			selfFinance.setMoney(selfFinance.getMoney()+flowWater.getMoney());
			    			financeService.updateFinance(selfFinance);
			    		}
			    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			    	} catch (Exception e) {
			    		// TODO Auto-generated catch block
			    		try {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    		} catch (IOException e1) {
			    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
			    			
			    			e1.printStackTrace();
			    		}
			    		//e.printStackTrace();
			    	} //确认消息成功消费 
			    }
}
