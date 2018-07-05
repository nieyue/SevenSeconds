package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.FlowWater;

/**
 * 流水数据库接口
 * @author yy
 *
 */
@Mapper
public interface FlowWaterDao {
	/** 新增流水*/	
	public boolean addFlowWater(FlowWater flowWater) ;	
	/** 删除流水 */	
	public boolean delFlowWater(Integer flowWaterId) ;
	/** 更新流水*/	
	public boolean updateFlowWater(FlowWater flowWater);
	/** 装载流水 */	
	public FlowWater loadFlowWater(Integer flowWaterId);	
	/** 流水总共数目 */	
	public int countAll(@Param("acountId")Integer acountId,@Param("type")Integer type,@Param("subtype")Integer subtype,@Param("createDate")Date createDate);	
	/** 分页流水信息 */
	public List<FlowWater> browsePagingFlowWater(@Param("acountId")Integer acountId,@Param("type")Integer type,@Param("subtype")Integer subtype,@Param("createDate")Date createDate,@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("orderName")String orderName,@Param("orderWay")String orderWay) ;		
}
