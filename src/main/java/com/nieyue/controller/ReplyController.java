package com.nieyue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Reply;
import com.nieyue.bean.ReplyAcountDTO;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.service.ReplyService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import net.sf.json.JSONObject;


/**
 * 回复控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Resource
	private ReplyService replyService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SensitivewordRedisFilter sensitivewordRedisFilter;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 回复DTO分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/listDTO", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingReplyAcountDTO(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="commentId",required=false)Integer commentId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="reply_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<ReplyAcountDTO> list = new ArrayList<ReplyAcountDTO>();
		list= replyService.browsePagingReplyAcountDTO(pointNumber,commentId,acountId,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	/**
	 * 回复分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingReply(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="commentId",required=false)Integer commentId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="reply_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Reply> list = new ArrayList<Reply>();
			list= replyService.browsePagingReply(pointNumber,commentId,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 回复修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateReply(@ModelAttribute Reply reply,HttpSession session)  {
		boolean um = replyService.updateReply(reply);
		return ResultUtil.getSR(um);
	}
	/**
	 * 回复增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addReply(@ModelAttribute Reply reply, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(reply.getContent()==null
				||reply.getContent().isEmpty()
				||reply.getAcountId()==null
				||reply.getAcountId().equals("")
				||reply.getCommentId()==null
				||reply.getCommentId().equals("")
				){
				return ResultUtil.getSlefSRFailList(list);
			}
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(reply.getContent(), 1);
		if(set.size()>0){
			list.add("敏感词"+set.size()+"个，包含"+set);
			return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		//boolean am = replyService.addReply(reply);
		sender.sendReply(reply);
		return ResultUtil.getSlefSRList(true, list);
	}
	/**
	 * 回复删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delReply(@RequestParam("replyId") Integer replyId,HttpSession session)  {
		boolean dm = replyService.delReply(replyId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 回复浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="commentId",required=false)Integer commentId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = replyService.countAll(pointNumber,commentId,acountId);
		return count;
	}
	/**
	 * 回复单个加载
	 * @return
	 */
	@RequestMapping(value = "/{replyId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadReply(@PathVariable("replyId") Integer replyId,HttpSession session)  {
		List<Reply> list = new ArrayList<Reply>();
		Reply reply = replyService.loadReply(replyId);
			if(reply!=null &&!reply.equals("")){
				list.add(reply);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 点赞
	 * @return
	 */
	@RequestMapping(value = "/point", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList pointReply(
			@RequestParam("replyId") Integer replyId,
			@RequestParam("commentId") Integer commentId,
			@RequestParam("acountId") Integer acountId,
			HttpSession session)  {
		List<JSONObject> list = new ArrayList<JSONObject>();
		Reply reply = replyService.loadReply(replyId);
		BoundSetOperations<String, String> an = stringRedisTemplate.boundSetOps(projectName+"PointReply"+replyId);
		long oldsize=an.size();
		an.add(String.valueOf(acountId));
		long newsize=an.size();
		JSONObject json = new JSONObject();
		if(newsize>oldsize){
			reply.setPointNumber(reply.getPointNumber()+1);
			replyService.updateReply(reply);
			json.put("isPoint", 1);
		}else{
			json.put("isPoint", 0);
			
		}
		list.add(json);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	
}
