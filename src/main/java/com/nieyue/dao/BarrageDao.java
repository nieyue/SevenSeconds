package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Barrage;

/**
 * 弹幕数据库接口
 * @author yy
 *
 */
@Mapper
public interface BarrageDao {
	/** 新增弹幕*/	
	public boolean addBarrage(Barrage barrage) ;	
	/** 删除弹幕 */	
	public boolean delBarrage(Integer barrageId) ;
	/** 更新弹幕*/	
	public boolean updateBarrage(Barrage barrage);
	/** 装载弹幕 */	
	public Barrage loadBarrage(Integer barrageId);	
	/** 弹幕总共数目 */	
	public int countAll(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("complainNumber")Integer complainNumber,
			@Param("status")Integer status
			);	
	/** 分页弹幕信息 */
	public List<Barrage> browsePagingBarrage(
			@Param("articleId")Integer articleId,
			@Param("acountId")Integer acountId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("complainNumber")Integer complainNumber,
			@Param("status")Integer status,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
