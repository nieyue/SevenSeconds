package com.nieyue.rabbitmq.confirmcallback;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nieyue.bean.Comment;
import com.nieyue.bean.DailyTask;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.bean.NoviceTask;
import com.nieyue.bean.Reply;

/**
 * 消息生产者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Component 
public class Sender  implements RabbitTemplate.ConfirmCallback{
	 private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);  
	/**
	 * 不能注入，否则没回调
	 */
	 private RabbitTemplate rabbitTemplate;
	 @Resource
	 private AmqpConfig amqpConfig;
	@Autowired  
	    public Sender(RabbitTemplate rabbitTemplate) {  
	        this.rabbitTemplate = rabbitTemplate;  
	        this.rabbitTemplate.setConfirmCallback(this); 
	    } 
	
	/**
	 * 文章点击
	 * @param dataRabbitmqDTO
	 */
	public void sendArticleClick(DataRabbitmqDTO dataRabbitmqDTO) {  
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		this.rabbitTemplate.convertAndSend(amqpConfig.ARTICLECLICK_DIRECT_EXCHANGE, amqpConfig.ARTICLECLICK_DIRECT_ROUTINGKEY, dataRabbitmqDTO, correlationData);  
		
	}
	/**
	 * 文章阅读
	 * @param dataRabbitmqDTO
	 */
	 public void sendArticleRead(DataRabbitmqDTO dataRabbitmqDTO) {  
	        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
	        this.rabbitTemplate.convertAndSend(amqpConfig.ARTICLEREAD_DIRECT_EXCHANGE, amqpConfig.ARTICLEREAD_DIRECT_ROUTINGKEY, dataRabbitmqDTO, correlationData);  
	 }
	 /**
	  * web文章阅读
	  * @param dataRabbitmqDTO
	  */
	 public void sendArticleWebRead(DataRabbitmqDTO dataRabbitmqDTO) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.ARTICLEWEBREAD_DIRECT_EXCHANGE, amqpConfig.ARTICLEWEBREAD_DIRECT_ROUTINGKEY, dataRabbitmqDTO, correlationData);  
	 }
	 /**
	  * 评论
	  * @param dataRabbitmqDTO
	  */
	 public void sendComment(Comment comment) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.COMMENT_DIRECT_EXCHANGE, amqpConfig.COMMENT_DIRECT_ROUTINGKEY, comment, correlationData);  
	 }
	 /**
	  * 回复
	  * @param dataRabbitmqDTO
	  */
	 public void sendReply(Reply reply) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.REPLY_DIRECT_EXCHANGE, amqpConfig.REPLY_DIRECT_ROUTINGKEY, reply, correlationData);  
	 }
	 /**
	  * 新手任务
	  * @param dataRabbitmqDTO
	  */
	 public void sendNoviceTask(NoviceTask noviceTask) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.NOVICETASK_DIRECT_EXCHANGE, amqpConfig.NOVICETASK_DIRECT_ROUTINGKEY, noviceTask, correlationData);  
	 }
	 /**
	  * 日常任务
	  * @param dataRabbitmqDTO
	  */
	 public void sendDailyTask(DailyTask dailyTask) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.DAILYTASK_DIRECT_EXCHANGE, amqpConfig.DAILYTASK_DIRECT_ROUTINGKEY, dailyTask, correlationData);  
	 }
	 
	 /** 回调方法 */
	 @Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
	        if (ack) {
	        	LOGGER.info("消息发送确认成功");
	        } else {
	        	LOGGER.info("消息发送确认失败:" + cause);

	        }  
		
	}

}
