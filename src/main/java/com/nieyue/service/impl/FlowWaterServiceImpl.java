package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.FlowWater;
import com.nieyue.dao.FlowWaterDao;
import com.nieyue.service.FlowWaterService;
@Service
public class FlowWaterServiceImpl implements FlowWaterService{
	@Resource
	FlowWaterDao flowWaterDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addFlowWater(FlowWater flowWater) {
		flowWater.setCreateDate(new Date());
		boolean b = flowWaterDao.addFlowWater(flowWater);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delFlowWater(Integer flowWaterId) {
		boolean b = flowWaterDao.delFlowWater(flowWaterId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateFlowWater(FlowWater flowWater) {
		boolean b = flowWaterDao.updateFlowWater(flowWater);
		return b;
	}

	@Override
	public FlowWater loadFlowWater(Integer flowWaterId) {
		FlowWater r = flowWaterDao.loadFlowWater(flowWaterId);
		return r;
	}

	@Override
	public int countAll(Integer acountId,Integer type,Integer subtype,Date createDate) {
		int c = flowWaterDao.countAll(acountId, type, subtype,createDate);
		return c;
	}

	@Override
	public List<FlowWater> browsePagingFlowWater(
			Integer acountId,Integer type,Integer subtype,Date createDate,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<FlowWater> l = flowWaterDao.browsePagingFlowWater(acountId, type, subtype,createDate,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
