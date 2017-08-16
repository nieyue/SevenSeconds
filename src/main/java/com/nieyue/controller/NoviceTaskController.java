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

import com.nieyue.bean.NoviceTask;
import com.nieyue.service.NoviceTaskService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 新手任务控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/noviceTask")
public class NoviceTaskController {
	@Resource
	private NoviceTaskService noviceTaskService;
	/**
	 * 新手任务分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingNoviceTask(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="novice_task_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<NoviceTask> list = new ArrayList<NoviceTask>();
			list= noviceTaskService.browsePagingNoviceTask(createDate,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 新手任务修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateNoviceTask(@ModelAttribute NoviceTask noviceTask,HttpSession session)  {
		boolean um = noviceTaskService.updateNoviceTask(noviceTask);
		return ResultUtil.getSR(um);
	}
	/**
	 * 新手任务增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addNoviceTask(@ModelAttribute NoviceTask noviceTask, HttpSession session) {
		boolean am = noviceTaskService.addNoviceTask(noviceTask);
		return ResultUtil.getSR(am);
	}
	/**
	 * 新手任务删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delNoviceTask(@RequestParam("noviceTaskId") Integer noviceTaskId,HttpSession session)  {
		boolean dm = noviceTaskService.delNoviceTask(noviceTaskId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 新手任务浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = noviceTaskService.countAll(createDate,acountId);
		return count;
	}
	/**
	 * 新手任务单个加载
	 * @return
	 */
	@RequestMapping(value = "/{noviceTaskId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadNoviceTask(@PathVariable("noviceTaskId") Integer noviceTaskId,HttpSession session)  {
		List<NoviceTask> list = new ArrayList<NoviceTask>();
		NoviceTask NoviceTask = noviceTaskService.loadNoviceTask(noviceTaskId);
			if(NoviceTask!=null &&!NoviceTask.equals("")){
				list.add(NoviceTask);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

	
}
