package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 收藏 文章DTO
 * @author yy
 *
 */
public class CollectArticleDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收藏id
	 */
	private Integer collectId;
	/**
	 * 收藏创建时间
	 */
	private Date createDate;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 收藏人ID
	 */
	private Integer acountId;
	/**
	 * 文章标题
	 */
	private  String title;
	/**
	 * 阅读数
	 */
	private  Integer readingNumber;
	/**
	 * 评论数
	 */
	private  Integer commentNumber;
	/**
	 * 文章图片
	 */
	private  List<Img> imgList;
	public CollectArticleDTO(Integer collectId, Date createDate, Integer articleId, Integer acountId, String title,
			Integer readingNumber, Integer commentNumber, List<Img> imgList) {
		super();
		this.collectId = collectId;
		this.createDate = createDate;
		this.articleId = articleId;
		this.acountId = acountId;
		this.title = title;
		this.readingNumber = readingNumber;
		this.commentNumber = commentNumber;
		this.imgList = imgList;
	}
	public CollectArticleDTO() {
		super();
	}
	public Integer getCollectId() {
		return collectId;
	}
	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getReadingNumber() {
		return readingNumber;
	}
	public void setReadingNumber(Integer readingNumber) {
		this.readingNumber = readingNumber;
	}
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public List<Img> getImgList() {
		return imgList;
	}
	public void setImgList(List<Img> imgList) {
		this.imgList = imgList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
