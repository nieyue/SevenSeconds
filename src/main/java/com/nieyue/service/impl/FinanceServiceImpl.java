package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.AcountDTO;
import com.nieyue.bean.Finance;
import com.nieyue.bean.FinanceDataDTO;
import com.nieyue.bean.FinanceDayDataDTO;
import com.nieyue.dao.FinanceDao;
import com.nieyue.service.FinanceService;
@Service
public class FinanceServiceImpl implements FinanceService{
	@Resource
	FinanceDao financeDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addFinance(Finance finance) {
		finance.setUpdateDate(new Date());
		boolean b = financeDao.addFinance(finance);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delFinance(Integer financeId) {
		boolean b = financeDao.delFinance(financeId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateFinance(Finance finance) {
		finance.setUpdateDate(new Date());
		boolean b = financeDao.updateFinance(finance);
		return b;
	}

	@Override
	public Finance loadFinance(Integer financeId) {
		Finance r = financeDao.loadFinance(financeId);
		return r;
	}

	@Override
	public int countAll(
			Double money,
			Integer acountId) {
		int c = financeDao.countAll(money,acountId);
		return c;
	}

	@Override
	public List<Finance> browsePagingFinance(Double money,Integer acountId,int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Finance> l = financeDao.browsePagingFinance(money,acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<AcountDTO> browsePagingFinanceByAcountId(Integer acountId,int pageNum, int pageSize, String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<AcountDTO> l = financeDao.browsePagingFinanceByAcountId( acountId,pageNum-1, pageSize, orderName, orderWay);
		return l;
	}
	@Override
	public List<FinanceDataDTO> browseFinanceData(Integer acountId) {
		List<FinanceDataDTO> f = financeDao.browseFinanceData(acountId);
		return f;
	}
	@Override
	public List<FinanceDayDataDTO> browseFinanceDayData(Integer acountId,Date startDate, Date endDate, Integer type, Integer subtype) {
		List<FinanceDayDataDTO> f = financeDao.browseFinanceDayData( acountId,startDate, endDate, type, subtype);
		return f;
	}

	
}
