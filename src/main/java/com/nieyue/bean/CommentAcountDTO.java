package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论 账户DTO
 * @author yy
 *
 */
public class CommentAcountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 评论id
	 */
	private Integer commentId;
	
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 点赞数
	 */
	private Integer pointNumber;
	/**
	 * 回复数量
	 */
	private Integer replyNumber;
	/**
	 * 评论创建时间
	 */
	private Date createDate;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 评论人ID
	 */
	private Integer acountId;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 图像
	 */
	private String icon;
	/**
	 * 电话
	 */
	private String phone;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	public Integer getReplyNumber() {
		return replyNumber;
	}
	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getAcountId() {
		return acountId;
	}
	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public CommentAcountDTO(Integer commentId, String content, Integer pointNumber, Integer replyNumber,
			Date createDate, Integer articleId, Integer acountId, String nickname, String icon, String phone) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.pointNumber = pointNumber;
		this.replyNumber = replyNumber;
		this.createDate = createDate;
		this.articleId = articleId;
		this.acountId = acountId;
		this.nickname = nickname;
		this.icon = icon;
		this.phone = phone;
	}
	public CommentAcountDTO() {
		super();
	}


}
