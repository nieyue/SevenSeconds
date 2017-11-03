package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.NoviceTask;

/**
 * 新手任务数据库接口
 * @author yy
 *
 */
@Mapper
public interface NoviceTaskDao {
	/** 新增新手任务*/	
	public boolean addNoviceTask(NoviceTask noviceTask) ;	
	/** 删除新手任务 */	
	public boolean delNoviceTask(Integer noviceTaskId) ;
	/** 更新新手任务*/	
	public boolean updateNoviceTask(NoviceTask noviceTask);
	/** 装载新手任务 */	
	public NoviceTask loadNoviceTask(Integer noviceTaskId);	
	/** 新手任务总共数目 */	
	public int countAll(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId,
			@Param("frequency")Integer frequency
			);	
	/** 分页新手任务信息 */
	public List<NoviceTask> browsePagingNoviceTask(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId,
			@Param("frequency")Integer frequency,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
