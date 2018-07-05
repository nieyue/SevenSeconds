package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Label;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.service.LabelService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 标签控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/label")
public class LabelController {
	@Resource
	private LabelService labelService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SensitivewordRedisFilter sensitivewordRedisFilter;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 标签分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingLabel(
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="label_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Label> list = new ArrayList<Label>();
			list= labelService.browsePagingLabel(type,name,createDate,updateDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 标签修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateLabel(@ModelAttribute Label label,HttpSession session)  {
		boolean um = labelService.updateLabel(label);
		return ResultUtil.getSR(um);
	}
	/**
	 * 标签增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addLabel(@ModelAttribute Label label, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(label.getType()==null
			||label.getType().isEmpty()
			||label.getName()==null
			||label.getName().equals("")
			){
			return ResultUtil.getSlefSRFailList(list);
		}
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(label.getName(), 1);
		if(set.size()>0){
			list.add("敏感词"+set.size()+"个，包含"+set);
			return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		boolean b = labelService.addLabel(label);
		return ResultUtil.getSlefSRList(b, list);
	}
	/**
	 * 标签删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delLabel(
			@RequestParam("labelId") Integer labelId,HttpSession session)  {
		boolean dm = labelService.delLabel(labelId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 标签浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		int count = labelService.countAll(type,name,createDate,updateDate);
		return count;
	}
	/**
	 * 标签单个加载
	 * @return
	 */
	@RequestMapping(value = "/{labelId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadLabel(@PathVariable("labelId") Integer labelId,HttpSession session)  {
		List<Label> list = new ArrayList<Label>();
		Label label = labelService.loadLabel(labelId);
			if(label!=null &&!label.equals("")){
				list.add(label);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

}
