package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Reply;
import com.nieyue.bean.ReplyAcountDTO;

/**
 * 回复数据库接口
 * @author yy
 *
 */
@Mapper
public interface ReplyDao {
	/** 新增回复*/	
	public boolean addReply(Reply reply) ;	
	/** 删除回复 */	
	public boolean delReply(Integer replyId) ;
	/** 更新回复*/	
	public boolean updateReply(Reply reply);
	/** 装载回复 */	
	public Reply loadReply(Integer replyId);	
	/** 回复总共数目 */	
	public int countAll(
			@Param("pointNumber")Integer pointNumber,
			@Param("commentId")Integer commentId,
			@Param("acountId")Integer acountId);	
	/** 分页回复信息 */
	public List<Reply> browsePagingReply(
			@Param("pointNumber")Integer pointNumber,
			@Param("commentId")Integer commentId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
	/** 分页DTO回复信息 */
	public List<ReplyAcountDTO> browsePagingReplyAcountDTO(
			@Param("pointNumber")Integer pointNumber,
			@Param("commentId")Integer commentId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
