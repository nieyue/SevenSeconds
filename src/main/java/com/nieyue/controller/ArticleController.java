package com.nieyue.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nieyue.bean.Article;
import com.nieyue.bean.ArticleCate;
import com.nieyue.bean.ArticleDataDTO;
import com.nieyue.bean.ArticleDayDataDTO;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.comments.IPCountUtil;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.AcountService;
import com.nieyue.service.ArticleCateService;
import com.nieyue.service.ArticleService;
import com.nieyue.service.DataService;
import com.nieyue.util.DateUtil;
import com.nieyue.util.FileUploadUtil;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;
import com.nieyue.util.UploaderPath;



/**
 * 文章控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
	@Resource
	private ArticleService articleService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private DataService dataService;
	@Resource
	private AcountService acountService;
	@Resource
	private ArticleCateService articleCateService;
	@Resource
	private Sender sender;

	/**
	 * 点击文章获取 根据articleId、acountId、uv来统计数据
	 * @return
	 */
	/*@ApiOperation(value="点击文章获取", notes="根据articleId、acountId、uv来统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "acountId", value = "账户ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "uv", value = "uv,默认为0，1为新用户",defaultValue="0", required = false, dataType = "Integer")
    })*/
	@RequestMapping(value = "/click", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult clickArticle(
			@RequestParam(value="articleId") Integer articleId,
			@RequestParam(value="acountId") Integer acountId,
			@RequestParam(value="uv",defaultValue="0",required=false) Integer uv,
			HttpSession session,HttpServletRequest request)  {
			if(uv!=0 &&uv!=1){
				return ResultUtil.getFail();
			}
			DataRabbitmqDTO drd=new DataRabbitmqDTO();
			drd.setAcountId(acountId);
			drd.setArticleId(articleId);
			drd.setUv(uv);
			drd.setIp(IPCountUtil.getIpAddr(request));//请求的ip地址
			drd.setReadingNumber(0);//点击不计算阅读
			sender.sendArticleClick(drd);
		return ResultUtil.getSuccess();
	}
	/**
	 * 阅读文章获取 根据articleId、acountId、uv来统计数据
	 * @return
	 */
	@RequestMapping(value = "/read", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult readArticle(
			@RequestParam(value="articleId") Integer articleId,
			@RequestParam(value="acountId") Integer acountId,
			HttpSession session,HttpServletRequest request)  {
		DataRabbitmqDTO drd=new DataRabbitmqDTO();
		drd.setAcountId(acountId);
		drd.setArticleId(articleId);
		drd.setUv(0);
		drd.setReadingNumber(1);//算阅读，计费。
		sender.sendArticleRead(drd);
		return ResultUtil.getSuccess();
	}
	/**
	 * web阅读文章获取 根据articleId、acountId、uv来统计数据
	 * @return
	 */
	@RequestMapping(value = "/webRead", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult webClickReadArticle(
			@RequestParam(value="articleId") Integer articleId,
			@RequestParam(value="acountId") Integer acountId,
			@RequestParam(value="uv",defaultValue="0",required=false) Integer uv,
			HttpSession session,HttpServletRequest request)  {
		if(uv!=0 &&uv!=1){
			return ResultUtil.getFail();
		}
		DataRabbitmqDTO drd=new DataRabbitmqDTO();
		drd.setAcountId(acountId);//转发推广文章    10积分（获得3个有效阅读）
		drd.setArticleId(articleId);
		drd.setUv(uv);
		drd.setIp(IPCountUtil.getIpAddr(request));//请求的ip地址
		sender.sendArticleWebRead(drd);
		return ResultUtil.getSuccess();
	}
	/**
	 * app文章转发
	 * @param articleId 
	 * @param acountId 
	 * @return
	 */
	@RequestMapping(value = "/turn", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList appArticleTurn(
			@RequestParam(value="articleId") Integer articleId,
			@RequestParam(value="acountId") Integer acountId,
			HttpSession session,HttpServletRequest request)  {
	//存储当日的记录转发的文章
	BoundSetOperations<String, String> bsodataturn= stringRedisTemplate.boundSetOps(projectName+"AcountId"+acountId+"Data"+DateUtil.getImgDir()+"Turn");
	List<String> list=new ArrayList<String>();
	if(bsodataturn.members().size()<10){//10个
	bsodataturn.add(articleId.toString());
	bsodataturn.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);
	list.add(articleId.toString());
	return ResultUtil.getSlefSRSuccessList(list);
	}
	list.add("数量超过");
	return ResultUtil.getSlefSRFailList(list);
	}
	/**
	 * 文章分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	//@RequestLimit(count=10,time=100000)
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingArticle(HttpServletRequest request,
			@RequestParam(value="type",required=false)String type,//articleCate上的name
			@RequestParam(value="status",required=false)String status,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="articleCateId",required=false)Integer articleCateId,
			@RequestParam(value="isRecommend",required=false)Integer isRecommend,
			@RequestParam(value="fixedRecommend",required=false)Integer fixedRecommend,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<Article> list = new ArrayList<Article>();
			//根据类型名称查找
			if(type!=null &&!type.equals("")){
				List<ArticleCate> acl = articleCateService.browsePagingArticleCate(null, type, 1, Integer.MAX_VALUE, "article_cate_id", "asc");
				if(acl.size()!=1){//必须只有一位
					return ResultUtil.getSlefSRFailList(list);
				}
			list = articleService.browsePagingArticle(status, acountId, acl.get(0).getarticleCateId(), isRecommend, fixedRecommend, pageNum, pageSize, orderName, orderWay);
			return ResultUtil.getSlefSRSuccessList(list);
			}
			
			//不根据类型名查找
			list= articleService.browsePagingArticle(status,acountId,articleCateId,isRecommend,fixedRecommend,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
				
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 文章数据
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	//@RequestLimit(count=10,time=100000)
	@RequestMapping(value = "/data", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browseArticleData(HttpServletRequest request,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		List<ArticleDataDTO> list = new ArrayList<ArticleDataDTO>();
		list= articleService.browseArticleData(acountId);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
			
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	/**
	 * 文章日数据
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	//@RequestLimit(count=10,time=100000)
	@RequestMapping(value = "/daydata", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browseArticleDayData(
			HttpServletRequest request,
			@RequestParam(value="startDate",required=false)Date startDate,
			@RequestParam(value="endDate",required=false)Date endDate,
			HttpSession session)  {
		List<ArticleDayDataDTO> list = new ArrayList<ArticleDayDataDTO>();
		list= articleService.browseArticleDayData(startDate,endDate);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	/**
	 * 文章修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateArticle(@RequestBody  Article article,HttpSession session)  {
		boolean um = articleService.updateArticle(article);
		return ResultUtil.getSR(um);
	}
	/**
	 * 文章增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addArticle(@RequestBody Article article, HttpSession session) {
		boolean am = articleService.addArticle(article);
		return ResultUtil.getSR(am);
	}
	/**
	 * 文章删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delArticle(@RequestParam("articleId") Integer articleId,HttpSession session)  {
		boolean dm =articleService.delArticle(articleId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 文章浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="type",required=false)String type,//articleCate上的name
			@RequestParam(value="status",required=false)String status,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="articleCateId",required=false)Integer articleCateId,
			@RequestParam(value="isRecommend",required=false)Integer isRecommend,
			@RequestParam(value="fixedRecommend",required=false)Integer fixedRecommend,
			HttpSession session)  {
		int count=0;
		//根据类型名称查找
		if(type!=null &&!type.equals("")){
			List<ArticleCate> acl = articleCateService.browsePagingArticleCate(null, type, 1, Integer.MAX_VALUE, "article_cate_id", "asc");
			System.err.println(acl.get(0).getName());
			if(acl.size()!=1){//必须只有一位
				return count;
			}
			count = articleService.countAll(status, acountId, acl.get(0).getarticleCateId(), isRecommend, fixedRecommend);
			return count;
		}
		//不根据类型名查找
		count= articleService.countAll(status,acountId,articleCateId, isRecommend,fixedRecommend);
		return count;
	}
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 文章单个加载
	 * @return
	 */
	@RequestMapping(value = "/{articleId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadArticle(@PathVariable("articleId") Integer articleId,HttpSession session)  {
		List<Object> list = new ArrayList<Object>();
		Article article = articleService.loadArticle(articleId);
			if(article!=null &&!article.equals("")){
				list.add(article);
				Map<String,Object> map=new HashMap<String,Object>();
				BoundValueOperations<String, String> sharebvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
				BoundZSetOperations<String, String> gmwbzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
				BoundValueOperations<String, String> ssbvo=stringRedisTemplate.boundValueOps(projectName+"SsDomain");
				map.put("url", "http://"+sharebvo.get()+"/article.html?articleId="+article.getArticleId());
				Set<String> gmwset = gmwbzso.range(0, -1);
				List<String> gmwurllist=new ArrayList<String>();
				for (String gmw : gmwset) {
					gmwurllist.add("http://"+gmw+"/article.html?articleId="+article.getArticleId());
				}
				map.put("gmwurl", gmwurllist);
				map.put("ssurl", "http://"+ssbvo.get()+"/article.html?articleId="+article.getArticleId());
				map.put("date", Calendar.getInstance());
				list.add(map);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 图片增加、修改
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/img/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addAdvertiseImg(
			@RequestParam("testFileUpload") MultipartFile file,
			HttpSession session ) throws IOException  {
		String imgUrl = null;
		String imgdir=DateUtil.getImgDir();
		try{
			imgUrl = FileUploadUtil.FormDataMerImgFileUpload(file, session,UploaderPath.GetValueByKey(UploaderPath.ROOTPATH),UploaderPath.GetValueByKey(UploaderPath.IMG),imgdir);
		}catch (IOException e) {
			throw new IOException();
		}
		return imgUrl;
	}
	
}
