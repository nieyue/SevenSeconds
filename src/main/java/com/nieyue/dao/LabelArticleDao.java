package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.LabelArticle;

/**
 * 标签文章数据库接口
 * @author yy
 *
 */
@Mapper
public interface LabelArticleDao {
	/** 新增标签文章*/	
	public boolean addLabelArticle(LabelArticle labelArticle) ;	
	/** 删除标签文章 */	
	public boolean delLabelArticle(Integer labelArticleId) ;
	/** 更新标签文章*/	
	public boolean updateLabelArticle(LabelArticle labelArticle);
	/** 装载标签文章 */	
	public LabelArticle loadLabelArticle(Integer labelArticleId);	
	/** 标签文章总共数目 */	
	public int countAll(
			@Param("labelId")Integer labelId,
			@Param("articleId")Integer articleId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate
			);	
	/** 分页标签文章信息 */
	public List<LabelArticle> browsePagingLabelArticle(
			@Param("labelId")Integer labelId,
			@Param("articleId")Integer articleId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
