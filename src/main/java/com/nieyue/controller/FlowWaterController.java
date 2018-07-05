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

import com.nieyue.bean.FlowWater;
import com.nieyue.service.FlowWaterService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 流水控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/flowWater")
public class FlowWaterController {
	@Resource
	private FlowWaterService flowWaterService;
	
	/**
	 * 流水分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingFlowWater(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="subtype",required=false)Integer subtype,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="flow_water_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<FlowWater> list = new ArrayList<FlowWater>();
			list= flowWaterService.browsePagingFlowWater(acountId,type,subtype,createDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 流水修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateFlowWater(@ModelAttribute FlowWater flowWater,HttpSession session)  {
		boolean um = flowWaterService.updateFlowWater(flowWater);
		return ResultUtil.getSR(um);
	}
	/**
	 * 流水增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addFlowWater(@ModelAttribute FlowWater flowWater, HttpSession session) {
		boolean am = flowWaterService.addFlowWater(flowWater);
		return ResultUtil.getSR(am);
	}
	/**
	 * 流水删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delFlowWater(@RequestParam("flowWaterId") Integer flowWaterId,HttpSession session)  {
		boolean dm = flowWaterService.delFlowWater(flowWaterId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 流水浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="subtype",required=false)Integer subtype,
			@RequestParam(value="createDate",required=false)Date createDate,
			HttpSession session)  {
		int count = flowWaterService.countAll(acountId,type,subtype,createDate);
		return count;
	}
	/**
	 * 流水单个加载
	 * @return
	 */
	@RequestMapping(value = "/{flowWaterId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadFlowWater(@PathVariable("flowWaterId") Integer flowWaterId,HttpSession session)  {
		List<FlowWater> list = new ArrayList<FlowWater>();
		FlowWater flowWater = flowWaterService.loadFlowWater(flowWaterId);
			if(flowWater!=null &&!flowWater.equals("")){
				list.add(flowWater);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
