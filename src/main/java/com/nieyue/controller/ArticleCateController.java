package com.nieyue.controller;

import java.util.ArrayList;
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

import com.nieyue.bean.ArticleCate;
import com.nieyue.service.ArticleCateService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 文章类型控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/articleCate")
public class ArticleCateController {
	@Resource
	private ArticleCateService articleCateService;
	
	/**
	 * 文章类型分页浏览
	 * @param orderName 排序数据库字段
	 * @param orderWay 排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingArticleCate(
			@RequestParam(value="holder",required=false)Integer holder,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="article_cate_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<ArticleCate> list = new ArrayList<ArticleCate>();
			list= articleCateService.browsePagingArticleCate(holder,name,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
				
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 文章类型修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateArticleCate(@ModelAttribute ArticleCate articleCate,HttpSession session)  {
		boolean um = articleCateService.updateArticleCate(articleCate);
		return ResultUtil.getSR(um);
	}
	/**
	 * 文章类型增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addArticleCate(@ModelAttribute ArticleCate articleCate, HttpSession session) {
		boolean am = articleCateService.addArticleCate(articleCate);
		return ResultUtil.getSR(am);
	}
	/**
	 * 文章类型删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delArticleCate(
			@RequestParam("articleCateId") Integer articleCateId,HttpSession session)  {
		boolean dm =articleCateService.delArticleCate(articleCateId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 文章类型浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="holder",required=false)Integer holder,
			@RequestParam(value="name",required=false)String name,
			HttpSession session)  {
		int count = articleCateService.countAll(holder,name);
		return count;
	}
	/**
	 * 文章类型单个加载
	 * @return
	 */
	@RequestMapping(value = "/{articleCateId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadArticleCate(@PathVariable("articleCateId") Integer articleCateId,HttpSession session)  {
		List<ArticleCate> list = new ArrayList<ArticleCate>();
		ArticleCate articleCate = articleCateService.loadArticleCate(articleCateId);
			if(articleCate!=null &&!articleCate.equals("")){
				list.add(articleCate);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
