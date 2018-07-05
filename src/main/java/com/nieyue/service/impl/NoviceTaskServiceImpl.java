package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.NoviceTask;
import com.nieyue.business.NoviceTaskBusiness;
import com.nieyue.dao.NoviceTaskDao;
import com.nieyue.service.NoviceTaskService;
@Service
public class NoviceTaskServiceImpl implements NoviceTaskService{
	@Resource
	NoviceTaskDao noviceTaskDao;
	@Resource
	NoviceTaskBusiness noviceTaskBusiness;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addNoviceTask(NoviceTask noviceTask) {
		boolean b=false;
		noviceTask.setCreateDate(new Date());
		Integer fre = noviceTask.getFrequency();
		//不是触发任务
		if(noviceTaskBusiness.disposableTrigger(fre) <=0 ||noviceTask.getAcountId()==null || noviceTask.getAcountId().equals("")){
			return b;
		}
		//获取当前任务是否存在
		List<NoviceTask> nl = noviceTaskDao.browsePagingNoviceTask(null, noviceTask.getAcountId(),fre, 0, 1, "create_date", "desc");
		//已经做过了 的任务 
		if(nl.size()>0){
			return b;
		}
		/*//获取每天做的任务
		List<NoviceTask> daynl = noviceTaskDao.browsePagingNoviceTask(new Date(), noviceTask.getAcountId(),null, 0, Integer.MAX_VALUE, "create_date", "desc");
		//今日已经做过做过了，且不是收徒（控制除开收徒外，每天只能做一次新手任务） 
		if(daynl.size()>0){
		for (int i = 0; i < daynl.size(); i++) {
			if((!daynl.get(i).getFrequency().equals(0))&&fre!=0){
				return b;
			}
		}
		}*/
		noviceTask.setMoney(noviceTaskBusiness.disposableTrigger(fre));
		b = noviceTaskDao.addNoviceTask(noviceTask);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delNoviceTask(Integer noviceTaskId) {
		boolean b = noviceTaskDao.delNoviceTask(noviceTaskId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateNoviceTask(NoviceTask noviceTask) {
		boolean b=false;
		 b = noviceTaskDao.updateNoviceTask(noviceTask);
		return b;
	}

	@Override
	public NoviceTask loadNoviceTask(
			Integer noviceTaskId) {
		NoviceTask r =noviceTaskDao.loadNoviceTask(noviceTaskId);
		return r;
	}

	@Override
	public int countAll(Date createDate,Integer acountId,Integer frequency) {
		int c = noviceTaskDao.countAll(createDate,acountId,frequency);
		return c;
	}

	@Override
	public List<NoviceTask> browsePagingNoviceTask(
			Date createDate,Integer acountId,Integer frequency,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<NoviceTask> l = noviceTaskDao.browsePagingNoviceTask(createDate,acountId,frequency,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
