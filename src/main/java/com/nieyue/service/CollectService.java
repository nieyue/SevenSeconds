package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.Collect;
import com.nieyue.bean.CollectArticleDTO;

/**
 * 收藏逻辑层接口
 * @author yy
 *
 */
public interface CollectService {
	/** 新增收藏 */	
	public boolean addCollect(Collect collect) ; 	
	/** 删除收藏 */	
	public boolean delCollect(Integer collectId) ;
	/** 更新收藏*/	
	public boolean updateCollect(Collect collect);
	/** 装载收藏 */	
	public Collect loadCollect(
			Integer articleId,
			Integer acountId,
			Integer collectId
			);	
	/** 收藏总共数目 */	
	public int countAll(Integer articleId,Integer acountId);
	/** 分页收藏信息 */
	public List<Collect> browsePagingCollect(Integer articleId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
	/** 分页DTO收藏信息 */
	public List<CollectArticleDTO> browsePagingCollectArticleDTO(Integer articleId,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
