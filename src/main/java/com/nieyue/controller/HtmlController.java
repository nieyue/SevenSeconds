package com.nieyue.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nieyue.bean.Acount;
import com.nieyue.comments.IPCountUtil;
import com.nieyue.comments.RequestToMethdoItemUtils;
import com.nieyue.comments.RequestToMethodItem;
import com.nieyue.limit.RequestLimit;
import com.nieyue.sensitive.SensitivewordRedisFilter;
import com.nieyue.util.MacAddressUtil;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.MyPugin;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;
import com.nieyue.util.barcode.QRCodeUtil;
import com.nieyue.verification.VerificationCode;

import net.sf.json.JSONObject;


/**
 * Html控制类
 * @author yy
 *
 */
@RestController
public class HtmlController {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Resource
	MyPugin myPugin;
	@Resource
	VerificationCode verificationCode;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 设置全局合伙人收益比例增量
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/scaleIncrement/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updatescaleIncrement(
			HttpSession session,
			@RequestParam("scaleValue")String scaleValue){
		Acount acount = (Acount) session.getAttribute("acount");
		if(!acount.getRoleId().equals(1000)&&!acount.getRoleId().equals(1001)){
			return ResultUtil.getFail();
		}
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ScaleIncrement");
		bvo.getAndSet(scaleValue);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取全局合伙人收益比例增量
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/scaleIncrement/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getscaleIncrement(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ScaleIncrement");
		JSONObject jo=new JSONObject();
		jo.put("scaleIncrement", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 设置全分享页面域名 article.html (如：光明网)
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/shareDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateShareDomain(
			HttpSession session,
			@RequestParam("shareDomain")String shareDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		bvo.getAndSet(shareDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取全分享页面域名 article.html
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/shareDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getShareDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		JSONObject jo=new JSONObject();
		jo.put("shareDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 设置跨域广告域名 ad.html 
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/adDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateAdDomain(
			HttpSession session,
			@RequestParam("adDomain")String adDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"AdDomain");
		bvo.getAndSet(adDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取跨域广告域名 ad.html 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/adDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getAdDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"AdDomain");
		JSONObject jo=new JSONObject();
		jo.put("adDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 获取光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/list", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getGmwDomain(
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/add", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList addGmwDomain(
			@RequestParam("value") String value,
			@RequestParam(value="score",required=false,defaultValue="1") double score,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		bzso.add(value, score);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/del", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList delGmwDomain(
			@RequestParam("value") String value,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		bzso.remove(value);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	
	/**
	 * 设置三俗页面域名 article.html 
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/ssDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateSsDomain(
			HttpSession session,
			@RequestParam("ssDomain")String ssDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"SsDomain");
		bvo.getAndSet(ssDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取三俗页面域名 article.html
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/ssDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getSsDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"SsDomain");
		JSONObject jo=new JSONObject();
		jo.put("ssDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 修改黑名单 acountId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/blackIp/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList updateBlackIP(
			@RequestParam("acountId")Integer acountId,
			@RequestParam("ip")String ip,
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"BlackIp"+acountId);
		bvo.getAndSet(ip);
		list.add(bvo.get());
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 修改黑名单 acountId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/blackIp/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getBlackIP(
			@RequestParam("acountId")Integer acountId,
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"BlackIp"+acountId);
		list.add(bvo.get());
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 验证码
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestLimit(count=3,time=1000)
	@RequestMapping(value="/getVerificationCode", method = {RequestMethod.GET,RequestMethod.POST})
	public void getVerificationCode(
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
			ByteArrayOutputStream vc = verificationCode.execute(session);
			response.getOutputStream().write(vc.toByteArray());
		
		return ;
	}
	
	/**
	 * 二维码
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestLimit(count=3,time=1000)
	@RequestMapping(value="/getBarcode", method = {RequestMethod.GET,RequestMethod.POST})
	public void getBarcode(
			@RequestParam("acountId")Integer acountId,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		String text = "http://"+bvo.get()+"/share.html?acountId="+acountId;
		QRCodeUtil.encode(text, response.getOutputStream());
		return ;
	}
	/**
	 * 二维码Url
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getBarcodeUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public String getBarcodeUrl(
			@RequestParam("acountId")Integer acountId,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		String text = "http://"+bvo.get()+"/share.html?acountId="+acountId;
		return text;
	}
	
	/**
	 * 获取js代码广告列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/list", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getJsAd(
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add js代码广告
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/add", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList addJsAd(
			@RequestParam("value") String value,
			@RequestParam(value="score",required=false,defaultValue="1") double score,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		bzso.add(value, score);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * del js代码广告
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/del", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList delJsAd(
			@RequestParam("value") String value,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		bzso.remove(value);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 获取敏感词列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sensitiveWord/list", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getSensitiveWord(
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundSetOperations<String, String> bzso=stringRedisTemplate.boundSetOps(projectName+"SensitiveWord");
		Set<String> l = bzso.members();
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add 敏感词
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sensitiveWord/add", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList addSensitiveWord(
			@RequestParam("word") String word,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundSetOperations<String, String> bzso=stringRedisTemplate.boundSetOps(projectName+"SensitiveWord");
		bzso.add(word);
		Set<String> l = bzso.members();
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add 敏感词
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/sensitiveWord/request", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList requestSensitiveWord(
			@RequestParam("sensitiveWordUpload") MultipartFile file,
			HttpSession session			
			) throws IOException{
		List<String> list = new ArrayList<String>();
		BoundSetOperations<String, String> bzso=stringRedisTemplate.boundSetOps(projectName+"SensitiveWord");
		InputStream in = file.getInputStream();
        InputStreamReader isr=new InputStreamReader(in);
        BufferedReader br=new BufferedReader(isr);
        //StringBuffer sb=new StringBuffer();
        while((br.readLine())!=null){
           // sb.append(br.readLine()); 
        	String txt = br.readLine();
        	if(txt.indexOf("=")>-1){
        	txt=txt.substring(0, txt.indexOf("="));
        	}else if(txt.indexOf("|")>-1){
        		txt=txt.substring(0, txt.indexOf("|"));
        	}else if(txt.indexOf("@")>-1){
        		txt=txt.substring(0, txt.indexOf("@"));
        	}else if(txt.indexOf("/n")>-1){
        		txt=txt.substring(0, txt.indexOf("/n"));
        	}
        	bzso.add(txt);
           // bzso.add(word);
        }
        br.close();
        isr.close();
        in.close();
		Set<String> l = bzso.members();
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	@Resource
	SensitivewordRedisFilter sensitivewordRedisFilter;
	/**
	 *有 敏感词?
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/sensitiveWord/haveSensitive", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList haveSensitiveWord(
			@RequestParam("content") String content,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		Set<String> set = sensitivewordRedisFilter.getSensitiveWord(content, 1);
		if(set.size()>0){
		list.add("敏感词"+set.size()+"个，包含"+set);
		return ResultUtil.getSlefSRList("40004", "敏感词", list);
		}
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * Html单个加载
	 * @return
	 */
	@RequestMapping(value={"/index","/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView index(Model model,HttpSession session){
		model.addAttribute("sessionId", session.getId());
		return new ModelAndView("index");
	}
	@RequestMapping(value={"/404"}, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView go404(){
		return new ModelAndView("404");
	}	

	@Value("${myPugin.location}")
	 String  location;
	@RequestMapping(value="/getlocation", method = {RequestMethod.GET,RequestMethod.POST})
	public String getlocation(){
		System.out.println(location);
		return this.location;
	}
	@RequestMapping(value="/getSession", method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			HttpSession session){
		return session.getId();
	}
	
	@Resource
	RequestToMethdoItemUtils requestToMethdoItemUtils;
	/**
	 * 获取API接口文档
	 * @return
	 */
	@RequestMapping(value={"/getAPI"}, method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getAPI(
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			@CookieValue("SESSION") String SESSION
			){
		List<RequestToMethodItem> requestToMethdoItemUtilsresult = requestToMethdoItemUtils.getRequestToMethodItemList(request);
	    /*for (int i = 0; i < requestToMethdoItemUtilsresult.size(); i++) {
			String controllerName = requestToMethdoItemUtilsresult.get(i).getControllerName();
			String methodName = requestToMethdoItemUtilsresult.get(i).getMethodName();
			String requestUrl = requestToMethdoItemUtilsresult.get(i).getRequestUrl();
			
		}*/
	    
		return ResultUtil.getSlefSRSuccessList(requestToMethdoItemUtilsresult);
	
	}
	
}
