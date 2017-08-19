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
		List<NoviceTask> nl = noviceTaskDao.browsePagingNoviceTask(new Date(), noviceTask.getAcountId(), 0, 1, "create_date", "desc");
		//今天已经做过了  且不为 收徒
		if(nl.size()>0 && nl.get(0).getFrequency()!=0){
			return b;
		}
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
	public int countAll(Date createDate,Integer acountId) {
		int c = noviceTaskDao.countAll(createDate,acountId);
		return c;
	}

	@Override
	public List<NoviceTask> browsePagingNoviceTask(
			Date createDate,Integer acountId,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<NoviceTask> l = noviceTaskDao.browsePagingNoviceTask(createDate,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
