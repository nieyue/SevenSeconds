package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 学堂
 * @author 聂跃
 * @date 2017年5月2日
 */
public class School  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 学堂id
	 */
	private Integer schoolId;
	/**
	 * 学堂标题
	 */
	private String title;
	/**
	 * 图片地址
	 */
	private String imgAddress;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 时间
	 */
	private Date createDate;
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public School(Integer schoolId, String title, String imgAddress,String content, Date createDate) {
		super();
		this.schoolId = schoolId;
		this.title = title;
		this.imgAddress = imgAddress;
		this.content = content;
		this.createDate = createDate;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public School() {
		super();
	}
	
}
