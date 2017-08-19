package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.FlowWater;

/**
 * 流水逻辑层接口
 * @author yy
 *
 */
public interface FlowWaterService {
	/** 新增流水 */	
	public boolean addFlowWater(FlowWater FlowWater) ;	
	/** 删除流水 */	
	public boolean delFlowWater(Integer FlowWaterId) ;
	/** 更新流水*/	
	public boolean updateFlowWater(FlowWater FlowWater);
	/** 装载流水 */	
	public FlowWater loadFlowWater(Integer FlowWaterId);	
	/** 流水总共数目 */	
	public int countAll(Integer acountId ,Integer type,Integer subtype,Date createDate);
	/** 分页流水信息 */
	public List<FlowWater> browsePagingFlowWater(Integer acountId ,Integer type,Integer subtype,Date createDate,int pageNum,int pageSize,String orderName,String orderWay) ;
}
