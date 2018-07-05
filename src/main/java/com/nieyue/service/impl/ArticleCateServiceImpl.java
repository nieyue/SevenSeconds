package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.ArticleCate;
import com.nieyue.dao.ArticleCateDao;
import com.nieyue.service.ArticleCateService;
@Service
public class ArticleCateServiceImpl implements ArticleCateService{
	@Resource
	ArticleCateDao articleCateDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addArticleCate(ArticleCate articleCate) {
		boolean b=false;
		List<ArticleCate> acl = articleCateDao.browsePagingArticleCate(null, articleCate.getName(), 0, Integer.MAX_VALUE, "article_cate_id", "asc");
		if(acl.size()>0){
			return b;//存在不能添加
		}
		articleCate.setUpdateDate(new Date());
		b = articleCateDao.addArticleCate(articleCate);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delArticleCate(Integer articleCateId) {
		boolean b = articleCateDao.delArticleCate(articleCateId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateArticleCate(ArticleCate articleCate) {
		boolean b=false;
		List<ArticleCate> acl = articleCateDao.browsePagingArticleCate(null, articleCate.getName(), 0, Integer.MAX_VALUE, "article_cate_id", "asc");
		if(acl.size()>0 && !acl.get(0).getarticleCateId().equals(articleCate.getarticleCateId())){
			return b;//存在且不是自身 ，不能添加
		}
		articleCate.setUpdateDate(new Date());
		b = articleCateDao.updateArticleCate(articleCate);
		return b;
	}

	@Override
	public ArticleCate loadArticleCate(Integer articleCateId) {
		ArticleCate r = articleCateDao.loadArticleCate(articleCateId);
		return r;
	}

	@Override
	public int countAll(Integer holder,String name) {
		int c = articleCateDao.countAll(holder,name);
		return c;
	}

	@Override
	public List<ArticleCate> browsePagingArticleCate(Integer holder,String name,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<ArticleCate> l = articleCateDao.browsePagingArticleCate(holder,name,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
