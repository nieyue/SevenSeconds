package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Collect;
import com.nieyue.bean.CollectArticleDTO;

/**
 * 收藏数据库接口
 * @author yy
 *
 */
@Mapper
public interface CollectDao {
	/** 新增收藏*/	
	public boolean addCollect(Collect collect) ;	
	/** 删除收藏 */	
	public boolean delCollect(Integer collectId) ;
	/** 更新收藏*/	
	public boolean updateCollect(Collect collect);
	/** 装载收藏 */	
	public Collect loadCollect(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("collectId")Integer collectId);	
	/** 收藏总共数目 */	
	public int countAll(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId
			);	
	/** 分页收藏信息 */
	public List<Collect> browsePagingCollect(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
	/** 分页DTO收藏信息 */
	public List<CollectArticleDTO> browsePagingCollectArticleDTO(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;	
}
