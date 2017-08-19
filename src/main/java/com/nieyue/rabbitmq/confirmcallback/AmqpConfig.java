package com.nieyue.rabbitmq.confirmcallback;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
/**
 * 配置
 * @author 聂跃
 * @date 2017年5月31日
 */
@Configuration  
public class AmqpConfig {
	/**
	 * 文章点击
	 */
	@Value("${myPugin.rabbitmq.ARTICLECLICK_DIRECT_EXCHANGE}")
    public  String ARTICLECLICK_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.ARTICLECLICK_DIRECT_ROUTINGKEY}")
    public String ARTICLECLICK_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.ARTICLECLICK_DIRECT_QUEUE}")
    public  String ARTICLECLICK_DIRECT_QUEUE; 
	/**
	 * 文章阅读
	 */
	@Value("${myPugin.rabbitmq.ARTICLEREAD_DIRECT_EXCHANGE}")
	public  String ARTICLEREAD_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.ARTICLEREAD_DIRECT_ROUTINGKEY}")
	public String ARTICLEREAD_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.ARTICLEREAD_DIRECT_QUEUE}")
	public  String ARTICLEREAD_DIRECT_QUEUE; 
	/**
	 * web文章阅读
	 */
	@Value("${myPugin.rabbitmq.ARTICLEWEBREAD_DIRECT_EXCHANGE}")
	public  String ARTICLEWEBREAD_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.ARTICLEWEBREAD_DIRECT_ROUTINGKEY}")
	public String ARTICLEWEBREAD_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.ARTICLEWEBREAD_DIRECT_QUEUE}")
	public  String ARTICLEWEBREAD_DIRECT_QUEUE; 
	/**
	 * 评论
	 */
	@Value("${myPugin.rabbitmq.COMMENT_DIRECT_EXCHANGE}")
	public  String COMMENT_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.COMMENT_DIRECT_ROUTINGKEY}")
	public String COMMENT_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.COMMENT_DIRECT_QUEUE}")
	public  String COMMENT_DIRECT_QUEUE; 
	/**
	 * 回复
	 */
	@Value("${myPugin.rabbitmq.REPLY_DIRECT_EXCHANGE}")
	public  String REPLY_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.REPLY_DIRECT_ROUTINGKEY}")
	public String REPLY_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.REPLY_DIRECT_QUEUE}")
	public  String REPLY_DIRECT_QUEUE; 
	/**
	 * 新手任务
	 */
	@Value("${myPugin.rabbitmq.NOVICETASK_DIRECT_EXCHANGE}")
	public  String NOVICETASK_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.NOVICETASK_DIRECT_ROUTINGKEY}")
	public String NOVICETASK_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.NOVICETASK_DIRECT_QUEUE}")
	public  String NOVICETASK_DIRECT_QUEUE; 
	/**
	 * 日常任务
	 */
	@Value("${myPugin.rabbitmq.DAILYTASK_DIRECT_EXCHANGE}")
	public  String DAILYTASK_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.DAILYTASK_DIRECT_ROUTINGKEY}")
	public String DAILYTASK_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.DAILYTASK_DIRECT_QUEUE}")
	public  String DAILYTASK_DIRECT_QUEUE; 
	/**
	 *流水
	 */
	@Value("${myPugin.rabbitmq.FLOWWATER_DIRECT_EXCHANGE}")
	public  String FLOWWATER_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.FLOWWATER_DIRECT_ROUTINGKEY}")
	public String FLOWWATER_DIRECT_ROUTINGKEY;  
	@Value("${myPugin.rabbitmq.FLOWWATER_DIRECT_QUEUE}")
	public  String FLOWWATER_DIRECT_QUEUE; 
	
    @Autowired
    ConnectionFactory  connectionFactory ;
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */  
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);  
        return rabbitTemplate;  
    } 
	 /** 
	  * 文章点击
	  */ 
    /*
     * 设置交换机类型
     */
    @Bean  
    public DirectExchange articleClickDirectExchange() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
    	DirectExchange de = new DirectExchange(ARTICLECLICK_DIRECT_EXCHANGE);
        return de;
    }  
      /* 
	  * 设置队列
	  */
    @Bean  
    public Queue articleClickDirectQueue() {  
        return new Queue(ARTICLECLICK_DIRECT_QUEUE);  
    } 
     /* 
	  * 设置绑定
	  */
    @Bean  
    public Binding  articleClickDirectBinding() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(articleClickDirectQueue()).to(articleClickDirectExchange()).with(ARTICLECLICK_DIRECT_ROUTINGKEY);  
    } 
    
    
    
    /** 
     * 文章阅读
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange articleReadDirectExchange() {  
    	DirectExchange de = new DirectExchange(ARTICLEREAD_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue articleReadDirectQueue() {  
    	return new Queue(ARTICLEREAD_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding articleReadDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(articleReadDirectQueue()).to(articleReadDirectExchange()).with(ARTICLEREAD_DIRECT_ROUTINGKEY);  
    } 

    /** 
     * web文章阅读
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange articleWebReadDirectExchange() {  
    	DirectExchange de = new DirectExchange(ARTICLEWEBREAD_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue articleWebReadDirectQueue() {  
    	return new Queue(ARTICLEWEBREAD_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding articleWebReadDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(articleWebReadDirectQueue()).to(articleWebReadDirectExchange()).with(ARTICLEWEBREAD_DIRECT_ROUTINGKEY);  
    } 
    
    
    /** 
     *评论
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange commentDirectExchange() {  
    	DirectExchange de = new DirectExchange(COMMENT_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue commentDirectQueue() {  
    	return new Queue(COMMENT_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding commentDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(commentDirectQueue()).to(commentDirectExchange()).with(COMMENT_DIRECT_ROUTINGKEY);  
    } 
    
    
    /** 
     *回复
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange replyDirectExchange() {  
    	DirectExchange de = new DirectExchange(REPLY_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue replyDirectQueue() {  
    	return new Queue(REPLY_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding replyDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(replyDirectQueue()).to(replyDirectExchange()).with(REPLY_DIRECT_ROUTINGKEY);  
    } 
    
    
    /** 
     *新手任务
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange noviceTaskDirectExchange() {  
    	DirectExchange de = new DirectExchange(NOVICETASK_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue noviceTaskDirectQueue() {  
    	return new Queue(NOVICETASK_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding noviceTaskDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(noviceTaskDirectQueue()).to(noviceTaskDirectExchange()).with(NOVICETASK_DIRECT_ROUTINGKEY);  
    } 
    
    /** 
     *日常任务
     */  
    /*
     * 设置交换机类型
     */  
    @Bean  
    public DirectExchange dailyTaskDirectExchange() {  
    	DirectExchange de = new DirectExchange(DAILYTASK_DIRECT_EXCHANGE);
    	return de;
    } 
    /*
     * 设置队列
     */
    @Bean  
    public Queue dailyTaskDirectQueue() {  
    	return new Queue(DAILYTASK_DIRECT_QUEUE);  
    } 
    /*
     * 设置绑定
     */
    @Bean  
    public Binding dailyTaskDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(dailyTaskDirectQueue()).to(dailyTaskDirectExchange()).with(DAILYTASK_DIRECT_ROUTINGKEY);  
    } 
    
    /** 
     *流水
     */  
    /*
     * 设置交换机类型
     */   
    @Bean  
    public DirectExchange flowWaterDirectExchange() {  
    	DirectExchange de = new DirectExchange(FLOWWATER_DIRECT_EXCHANGE);
    	return de;
    }
    /*
     * 设置队列
     */
    @Bean  
    public Queue flowWaterDirectQueue() {  
    	return new Queue(FLOWWATER_DIRECT_QUEUE);  
    }
    /*
     * 设置绑定
     */
    @Bean  
    public Binding flowWaterDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(flowWaterDirectQueue()).to(flowWaterDirectExchange()).with(FLOWWATER_DIRECT_ROUTINGKEY);  
    } 
}
