package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Sign;

/**
 * 签到数据库接口
 * @author yy
 *
 */
@Mapper
public interface SignDao {
	/** 新增签到*/	
	public boolean addSign(Sign sign) ;	
	/** 删除签到 */	
	public boolean delSign(Integer signId) ;
	/** 更新签到*/	
	public boolean updateSign(Sign sign);
	/** 装载签到 */	
	public Sign loadSign(Integer SignId);	
	/** 签到总共数目 */	
	public int countAll(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId
			);	
	/** 分页签到信息 */
	public List<Sign> browsePagingSign(
			@Param("createDate")Date createDate,
			@Param("acountId")Integer acountId,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
