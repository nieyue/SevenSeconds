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

import com.nieyue.bean.Barrage;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.service.BarrageService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 弹幕控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/barrage")
public class BarrageController {
	@Resource
	private BarrageService barrageService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SensitivewordRedisFilter sensitivewordRedisFilter;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 弹幕分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingBarrage(
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="complainNumber",required=false)Integer complainNumber,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="Barrage_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Barrage> list = new ArrayList<Barrage>();
			list= barrageService.browsePagingBarrage(articleId,acountId,createDate,updateDate,complainNumber,status,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 弹幕修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateBarrage(@ModelAttribute Barrage barrage,HttpSession session)  {
		boolean um = barrageService.updateBarrage(barrage);
		return ResultUtil.getSR(um);
	}
	/**
	 * 弹幕增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addBarrage(@ModelAttribute Barrage barrage, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(barrage.getContent()==null
			||barrage.getContent().isEmpty()
			||barrage.getAcountId()==null
			||barrage.getAcountId().equals("")
			||barrage.getArticleId()==null
			||barrage.getArticleId().equals("")
			){
			return ResultUtil.getSlefSRFailList(list);
		}
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(barrage.getContent(), 1);
		if(set.size()>0){
			list.add("敏感词"+set.size()+"个，包含"+set);
			return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		sender.sendBarrage(barrage);
		return ResultUtil.getSlefSRList(true, list);
	}
	/**
	 * 弹幕删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delBarrage(
			@RequestParam("barrageId") Integer barrageId,HttpSession session)  {
		boolean dm = barrageService.delBarrage(barrageId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 弹幕浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="complainNumber",required=false)Integer complainNumber,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		int count = barrageService.countAll(articleId,acountId,createDate,updateDate,complainNumber,status);
		return count;
	}
	/**
	 * 弹幕单个加载
	 * @return
	 */
	@RequestMapping(value = "/{barrageId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadBarrage(@PathVariable("barrageId") Integer barrageId,HttpSession session)  {
		List<Barrage> list = new ArrayList<Barrage>();
		Barrage barrage = barrageService.loadBarrage(barrageId);
			if(barrage!=null &&!barrage.equals("")){
				list.add(barrage);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

}
