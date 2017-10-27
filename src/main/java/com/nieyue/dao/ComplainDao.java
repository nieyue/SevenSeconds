package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Complain;

/**
 * 投诉数据库接口
 * @author yy
 *
 */
@Mapper
public interface ComplainDao {
	/** 新增投诉*/	
	public boolean addComplain(Complain complain) ;	
	/** 删除投诉 */	
	public boolean delComplain(Integer complainId) ;
	/** 更新投诉*/	
	public boolean updateComplain(Complain complain);
	/** 装载投诉 */	
	public Complain loadComplain(Integer complainId);	
	/** 投诉总共数目 */	
	public int countAll(
			@Param("barrageId")Integer barrageId,
			@Param("acountId")Integer acountId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("type")Integer type,
			@Param("status")Integer status
			);	
	/** 分页投诉信息 */
	public List<Complain> browsePagingComplain(
			@Param("barrageId")Integer barrageId,
			@Param("acountId")Integer acountId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("type")Integer type,
			@Param("status")Integer status,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
