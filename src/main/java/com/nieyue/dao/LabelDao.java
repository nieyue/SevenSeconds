package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Label;

/**
 * 标签数据库接口
 * @author yy
 *
 */
@Mapper
public interface LabelDao {
	/** 新增标签*/	
	public boolean addLabel(Label label) ;	
	/** 删除标签 */	
	public boolean delLabel(Integer labelId) ;
	/** 更新标签*/	
	public boolean updateLabel(Label label);
	/** 装载标签 */	
	public Label loadLabel(Integer labelId);	
	/** 标签总共数目 */	
	public int countAll(
			@Param("type")String type,
			@Param("name")String name,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate
			);	
	/** 分页标签信息 */
	public List<Label> browsePagingLabel(
			@Param("type")String type,
			@Param("name")String name,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
