package com.nieyue.controller;

import java.util.ArrayList;
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

import com.nieyue.bean.Collect;
import com.nieyue.bean.CollectArticleDTO;
import com.nieyue.bean.Img;
import com.nieyue.service.CollectService;
import com.nieyue.service.ImgService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 收藏控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
	@Resource
	private CollectService collectService;
	@Resource
	private ImgService imgService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 收藏DTO分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/listDTO", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingCollectAcountDTO(
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="collect_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		List<CollectArticleDTO> list = new ArrayList<CollectArticleDTO>();
		list= collectService.browsePagingCollectArticleDTO(articleId,acountId,pageNum, pageSize, orderName, orderWay);
		for (int i = 0; i < list.size(); i++) {
			List<Img> imgList = imgService.browsePagingImg(list.get(i).getArticleId(), 1, 3, "img_id", "asc");
			list.get(i).setImgList(imgList);
		}
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	/**
	 * 收藏分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingCollect(
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="Collect_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Collect> list = new ArrayList<Collect>();
			list= collectService.browsePagingCollect(articleId,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 收藏修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateCollect(@ModelAttribute Collect collect,HttpSession session)  {
		boolean um = collectService.updateCollect(collect);
		return ResultUtil.getSR(um);
	}
	/**
	 * 收藏增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addCollect(@ModelAttribute Collect collect, HttpSession session) {
		if(collect.getAcountId()==null
			||collect.getAcountId().equals("")
				){
				return ResultUtil.getSR(false);
			}
		boolean am = collectService.addCollect(collect);
		return ResultUtil.getSR(am);
	}
	/**
	 * 收藏删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delCollect(
			@RequestParam("collectId") Integer collectId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		boolean dm = collectService.delCollect(collectId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 收藏浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="articleId",required=false)Integer articleId,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		int count = collectService.countAll(articleId,acountId);
		return count;
	}
	/**
	 * 收藏单个加载
	 * @return
	 */
	@RequestMapping(value = "/{collectId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadCollect(@PathVariable("collectId") Integer collectId,HttpSession session)  {
		List<Collect> list = new ArrayList<Collect>();
		Collect collect = collectService.loadCollect(null,null,collectId);
			if(collect!=null &&!collect.equals("")){
				list.add(collect);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
