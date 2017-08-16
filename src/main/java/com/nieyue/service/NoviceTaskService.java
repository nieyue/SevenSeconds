package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.NoviceTask;

/**
 * 新手任务逻辑层接口
 * @author yy
 *
 */
public interface NoviceTaskService {
	/** 新增新手任务 */	
	public boolean addNoviceTask(NoviceTask noviceTask) ; 	
	/** 删除新手任务 */	
	public boolean delNoviceTask(Integer noviceTaskId) ;
	/** 更新新手任务*/	
	public boolean updateNoviceTask(NoviceTask noviceTask);
	/** 装载新手任务 */	
	public NoviceTask loadNoviceTask(
			Integer noviceTaskId
			);	
	/** 新手任务总共数目 */	
	public int countAll(Date createDate,Integer acountId);
	/** 分页新手任务信息 */
	public List<NoviceTask> browsePagingNoviceTask(Date createDate,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
