package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.AcountDTO;
import com.nieyue.bean.Finance;
import com.nieyue.bean.FinanceDataDTO;
import com.nieyue.bean.FinanceDayDataDTO;

/**
 * 财务逻辑层接口
 * @author yy
 *
 */
public interface FinanceService {
	/** 新增财务 */	
	public boolean addFinance(Finance finance) ;	
	/** 删除财务 */	
	public boolean delFinance(Integer financeId) ;
	/** 更新财务*/	
	public boolean updateFinance(Finance finance);
	/** 装载财务 */	
	public Finance loadFinance(Integer financeId);	
	/** 财务总共数目 */	
	public int countAll(
			Double money,
			Integer acountId);
	/** 账户分页浏览收益数目排行榜 */
	public List<AcountDTO> browsePagingFinanceByAcountId(Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;		
	/** 分页财务信息 */
	public List<Finance> browsePagingFinance(Double money,Integer acountId,int pageNum,int pageSize,String orderName,String orderWay) ;
	/** 财务数据 */
	public List<FinanceDataDTO> browseFinanceData(Integer acountId) ;		
	/** 财务日数据 */
	public List<FinanceDayDataDTO> browseFinanceDayData(Integer acountId,Date startDate,Date endDate,Integer type,Integer subtype) ;	
}
