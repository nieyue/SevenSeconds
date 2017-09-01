package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 文章类
 * @author yy
 *
 */
public class Article implements Serializable {
	private static final long serialVersionUID = 8198930199550185349L;
	private Integer articleId;//id
	private String title;//标题
	private Integer isRecommend;//是否推荐 默认0否
	private Integer fixedRecommend;//是否置顶 默认0否
	private String redirectUrl;//跳转url
	private String content;//内容
	private Integer model;//计费模式,1.阅读计费，2.ip计费，3.uv计费，4.千次展示计费
	private Double userUnitPrice;//用户单价
	private Double unitPrice;//单价
	private Double totalPrice;//总价
	private Integer turnNumber;//转发数
	private Integer readingNumber;//阅读数
	private Double userNowTotalPrice;//用户收益
	private Double nowTotalPrice;//已消耗金额
	private Integer commentNumber;//评论数
	private Double scale;//扣量比例默认为0
	private Long pvs;//总pv数
	private Long uvs;//总uv数
	private Long ips;//总ip数
	private String status;//状态
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	private Integer acountId;//账户id
	private Integer articleCateId;//文章类型id
	private ArticleCate articleCate;//文章类型
	private List<Img> imgList;
	
	
	public Article() {
		super();
	}
	

	public Article(Integer articleId, String title, Integer isRecommend, Integer fixedRecommend, String redirectUrl,
			String content, Integer model, Double userUnitPrice, Double unitPrice, Double totalPrice,
			Integer turnNumber, Integer readingNumber, Double userNowTotalPrice, Double nowTotalPrice,
			Integer commentNumber, Double scale, Long pvs, Long uvs, Long ips, String status, Date createDate,
			Date updateDate, Integer acountId, Integer articleCateId, ArticleCate articleCate, List<Img> imgList) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.isRecommend = isRecommend;
		this.fixedRecommend = fixedRecommend;
		this.redirectUrl = redirectUrl;
		this.content = content;
		this.model = model;
		this.userUnitPrice = userUnitPrice;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.turnNumber = turnNumber;
		this.readingNumber = readingNumber;
		this.userNowTotalPrice = userNowTotalPrice;
		this.nowTotalPrice = nowTotalPrice;
		this.commentNumber = commentNumber;
		this.scale = scale;
		this.pvs = pvs;
		this.uvs = uvs;
		this.ips = ips;
		this.status = status;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.acountId = acountId;
		this.articleCateId = articleCateId;
		this.articleCate = articleCate;
		this.imgList = imgList;
	}


	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", title=" + title + ", isRecommend=" + isRecommend
				+ ", fixedRecommend=" + fixedRecommend + ", redirectUrl=" + redirectUrl + ", content=" + content
				+ ", model=" + model + ", userUnitPrice=" + userUnitPrice + ", unitPrice=" + unitPrice + ", totalPrice="
				+ totalPrice + ", turnNumber=" + turnNumber + ", readingNumber=" + readingNumber
				+ ", userNowTotalPrice=" + userNowTotalPrice + ", nowTotalPrice=" + nowTotalPrice + ", commentNumber="
				+ commentNumber + ", scale=" + scale + ", pvs=" + pvs + ", uvs=" + uvs + ", ips=" + ips + ", status="
				+ status + ", createDate=" + createDate + ", updateDate=" + updateDate + ", acountId=" + acountId
				+ ", articleCateId=" + articleCateId + ", articleCate=" + articleCate + ", imgList=" + imgList + "]";
	}


	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getFixedRecommend() {
		return fixedRecommend;
	}
	public void setFixedRecommend(Integer fixedRecommend) {
		this.fixedRecommend = fixedRecommend;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTurnNumber() {
		return turnNumber;
	}
	public void setTurnNumber(Integer turnNumber) {
		this.turnNumber = turnNumber;
	}
	public Integer getReadingNumber() {
		return readingNumber;
	}
	public void setReadingNumber(Integer readingNumber) {
		this.readingNumber = readingNumber;
	}
	public Double getNowTotalPrice() {
		return nowTotalPrice;
	}
	public void setNowTotalPrice(Double nowTotalPrice) {
		this.nowTotalPrice = nowTotalPrice;
	}
	public Double getScale() {
		return scale;
	}
	public void setScale(Double scale) {
		this.scale = scale;
	}
	public Long getPvs() {
		return pvs;
	}
	public void setPvs(Long pvs) {
		this.pvs = pvs;
	}
	public Long getUvs() {
		return uvs;
	}
	public void setUvs(Long uvs) {
		this.uvs = uvs;
	}
	public Long getIps() {
		return ips;
	}
	public void setIps(Long ips) {
		this.ips = ips;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public List<Img> getImgList() {
		return imgList;
	}
	public void setImgList(List<Img> imgList) {
		this.imgList = imgList;
	}
	public Double getUserUnitPrice() {
		return userUnitPrice;
	}
	public void setUserUnitPrice(Double userUnitPrice) {
		this.userUnitPrice = userUnitPrice;
	}
	public Double getUserNowTotalPrice() {
		return userNowTotalPrice;
	}
	public void setUserNowTotalPrice(Double userNowTotalPrice) {
		this.userNowTotalPrice = userNowTotalPrice;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getArticleCateId() {
		return articleCateId;
	}

	public void setArticleCateId(Integer articleCateId) {
		this.articleCateId = articleCateId;
	}

	public ArticleCate getArticleCate() {
		return articleCate;
	}

	public void setArticleCate(ArticleCate articleCate) {
		this.articleCate = articleCate;
	}
	
	
}
