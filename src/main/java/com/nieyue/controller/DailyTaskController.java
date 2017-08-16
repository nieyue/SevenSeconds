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

import com.nieyue.bean.DailyTask;
import com.nieyue.service.DailyTaskService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 每日任务控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/dailyTask")
public class DailyTaskController {
	@Resource
	private DailyTaskService dailyTaskService;
	/**
	 * 每日任务分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingDailyTask(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="daily_task_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<DailyTask> list = new ArrayList<DailyTask>();
			list= dailyTaskService.browsePagingDailyTask(createDate,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 每日任务修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateDailyTask(@ModelAttribute DailyTask dailyTask,HttpSession session)  {
		boolean um = dailyTaskService.updateDailyTask(dailyTask);
		return ResultUtil.getSR(um);
	}
	/**
	 * 每日任务增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addDailyTask(@ModelAttribute DailyTask dailyTask, HttpSession session) {
		boolean am = dailyTaskService.addDailyTask(dailyTask);
		return ResultUtil.getSR(am);
	}
	/**
	 * 每日任务删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delDailyTask(@RequestParam("dailyTaskId") Integer dailyTaskId,HttpSession session)  {
		boolean dm = dailyTaskService.delDailyTask(dailyTaskId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 每日任务浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = dailyTaskService.countAll(createDate,acountId);
		return count;
	}
	/**
	 * 每日任务单个加载
	 * @return
	 */
	@RequestMapping(value = "/{dailyTaskId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadDailyTask(@PathVariable("dailyTaskId") Integer dailyTaskId,HttpSession session)  {
		List<DailyTask> list = new ArrayList<DailyTask>();
		DailyTask DailyTask = dailyTaskService.loadDailyTask(dailyTaskId);
			if(DailyTask!=null &&!DailyTask.equals("")){
				list.add(DailyTask);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

	
}
