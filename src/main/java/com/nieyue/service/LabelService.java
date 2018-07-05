package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Label;

/**
 * 标签逻辑层接口
 * @author yy
 *
 */
public interface LabelService {
	/** 新增标签 */	
	public boolean addLabel(Label label) ;	
	/** 删除标签 */	
	public boolean delLabel(Integer labelId) ;
	/** 更新标签*/	
	public boolean updateLabel(Label label);
	/** 装载标签 */	
	public Label loadLabel(Integer labelId);	
	/** 标签总共数目 */	
	public int countAll(
			String type,
			String name,
			Date createDate,
			Date updateDate);
	/** 分页标签信息 */
	public List<Label> browsePagingLabel(
			String type,
			String name,
			Date createDate,
			Date updateDate,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
