package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Barrage;

/**
 * 弹幕逻辑层接口
 * @author yy
 *
 */
public interface BarrageService {
	/** 新增弹幕 */	
	public boolean addBarrage(Barrage barrage) ;	
	/** 删除弹幕 */	
	public boolean delBarrage(Integer barrageId) ;
	/** 更新弹幕*/	
	public boolean updateBarrage(Barrage barrage);
	/** 装载弹幕 */	
	public Barrage loadBarrage(Integer barrageId);	
	/** 弹幕总共数目 */	
	public int countAll(
			Integer articleId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer complainNumber,
			Integer status);
	/** 分页弹幕信息 */
	public List<Barrage> browsePagingBarrage(
			Integer articleId,
			Integer acountId,
			Date createDate,
			Date updateDate,
			Integer complainNumber,
			Integer status,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
