package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Sign;

/**
 * 签到逻辑层接口
 * @author yy
 *
 */
public interface SignService {
	/** 新增签到 */	
	public boolean addSign(Sign sign) ; 	
	/** 删除签到 */	
	public boolean delSign(Integer signId) ;
	/** 更新签到*/	
	public boolean updateSign(Sign sign);
	/** 装载签到 */	
	public Sign loadSign(
			Integer SignId
			);	
	/** 签到总共数目 */	
	public int countAll(Date createDate,Integer acountId);
	/** 分页签到信息 */
	public List<Sign> browsePagingSign(Date createDate,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
}
