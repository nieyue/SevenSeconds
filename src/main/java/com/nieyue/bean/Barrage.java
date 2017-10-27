package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 弹幕
 * @author yy
 *
 */
public class Barrage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 弹幕id
	 */
	private Integer barrageId;
	
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 头像
	 */
	private String icon;
	/**
	 * 显示位置，随机1,2,3,4
	 */
	private Integer location;
	/**
	 * 字体风格，默认1
	 */
	private Integer fontStyle;
	/**
	 * 字体大小，默认1小，2中，3大
	 */
	private Integer fontSize;
	/**
	 * 背景颜色默认#000
	 */
	private String fontBgcolor;
	/**
	 * 字体颜色默认#fff
	 */
	private String fontColor;
	/**
	 * 投诉次数，默认0
	 */
	private Integer complainNumber;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 状态，默认正常1，异常0
	 */
	private Integer status;
	/**
	 * 文章ID
	 */
	private Integer articleId;
	/**
	 * 弹幕人ID
	 */
	private Integer acountId;
	
	public Barrage() {
		super();
	}

	public Barrage(Integer barrageId, String content, String icon, Integer location, Integer fontStyle,
			Integer fontSize, String fontBgcolor, String fontColor, Integer complainNumber, Date createDate,
			Date updateDate, Integer status, Integer articleId, Integer acountId) {
		super();
		this.barrageId = barrageId;
		this.content = content;
		this.icon = icon;
		this.location = location;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.fontBgcolor = fontBgcolor;
		this.fontColor = fontColor;
		this.complainNumber = complainNumber;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
		this.articleId = articleId;
		this.acountId = acountId;
	}

	public Integer getBarrageId() {
		return barrageId;
	}

	public void setBarrageId(Integer barrageId) {
		this.barrageId = barrageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public Integer getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(Integer fontStyle) {
		this.fontStyle = fontStyle;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontBgcolor() {
		return fontBgcolor;
	}

	public void setFontBgcolor(String fontBgcolor) {
		this.fontBgcolor = fontBgcolor;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public Integer getComplainNumber() {
		return complainNumber;
	}

	public void setComplainNumber(Integer complainNumber) {
		this.complainNumber = complainNumber;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

}
