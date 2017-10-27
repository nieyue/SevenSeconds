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

import com.nieyue.bean.Complain;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.service.ComplainService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 投诉控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/complain")
public class ComplainController {
	@Resource
	private ComplainService complainService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SensitivewordRedisFilter sensitivewordRedisFilter;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 投诉分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingComplain(
			@RequestParam(value="barrageId",required=false)Integer barrageId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="Complain_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Complain> list = new ArrayList<Complain>();
			list= complainService.browsePagingComplain(barrageId,acountId,createDate,updateDate,type,status,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 投诉修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateComplain(@ModelAttribute Complain complain,HttpSession session)  {
		boolean um = complainService.updateComplain(complain);
		return ResultUtil.getSR(um);
	}
	/**
	 * 投诉增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addComplain(@ModelAttribute Complain complain, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(complain.getContent()==null
			||complain.getContent().isEmpty()
			||complain.getAcountId()==null
			||complain.getAcountId().equals("")
			||complain.getBarrageId()==null
			||complain.getBarrageId().equals("")
			){
			return ResultUtil.getSlefSRFailList(list);
		}
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(complain.getContent(), 1);
		if(set.size()>0){
			list.add("敏感词"+set.size()+"个，包含"+set);
			return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		sender.sendComplain(complain);
		return ResultUtil.getSlefSRList(true, list);
	}
	/**
	 * 投诉删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delComplain(
			@RequestParam("complainId") Integer complainId,HttpSession session)  {
		boolean dm = complainService.delComplain(complainId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 投诉浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="barrageId",required=false)Integer barrageId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		int count = complainService.countAll(barrageId,acountId,createDate,updateDate,type,status);
		return count;
	}
	/**
	 * 投诉单个加载
	 * @return
	 */
	@RequestMapping(value = "/{complainId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadComplain(@PathVariable("complainId") Integer complainId,HttpSession session)  {
		List<Complain> list = new ArrayList<Complain>();
		Complain complain = complainService.loadComplain(complainId);
			if(complain!=null &&!complain.equals("")){
				list.add(complain);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

}
