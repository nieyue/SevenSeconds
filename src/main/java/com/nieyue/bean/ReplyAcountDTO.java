package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复 账户DTO
 * @author yy
 *
 */
public class ReplyAcountDTO implements Serializable{

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
	public ReplyAcountDTO(Integer replyId, String content, Integer pointNumber, Date createDate, Integer commentId,
			Integer acountId, String nickname, String icon, String phone) {
		super();
		this.replyId = replyId;
		this.content = content;
		this.pointNumber = pointNumber;
		this.createDate = createDate;
		this.commentId = commentId;
		this.acountId = acountId;
		this.nickname = nickname;
		this.icon = icon;
		this.phone = phone;
	}
	
	
	public ReplyAcountDTO() {
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


}
