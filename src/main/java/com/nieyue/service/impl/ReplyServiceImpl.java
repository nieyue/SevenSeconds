package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Comment;
import com.nieyue.bean.Reply;
import com.nieyue.bean.ReplyAcountDTO;
import com.nieyue.dao.CommentDao;
import com.nieyue.dao.ReplyDao;
import com.nieyue.service.ReplyService;
@Service
public class ReplyServiceImpl implements ReplyService{
	@Resource
	ReplyDao replyDao;
	@Resource
	CommentDao commentDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addReply(Reply reply) {
		reply.setCreateDate(new Date());
		reply.setPointNumber(0);
		boolean b = replyDao.addReply(reply);
		//回复数
		if(b){
			Comment comment = commentDao.loadComment(reply.getCommentId());
			int commentreplynumber = replyDao.countAll(comment.getCommentId(), null);
			comment.setReplyNumber(commentreplynumber);
			b=commentDao.updateComment(comment);
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delReply(Integer replyId) {
		boolean b = replyDao.delReply(replyId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateReply(Reply reply) {
		boolean b = replyDao.updateReply(reply);
		return b;
	}

	@Override
	public Reply loadReply(Integer replyId) {
		Reply r = replyDao.loadReply(replyId);
		return r;
	}

	@Override
	public int countAll(Integer commentId,Integer acountId) {
		int c = replyDao.countAll(commentId,acountId);
		return c;
	}

	@Override
	public List<Reply> browsePagingReply(
			Integer commentId,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Reply> l = replyDao.browsePagingReply(commentId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<ReplyAcountDTO> browsePagingReplyAcountDTO(Integer commentId, Integer acountId, int pageNum,
			int pageSize, String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<ReplyAcountDTO> l = replyDao.browsePagingReplyAcountDTO(commentId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
