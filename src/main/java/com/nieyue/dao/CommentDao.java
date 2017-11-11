package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Comment;
import com.nieyue.bean.CommentAcountDTO;

/**
 * 评论数据库接口
 * @author yy
 *
 */
@Mapper
public interface CommentDao {
	/** 新增评论*/	
	public boolean addComment(Comment comment) ;	
	/** 删除评论 */	
	public boolean delComment(Integer commentId) ;
	/** 更新评论*/	
	public boolean updateComment(Comment comment);
	/** 装载评论 */	
	public Comment loadComment(Integer commentId);	
	/** 评论总共数目 */	
	public int countAll(
			@Param("pointNumber")Integer pointNumber,
			@Param("replyNumber")Integer replyNumber,
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId);	
	/** 分页评论信息 */
	public List<Comment> browsePagingComment(
			@Param("pointNumber")Integer pointNumber,
			@Param("replyNumber")Integer replyNumber,
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
	/** 分页DTO评论信息 */
	public List<CommentAcountDTO> browsePagingCommentAcountDTO(
			@Param("pointNumber")Integer pointNumber,
			@Param("replyNumber")Integer replyNumber,
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
