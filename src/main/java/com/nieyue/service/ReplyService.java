package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.Reply;
import com.nieyue.bean.ReplyAcountDTO;

/**
 * 回复逻辑层接口
 * @author yy
 *
 */
public interface ReplyService {
	/** 新增回复 */	
	public boolean addReply(Reply reply) ;	
	/** 删除回复 */	
	public boolean delReply(Integer replyId) ;
	/** 更新回复*/	
	public boolean updateReply(Reply reply);
	/** 装载回复 */	
	public Reply loadReply(Integer replyId);	
	/** 回复总共数目 */	
	public int countAll(Integer pointNumber,Integer commentId,Integer acountId);
	/** 分页回复信息 */
	public List<Reply> browsePagingReply(Integer pointNumber,Integer commentId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
	/** 分页DTO回复信息 */
	public List<ReplyAcountDTO> browsePagingReplyAcountDTO(Integer pointNumber,Integer commentId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
