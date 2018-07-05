package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Complain;

/**
 * 投诉逻辑层接口
 * @author yy
 *
 */
public interface ComplainService {
	/** 新增投诉 */	
	public boolean addComplain(Complain complain) ;	
	/** 删除投诉 */	
	public boolean delComplain(Integer complainId) ;
	/** 更新投诉*/	
	public boolean updateComplain(Complain complain);
	/** 装载投诉 */	
	public Complain loadComplain(Integer complainId);	
	/** 投诉总共数目 */	
	public int countAll(
			Integer barrageId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer type,
			Integer status);
	/** 分页投诉信息 */
	public List<Complain> browsePagingComplain(
			Integer barrageId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer type,
			Integer status,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
