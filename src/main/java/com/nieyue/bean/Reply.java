package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复
 * @author yy
 *
 */
public class Reply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 回复id
	 */
	private Integer replyId;
	
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 点赞数
	 */
	private Integer pointNumber;
	/**
	 * 回复创建时间
	 */
	private Date createDate;
	/**
	 * 评论ID
	 */
	private Integer commentId;
	/**
	 * 回复人ID
	 */
	private Integer acountId;
	public Reply(Integer replyId, String content, Integer pointNumber, Integer replyNumber, Date createDate,
			Integer commentId, Integer acountId) {
		super();
		this.replyId = replyId;
		this.content = content;
		this.pointNumber = pointNumber;
		this.createDate = createDate;
		this.commentId = commentId;
		this.acountId = acountId;
	}
	public Reply() {
		super();
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPointNumber() {
		return pointNumber;
	}
	public void setPointNumber(Integer pointNumber) {
		this.pointNumber = pointNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getAcountId() {
		return acountId;
	}
	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
