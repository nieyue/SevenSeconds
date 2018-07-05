package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.nieyue.bean.Comment;
import com.nieyue.bean.CommentAcountDTO;
import com.nieyue.bean.Finance;
import com.nieyue.bean.FlowWater;
import com.nieyue.business.DailyTaskBusiness;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.service.CommentService;
import com.nieyue.service.FinanceService;
import com.nieyue.service.FlowWaterService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import net.sf.json.JSONObject;


/**
 * 评论控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Resource
	private CommentService commentService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private SensitivewordRedisFilter sensitivewordRedisFilter;
	@Resource
	private Sender sender;
	@Resource
	private FinanceService financeService;
	@Resource
	private DailyTaskBusiness dailyTaskBusiness;
	@Resource
	private FlowWaterService flowWaterService;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 评论DTO分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/listDTO", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingCommentAcountDTO(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="replyNumber",required=false)Integer replyNumber,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="comment_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<CommentAcountDTO> list = new ArrayList<CommentAcountDTO>();
		list= commentService.browsePagingCommentAcountDTO(pointNumber,replyNumber,articleId,acountId,pageNum, pageSize, orderName, orderWay);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	/**
	 * 评论分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingComment(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="replyNumber",required=false)Integer replyNumber,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="comment_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Comment> list = new ArrayList<Comment>();
			list= commentService.browsePagingComment(pointNumber,replyNumber,articleId,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 评论修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateComment(@ModelAttribute Comment comment,HttpSession session)  {
		boolean um = commentService.updateComment(comment);
		return ResultUtil.getSR(um);
	}
	/**
	 * 评论增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addComment(@ModelAttribute Comment comment, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(comment.getContent()==null
			||comment.getContent().isEmpty()
			||comment.getAcountId()==null
			||comment.getAcountId().equals("")
			||comment.getArticleId()==null
			||comment.getArticleId().equals("")
			){
			return ResultUtil.getSlefSRFailList(list);
		}
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(comment.getContent(), 1);
		if(set.size()>0){
			list.add("敏感词"+set.size()+"个，包含"+set);
			return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		sender.sendComment(comment);
		return ResultUtil.getSlefSRList(true, list);
	}
	/**
	 * 评论删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delComment(
			@RequestParam("commentId") Integer commentId,HttpSession session)  {
		boolean dm = commentService.delComment(commentId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 评论浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="pointNumber",required=false)Integer pointNumber,
			@RequestParam(value="replyNumber",required=false)Integer replyNumber,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = commentService.countAll(pointNumber,replyNumber,articleId,acountId);
		return count;
	}
	/**
	 * 评论单个加载
	 * @return
	 */
	@RequestMapping(value = "/{commentId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadComment(@PathVariable("commentId") Integer commentId,HttpSession session)  {
		List<Comment> list = new ArrayList<Comment>();
		Comment comment = commentService.loadComment(commentId);
			if(comment!=null &&!comment.equals("")){
				list.add(comment);
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
	public  StateResultList pointComment(
			@RequestParam("commentId") Integer commentId,
			@RequestParam("articleId") Integer articleId,
			@RequestParam("acountId") Integer acountId,
			HttpSession session)  {
		List<JSONObject> list = new ArrayList<JSONObject>();
		Comment comment = commentService.loadComment(commentId);
		BoundSetOperations<String, String> an = stringRedisTemplate.boundSetOps(projectName+"PointComment"+commentId);
		long oldsize=an.size();
		an.add(String.valueOf(acountId));
		long newsize=an.size();
		JSONObject json = new JSONObject();
		if(newsize>oldsize){
			comment.setPointNumber(comment.getPointNumber()+1);
			commentService.updateComment(comment);
			/*if(comment.getPointNumber()==20){//优质评论        30积分（点赞20个）
				List<Finance> financelist = financeService.browsePagingFinance(null,comment.getAcountId(), 1, 1, "finance_id", "asc");
	        	Finance selfFinance = financelist.get(0);
        	   //记录流水，阅读文章收益
        	   FlowWater flowWater = new FlowWater();
        	   flowWater.setAcountId(comment.getAcountId());
        	   flowWater.setCreateDate(new Date());
        	   flowWater.setMoney(dailyTaskBusiness.qualityComment(comment.getPointNumber()));
        	   flowWater.setType(3);//3达人奖励
        	   flowWater.setSubtype(6);//优质评论        30积分（点赞20个）
        	   flowWaterService.addFlowWater(flowWater);
        	   //自身总收益增加
        	   selfFinance.setSelfProfit(selfFinance.getSelfProfit()+dailyTaskBusiness.qualityComment(comment.getPointNumber()));
        	   //余额=增加
        	   selfFinance.setMoney(selfFinance.getMoney()+dailyTaskBusiness.qualityComment(comment.getPointNumber()));
        	   financeService.updateFinance(selfFinance);
			}*/
			json.put("isPoint", 1);
		}else{
			json.put("isPoint", 0);
			
		}
		list.add(json);
			return ResultUtil.getSlefSRSuccessList(list);
	}
	
}
