package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Barrage;
import com.nieyue.bean.Complain;
import com.nieyue.dao.ComplainDao;
import com.nieyue.service.BarrageService;
import com.nieyue.service.ComplainService;
@Service
public class ComplainServiceImpl implements ComplainService{
	@Resource
	ComplainDao complainDao;
	@Resource
	BarrageService barrageService;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addComplain(Complain complain) {
		complain.setCreateDate(new Date());
		complain.setUpdateDate(new Date());
		if(complain.getType()==null|| complain.getType().equals("")){
			complain.setType(1);;//投诉类型，默认1非法政治言论，2情色暴力，3诱导诈骗
		}
		complain.setStatus(1);//状态，默认审核1，采纳2
		boolean b = complainDao.addComplain(complain);
		if(b){
			Barrage barrage = barrageService.loadBarrage(complain.getBarrageId());
			if(barrage!=null){//添加投诉+1
				barrage.setComplainNumber(barrage.getComplainNumber()+1);
				barrageService.updateBarrage(barrage);
			}
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delComplain(Integer complainId) {
		boolean b = complainDao.delComplain(complainId);
		if(b){
			Complain complain = complainDao.loadComplain(complainId);
			if(complain!=null){
			Barrage barrage = barrageService.loadBarrage(complain.getBarrageId());
			if(barrage!=null){//删除投诉-1
				barrage.setComplainNumber(barrage.getComplainNumber()-1);
				barrageService.updateBarrage(barrage);
			}
			}
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateComplain(Complain complain) {
		complain.setUpdateDate(new Date());
		boolean b = complainDao.updateComplain(complain);
		return b;
	}

	@Override
	public Complain loadComplain(Integer complainId) {
		Complain r = complainDao.loadComplain(complainId);
		return r;
	}

	@Override
	public int countAll(
			Integer barrageId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer type,
			Integer status
			) {
		int c = complainDao.countAll(
				barrageId,
				 acountId,
				 createDate,
				 updateDate,
				 type,
				 status
				);
		return c;
	}

	@Override
	public List<Complain> browsePagingComplain(
			Integer barrageId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer type,
			Integer status,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Complain> l =complainDao.browsePagingComplain(
				barrageId,
				 acountId,
				 createDate,
				 updateDate,
				 type,
				 status,
				 pageNum-1,
				 pageSize, 
				 orderName, 
				 orderWay);
		return l;
	}
	
}
