package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.LabelArticle;
import com.nieyue.dao.LabelArticleDao;
import com.nieyue.service.LabelArticleService;
@Service
public class LabelArticleServiceImpl implements LabelArticleService{
	@Resource
	LabelArticleDao labelArticleDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addLabelArticle(LabelArticle labelArticle) {
		labelArticle.setCreateDate(new Date());
		labelArticle.setUpdateDate(new Date());
		boolean b = labelArticleDao.addLabelArticle(labelArticle);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delLabelArticle(Integer labelArticleId) {
		boolean b = labelArticleDao.delLabelArticle(labelArticleId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateLabelArticle(LabelArticle labelArticle) {
		labelArticle.setUpdateDate(new Date());
		boolean b = labelArticleDao.updateLabelArticle(labelArticle);
		return b;
	}

	@Override
	public LabelArticle loadLabelArticle(Integer labelArticleId) {
		LabelArticle r = labelArticleDao.loadLabelArticle(labelArticleId);
		return r;
	}

	@Override
	public int countAll(
			Integer labelId,
			Integer articleId,
			Date createDate,
			Date updateDate
			) {
		int c = labelArticleDao.countAll(
				 labelId,
				 articleId,
				 createDate,
				 updateDate
				);
		return c;
	}

	@Override
	public List<LabelArticle> browsePagingLabelArticle(
			Integer labelId,
			Integer articleId,
			Date createDate,
			Date updateDate,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<LabelArticle> l =labelArticleDao.browsePagingLabelArticle(
				 labelId,
				 articleId,
				 createDate,
				 updateDate,
				 pageNum-1,
				 pageSize, 
				 orderName, 
				 orderWay);
		return l;
	}
	
}
