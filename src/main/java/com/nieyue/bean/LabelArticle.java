package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签文章
 * @author yy
 *
 */
public class LabelArticle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标签文章id
	 */
	private Integer labelArticleId;
	/**
	 * 类型
	 */
	private Integer labelId;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	
	public LabelArticle() {
		super();
	}

	public LabelArticle(Integer labelArticleId, Integer labelId, Integer articleId, Date createDate, Date updateDate) {
		super();
		this.labelArticleId = labelArticleId;
		this.labelId = labelId;
		this.articleId = articleId;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Integer getLabelArticleId() {
		return labelArticleId;
	}

	public void setLabelArticleId(Integer labelArticleId) {
		this.labelArticleId = labelArticleId;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
