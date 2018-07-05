package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.nieyue.bean.LabelArticle;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.LabelArticleService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 标签文章控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/labelArticle")
public class LabelArticleController {
	@Resource
	private LabelArticleService labelArticleService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 标签文章分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingLabelArticle(
			@RequestParam(value="labelId",required=false)Integer labelId,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="label_article_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<LabelArticle> list = new ArrayList<LabelArticle>();
			list= labelArticleService.browsePagingLabelArticle(labelId,articleId,createDate,updateDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 标签文章修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateLabelArticle(@ModelAttribute LabelArticle labelArticle,HttpSession session)  {
		boolean um = labelArticleService.updateLabelArticle(labelArticle);
		return ResultUtil.getSR(um);
	}
	/**
	 * 标签文章增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList addLabelArticle(@ModelAttribute LabelArticle labelArticle, HttpSession session) {
		List<String> list=new ArrayList<String>();
		if(labelArticle.getLabelId()==null
			||labelArticle.getLabelId().equals("")
			||labelArticle.getArticleId()==null
			||labelArticle.getArticleId().equals("")
			){
			return ResultUtil.getSlefSRFailList(list);
		}
		boolean b = labelArticleService.addLabelArticle(labelArticle);
		return ResultUtil.getSlefSRList(b, list);
	}
	/**
	 * 标签文章删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delLabelArticle(
			@RequestParam("labelArticleId") Integer labelArticleId,HttpSession session)  {
		boolean dm = labelArticleService.delLabelArticle(labelArticleId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 标签文章浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="labelId",required=false)Integer labelId,
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		int count = labelArticleService.countAll(labelId,articleId,createDate,updateDate);
		return count;
	}
	/**
	 * 标签文章单个加载
	 * @return
	 */
	@RequestMapping(value = "/{labelArticleId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadLabelArticle(@PathVariable("labelArticleId") Integer labelArticleId,HttpSession session)  {
		List<LabelArticle> list = new ArrayList<LabelArticle>();
		LabelArticle labelArticle = labelArticleService.loadLabelArticle(labelArticleId);
			if(labelArticle!=null &&!labelArticle.equals("")){
				list.add(labelArticle);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}

}
