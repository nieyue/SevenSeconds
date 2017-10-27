package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Barrage;
import com.nieyue.dao.BarrageDao;
import com.nieyue.service.BarrageService;
@Service
public class BarrageServiceImpl implements BarrageService{
	@Resource
	BarrageDao barrageDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addBarrage(Barrage barrage) {
		barrage.setCreateDate(new Date());
		barrage.setUpdateDate(new Date());
		if(barrage.getLocation()==null ||barrage.getLocation().equals("")){
			barrage.setLocation(new Random().nextInt(4)+1);//显示位置，随机1,2,3,4
		}
		if(barrage.getFontStyle()==null ||barrage.getFontStyle().equals("")){
			barrage.setFontStyle(1);//字体风格，默认1
		}
		if(barrage.getFontSize()==null ||barrage.getFontSize().equals("")){
			barrage.setFontSize(2);//字体大小，默认1小，2中，3大
		}
		if(barrage.getFontBgcolor()==null ||barrage.getFontBgcolor().equals("")){
			barrage.setFontBgcolor("#000");//背景颜色默认#000
		}
		if(barrage.getFontColor()==null ||barrage.getFontColor().equals("")){
			barrage.setFontColor("#fff");//字体颜色默认#fff
		}
		barrage.setComplainNumber(0);//投诉次数，默认0
		barrage.setStatus(1);//状态，默认正常1，异常0
		boolean b = barrageDao.addBarrage(barrage);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delBarrage(Integer barrageId) {
		boolean b = barrageDao.delBarrage(barrageId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateBarrage(Barrage barrage) {
		barrage.setUpdateDate(new Date());
		boolean b = barrageDao.updateBarrage(barrage);
		return b;
	}

	@Override
	public Barrage loadBarrage(Integer barrageId) {
		Barrage r = barrageDao.loadBarrage(barrageId);
		return r;
	}

	@Override
	public int countAll(
			Integer articleId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer complainNumber,
			Integer status
			) {
		int c = barrageDao.countAll(
				 articleId,
				 acountId,
				 createDate,
				 updateDate,
				 complainNumber,
				 status
				);
		return c;
	}

	@Override
	public List<Barrage> browsePagingBarrage(
			Integer articleId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer complainNumber,
			Integer status,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Barrage> l =barrageDao.browsePagingBarrage(
				 articleId,
				 acountId,
				 createDate,
				 updateDate,
				 complainNumber,
				 status,
				 pageNum-1,
				 pageSize, 
				 orderName, 
				 orderWay);
		return l;
	}
	
}
