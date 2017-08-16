package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.Comment;
import com.nieyue.bean.CommentAcountDTO;

/**
 * 评论逻辑层接口
 * @author yy
 *
 */
public interface CommentService {
	/** 新增评论 */	
	public boolean addComment(Comment comment) ;	
	/** 删除评论 */	
	public boolean delComment(Integer commentId) ;
	/** 更新评论*/	
	public boolean updateComment(Comment comment);
	/** 装载评论 */	
	public Comment loadComment(Integer commentId);	
	/** 评论总共数目 */	
	public int countAll(Integer articleId,Integer acountId);
	/** 分页评论信息 */
	public List<Comment> browsePagingComment(Integer articleId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
	/** 分页DTO评论信息 */
	public List<CommentAcountDTO> browsePagingCommentAcountDTO(Integer articleId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
