package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Collect;
import com.nieyue.bean.CollectArticleDTO;
import com.nieyue.dao.CollectDao;
import com.nieyue.service.CollectService;
@Service
public class CollectServiceImpl implements CollectService{
	@Resource
	CollectDao collectDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addCollect(Collect collect) {
		Collect c = collectDao.loadCollect(collect.getArticleId(), collect.getAcountId(), null);
		collect.setCreateDate(new Date());
		boolean b=false;
		if(c==null||c.equals("")){			
		b = collectDao.addCollect(collect);
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delCollect(Integer collectId) {
		boolean b = collectDao.delCollect(collectId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateCollect(Collect collect) {
		Collect c = collectDao.loadCollect(collect.getArticleId(), collect.getAcountId(), null);
		boolean b=false;
		if(c==null||c.equals("")){			
		 b = collectDao.updateCollect(collect);
		}
		return b;
	}

	@Override
	public Collect loadCollect(
			Integer articleId,
			Integer acountId,
			Integer collectId) {
		Collect r = collectDao.loadCollect(articleId,acountId,collectId);
		return r;
	}

	@Override
	public int countAll(Integer articleId,Integer acountId) {
		int c = collectDao.countAll(articleId,acountId);
		return c;
	}

	@Override
	public List<Collect> browsePagingCollect(
			Integer articleId,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Collect> l = collectDao.browsePagingCollect(articleId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<CollectArticleDTO> browsePagingCollectArticleDTO(Integer articleId, Integer acountId, int pageNum,
			int pageSize, String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<CollectArticleDTO> l = collectDao.browsePagingCollectArticleDTO(articleId,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
