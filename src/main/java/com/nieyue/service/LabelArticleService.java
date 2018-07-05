package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.LabelArticle;

/**
 * 标签文章逻辑层接口
 * @author yy
 *
 */
public interface LabelArticleService {
	/** 新增标签文章 */	
	public boolean addLabelArticle(LabelArticle labelArticle) ;	
	/** 删除标签文章 */	
	public boolean delLabelArticle(Integer labelArticleId) ;
	/** 更新标签文章*/	
	public boolean updateLabelArticle(LabelArticle labelArticle);
	/** 装载标签文章 */	
	public LabelArticle loadLabelArticle(Integer labelArticleId);	
	/** 标签文章总共数目 */	
	public int countAll(
			Integer labelId,
			Integer articleId,
			Date createDate,
			Date updateDate);
	/** 分页标签文章信息 */
	public List<LabelArticle> browsePagingLabelArticle(
			Integer labelId,
			Integer articleId,
			Date createDate,
			Date updateDate,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
