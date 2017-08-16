package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Article;
import com.nieyue.bean.Comment;
import com.nieyue.bean.CommentAcountDTO;
import com.nieyue.dao.ArticleDao;
import com.nieyue.dao.CommentDao;
import com.nieyue.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{
	@Resource
	CommentDao commentDao;
	@Resource
	ArticleDao articleDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addComment(Comment comment) {
		comment.setCreateDate(new Date());
		comment.setPointNumber(0);
		comment.setReplyNumber(0);
		boolean b = commentDao.addComment(comment);
		//评论数
		if(b){
			Article article = articleDao.loadSmallArticle(comment.getArticleId());
			int articlecommentnumber = commentDao.countAll(comment.getArticleId(), null);
			article.setCommentNumber(articlecommentnumber);
			articleDao.updateArticle(article);
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delComment(Integer commentId) {
		boolean b = commentDao.delComment(commentId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateComment(Comment comment) {
		boolean b = commentDao.updateComment(comment);
		return b;
	}

	@Override
	public Comment loadComment(Integer commentId) {
		Comment r = commentDao.loadComment(commentId);
		return r;
	}

	@Override
	public int countAll(Integer articleId,Integer acountId) {
		int c = commentDao.countAll(articleId,acountId);
		return c;
	}

	@Override
	public List<Comment> browsePagingComment(
			Integer articleId,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Comment> l = commentDao.browsePagingComment(articleId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<CommentAcountDTO> browsePagingCommentAcountDTO(Integer articleId, Integer acountId, int pageNum,
			int pageSize, String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<CommentAcountDTO> l = commentDao.browsePagingCommentAcountDTO(articleId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
