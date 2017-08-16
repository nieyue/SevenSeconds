package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏
 * @author yy
 *
 */
public class Collect implements Serializable{

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
	public Integer getCollectId() {
		return collectId;
	}
	public void setCollectionId(Integer collectId) {
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Collect(Integer collectId, Date createDate, Integer articleId, Integer acountId) {
		super();
		this.collectId = collectId;
		this.createDate = createDate;
		this.articleId = articleId;
		this.acountId = acountId;
	}
	public Collect() {
		super();
	}
	

}
