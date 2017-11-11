package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Label;
import com.nieyue.dao.LabelDao;
import com.nieyue.service.LabelService;
@Service
public class LabelServiceImpl implements LabelService{
	@Resource
	LabelDao labelDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addLabel(Label label) {
		label.setCreateDate(new Date());
		label.setUpdateDate(new Date());
		boolean b = labelDao.addLabel(label);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delLabel(Integer labelId) {
		boolean b = labelDao.delLabel(labelId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateLabel(Label label) {
		label.setUpdateDate(new Date());
		boolean b = labelDao.updateLabel(label);
		return b;
	}

	@Override
	public Label loadLabel(Integer labelId) {
		Label r = labelDao.loadLabel(labelId);
		return r;
	}

	@Override
	public int countAll(
			String type,
			String name,
			Date createDate,
			Date updateDate
			) {
		int c = labelDao.countAll(
				 type,
				 name,
				 createDate,
				 updateDate
				);
		return c;
	}

	@Override
	public List<Label> browsePagingLabel(
			String type,
			String name,
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
		List<Label> l =labelDao.browsePagingLabel(
				 type,
				 name,
				 createDate,
				 updateDate,
				 pageNum-1,
				 pageSize, 
				 orderName, 
				 orderWay);
		return l;
	}
	
}
