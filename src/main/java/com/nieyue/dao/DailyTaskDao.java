package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.DailyTask;

/**
 * 每日任务数据库接口
 * @author yy
 *
 */
@Mapper
public interface DailyTaskDao {
	/** 新增每日任务*/	
	public boolean addDailyTask(DailyTask dailyTask) ;	
	/** 删除每日任务 */	
	public boolean delDailyTask(Integer dailyTaskId) ;
	/** 更新每日任务*/	
	public boolean updateDailyTask(DailyTask dailyTask);
	/** 装载每日任务 */	
	public DailyTask loadDailyTask(Integer dailyTaskId);	
	/** 每日任务总共数目 */	
	public int countAll(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId
			);	
	/** 分页每日任务信息 */
	public List<DailyTask> browsePagingDailyTask(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
