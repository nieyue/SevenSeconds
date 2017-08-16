package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.DailyTask;
import com.nieyue.business.DailyTaskBusiness;
import com.nieyue.dao.DailyTaskDao;
import com.nieyue.service.DailyTaskService;
@Service
public class DailyTaskServiceImpl implements DailyTaskService{
	@Resource
	DailyTaskDao dailyTaskDao;
	@Resource
	DailyTaskBusiness dailyTaskBusiness;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addDailyTask(DailyTask dailyTask) {
		boolean b=false;
		dailyTask.setCreateDate(new Date());
		Integer fre = dailyTask.getFrequency();
		String type = dailyTask.getType();
		//不是每日任务
		if(dailyTaskBusiness.dailyTrigger(type, fre) <=0  ||dailyTask.getAcountId()==null || dailyTask.getAcountId().equals("") ){
			return b;
		}
		List<DailyTask> nl = dailyTaskDao.browsePagingDailyTask(new Date(), dailyTask.getAcountId(), 0, Integer.MAX_VALUE, "create_date", "desc");
		int totalNum=0;//总完成次数，nl.size()个相加
		Double totalMoney=0.0;//总获得积分
		//今日已经完成一个以上任务
		if(nl.size()>0 ){
		for (int i = 0; i < nl.size(); i++) {
			DailyTask dt = nl.get(i);
			//已经做过了
			if(dt.getType().equals(type) && dt.getFrequency().equals(fre)){
				return b;
			}
			totalNum+=dt.getFrequency();
			totalMoney+=dt.getMoney();
		}
		//今日任务已经全部完成过
		if(nl.size()==5 && totalNum==19 && totalMoney==500){
		return b;	
		}
		}
		dailyTask.setMoney(dailyTaskBusiness.dailyTrigger(type, fre));
		b = dailyTaskDao.addDailyTask(dailyTask);
		//今日任务全部完成
		if(b && nl.size()==4 && totalNum>=9 && totalMoney>=250){
			//另外加500积分
		}
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delDailyTask(Integer dailyTaskId) {
		boolean b = dailyTaskDao.delDailyTask(dailyTaskId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateDailyTask(DailyTask dailyTask) {
		boolean b=false;
		 b = dailyTaskDao.updateDailyTask(dailyTask);
		return b;
	}

	@Override
	public DailyTask loadDailyTask(
			Integer dailyTaskId) {
		DailyTask r =dailyTaskDao.loadDailyTask(dailyTaskId);
		return r;
	}

	@Override
	public int countAll(Date createDate,Integer acountId) {
		int c = dailyTaskDao.countAll(createDate,acountId);
		return c;
	}

	@Override
	public List<DailyTask> browsePagingDailyTask(
			Date createDate,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<DailyTask> l = dailyTaskDao.browsePagingDailyTask(createDate,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
