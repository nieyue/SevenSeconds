package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Sign;
import com.nieyue.service.SignService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 签到控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/sign")
public class SignController {
	@Resource
	private SignService signService;
	/**
	 * 签到分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingSign(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="sign_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Sign> list = new ArrayList<Sign>();
			list= signService.browsePagingSign(createDate,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 签到修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateSign(@ModelAttribute Sign sign,HttpSession session)  {
		boolean um = signService.updateSign(sign);
		return ResultUtil.getSR(um);
	}
	/**
	 * 签到增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addSign(@ModelAttribute Sign sign, HttpSession session) {
		boolean am = signService.addSign(sign);
		return ResultUtil.getSR(am);
	}
	/**
	 * 签到删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delSign(@RequestParam("signId") Integer signId,HttpSession session)  {
		boolean dm = signService.delSign(signId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 签到浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = signService.countAll(createDate,acountId);
		return count;
	}
	/**
	 * 签到单个加载
	 * @return
	 */
	@RequestMapping(value = "/{signId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadSign(@PathVariable("signId") Integer signId,HttpSession session)  {
		List<Sign> list = new ArrayList<Sign>();
		Sign Sign = signService.loadSign(signId);
			if(Sign!=null &&!Sign.equals("")){
				list.add(Sign);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

	
}
