package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Sign;
import com.nieyue.business.SignBusiness;
import com.nieyue.dao.SignDao;
import com.nieyue.service.SignService;
import com.nieyue.util.DateUtil;
@Service
public class SignServiceImpl implements SignService{
	@Resource
	SignDao signDao;
	@Resource
	SignBusiness signBusiness;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addSign(Sign sign) {
		boolean b=false;
		List<Sign> signList = signDao.browsePagingSign(null,sign.getAcountId(), 0, Integer.MAX_VALUE, "create_date", "desc");
		sign.setCreateDate(new Date());
		//新用户
		if(signList.size()<=0){
			sign.setLevel(1);
			sign.setMoney(signBusiness.signTrigger(1));
			b = signDao.addSign(sign);
			return b;
		}
		Sign s0 = signList.get(0);
		//今天已经签到过了
		if(DateUtil.getSeparatedTime(s0.getCreateDate(), new Date())<=0){
			return b;	
		}
		//相差2天及以上或者新用户
		if(DateUtil.getSeparatedTime(s0.getCreateDate(), new Date())>1){
			sign.setLevel(1);
		}
		//相差一天,连续签到
		if(DateUtil.getSeparatedTime(s0.getCreateDate(), new Date())==1){
			if(s0.getLevel()<7){				
			sign.setLevel(s0.getLevel()+1);
			}else{
				sign.setLevel(7);
			}
		}
		sign.setMoney(signBusiness.signTrigger(s0.getLevel()));
		b = signDao.addSign(sign);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delSign(Integer signId) {
		boolean b = signDao.delSign(signId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateSign(Sign sign) {
		boolean b=false;
		 b = signDao.updateSign(sign);
		return b;
	}

	@Override
	public Sign loadSign(
			Integer signId) {
		Sign r =signDao.loadSign(signId);
		return r;
	}

	@Override
	public int countAll(Date createDate,Integer acountId) {
		int c = signDao.countAll(createDate,acountId);
		return c;
	}

	@Override
	public List<Sign> browsePagingSign(
			Date createDate,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Sign> l = signDao.browsePagingSign(createDate,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
