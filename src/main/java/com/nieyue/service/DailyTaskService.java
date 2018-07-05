package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.DailyTask;

/**
 * 每日任务逻辑层接口
 * @author yy
 *
 */
public interface DailyTaskService {
	/** 新增每日任务 */	
	public boolean addDailyTask(DailyTask dailyTask) ; 	
	/** 删除每日任务 */	
	public boolean delDailyTask(Integer dailyTaskId) ;
	/** 更新每日任务*/	
	public boolean updateDailyTask(DailyTask dailyTask);
	/** 装载每日任务 */	
	public DailyTask loadDailyTask(
			Integer dailyTaskId
			);	
	/** 每日任务总共数目 */	
	public int countAll(Date createDate,Integer acountId);
	/** 分页每日任务信息 */
	public List<DailyTask> browsePagingDailyTask(Date createDate,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
