package com.nieyue.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nieyue.bean.Acount;
import com.nieyue.bean.Finance;
import com.nieyue.bean.Role;
import com.nieyue.business.CertificateBusiness;
import com.nieyue.exception.MyCertificateException;
import com.nieyue.exception.MySessionException;
import com.nieyue.util.MyDESutil;

/**
 * 用户session控制拦截器
 * @author yy
 *
 */
@Configuration
public class SessionControllerInterceptor implements HandlerInterceptor {

	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		
		//如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
        	System.out.println(handler);
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
       System.out.println(method.getDefaultValue());
        
        //自定义token
//        if(method.getName().equals("loginAdmin")||method.getName().equals("isloginAdmin")||method.getName().equals("tokenAdmin")){
//        	return true;
//        }else if (manager.checkToken("XuDeOAadmin", manager.getToken("XuDeOAadmin", request), request, response)) {
//        	//验证token成功
//            return true;
//        }
       
       //天窗
       if(MyDESutil.getMD5("1000").equals(request.getParameter("auth"))){
       	return true;
       }
       
        Acount sessionAcount = null;
        Role sessionRole=null;
        Finance sessionFinance=null;
        if(request.getSession()!=null
        		&&request.getSession().getAttribute("acount")!=null
        		&&request.getSession().getAttribute("role")!=null
        		&&request.getSession().getAttribute("finance")!=null
        		){
        sessionAcount = (Acount) request.getSession().getAttribute("acount");
        sessionRole = (Role) request.getSession().getAttribute("role");
         sessionFinance = (Finance) request.getSession().getAttribute("finance");
        }
        
       if(request.getRequestURI().indexOf("getSession")>-1){
        	return true;
        }
//       if(1==1){
//    	   return true;
//       }
        if(
        		request.getServletPath().equals("/")
        		||request.getRequestURI().indexOf("swagger")>-1
        		||request.getRequestURI().indexOf("api-docs")>-1
        		||request.getRequestURI().indexOf("getAPI")>-1
        		||request.getRequestURI().indexOf("validCode")>-1
        		||request.getRequestURI().indexOf("scaleIncrement")>-1
        		||request.getRequestURI().indexOf("blackIp")>-1
        		||request.getRequestURI().indexOf("Domain")>-1
        		||request.getRequestURI().indexOf("jsAd")>-1
        		||request.getRequestURI().indexOf("sensitiveWord")>-1
        		//二维码、验证码、404、
        		||request.getRequestURI().indexOf("getVerificationCode")>-1
        		||request.getRequestURI().indexOf("getBarcode")>-1
        		||request.getRequestURI().indexOf("getBarcodeUrl")>-1
        		||request.getRequestURI().indexOf("getlocation")>-1
        		||request.getRequestURI().indexOf("404")>-1
        		//登陆、登出、增加师傅徒弟关系
        		||request.getRequestURI().indexOf("acount/count")>-1
        		||request.getRequestURI().indexOf("acount/addMasterId")>-1
        		||request.getRequestURI().indexOf("register")>-1
        		||request.getRequestURI().indexOf("login")>-1
        		||request.getRequestURI().indexOf("acount/updatePassword")>-1
        		||request.getRequestURI().indexOf("acount/listGroupByMasterId")>-1
        		||request.getRequestURI().indexOf("acount/data")>-1
        		//版本
        		||request.getRequestURI().indexOf("appVersion/count")>-1
        		||request.getRequestURI().indexOf("appVersion/list")>-1
        		||method.getName().equals("loadAppVersion")
        		//articleCate
        		||request.getRequestURI().indexOf("articleCate/count")>-1
        		||request.getRequestURI().indexOf("articleCate/list")>-1
        		||method.getName().equals("loadArticleCate")
        		//article
        		||request.getRequestURI().indexOf("article/count")>-1
        		||(request.getRequestURI().indexOf("article/click")>-1&& CertificateBusiness.md5SessionCertificate(request))
        		||(request.getRequestURI().indexOf("article/read")>-1&& CertificateBusiness.md5SessionCertificate(request))
        		||(request.getRequestURI().indexOf("article/turn")>-1&& CertificateBusiness.md5SessionCertificate(request))
        		||request.getRequestURI().indexOf("article/webRead")>-1
        		||request.getRequestURI().indexOf("article/list")>-1
        		//||request.getRequestURI().indexOf("article/data")>-1
        		||method.getName().equals("loadArticle")
        		||request.getRequestURI().indexOf("article/img/add")>-1
        		//barrage弹幕
        		||request.getRequestURI().indexOf("barrage/count")>-1
        		||request.getRequestURI().indexOf("barrage/list")>-1
        		||method.getName().equals("loadBarrage")
        		//complain投诉
        		||request.getRequestURI().indexOf("complain/count")>-1
        		||request.getRequestURI().indexOf("complain/list")>-1
        		||method.getName().equals("loadComplain")
        		//label标签
        		||request.getRequestURI().indexOf("label/count")>-1
        		||request.getRequestURI().indexOf("label/list")>-1
        		||method.getName().equals("loadLabel")
        		//labelArticle标签文章
        		||request.getRequestURI().indexOf("labelArticle/count")>-1
        		||request.getRequestURI().indexOf("labelArticle/list")>-1
        		||method.getName().equals("loadLabelArticle")
        		//dailyData
        		||request.getRequestURI().indexOf("dailyData/count")>-1
        		||request.getRequestURI().indexOf("dailyData/statisticsDailyData")>-1
        		||request.getRequestURI().indexOf("dailyData/list")>-1
        		||method.getName().equals("loadDailyData")
        		//data
        		||request.getRequestURI().indexOf("data/count")>-1
        		||request.getRequestURI().indexOf("data/statisticsData")>-1
        		||request.getRequestURI().indexOf("data/list")>-1
        		||method.getName().equals("loadData")
        		//feedback
        		||request.getRequestURI().indexOf("feedback/count")>-1
        		||request.getRequestURI().indexOf("feedback/list")>-1
        		||method.getName().equals("loadFeedback")
        		//finance
        		||request.getRequestURI().indexOf("finance/count")>-1
        		||request.getRequestURI().indexOf("finance/listFinanceByAcountId")>-1
        		||request.getRequestURI().indexOf("finance/today")>-1
        		//financeDetails
        		||request.getRequestURI().indexOf("financeDetails/count")>-1
        		||request.getRequestURI().indexOf("financeDetails/list")>-1
        		||request.getRequestURI().indexOf("financeDetails/acountId")>-1
        		//img
        		||request.getRequestURI().indexOf("img/count")>-1
        		||request.getRequestURI().indexOf("img/list")>-1
        		||method.getName().equals("loadImg")
        		//notice
        		||request.getRequestURI().indexOf("notice/count")>-1
        		||request.getRequestURI().indexOf("notice/list")>-1
        		||method.getName().equals("loadNotice")
        		//role
        		||request.getRequestURI().indexOf("role/count")>-1
        		//school
        		||request.getRequestURI().indexOf("school/count")>-1
        		||request.getRequestURI().indexOf("school/list")>-1
        		||method.getName().equals("loadSchool")
        		//spread
        		||request.getRequestURI().indexOf("spread/count")>-1
        		||request.getRequestURI().indexOf("spread/list")>-1
        		||method.getName().equals("loadSpread")
        		//comment
        		||request.getRequestURI().indexOf("comment/count")>-1
        		||request.getRequestURI().indexOf("comment/list")>-1
        		||request.getRequestURI().indexOf("comment/point")>-1
        		||method.getName().equals("loadComment")
        		//reply
        		||request.getRequestURI().indexOf("reply/count")>-1
        		||request.getRequestURI().indexOf("reply/list")>-1
        		||request.getRequestURI().indexOf("reply/point")>-1
        		||method.getName().equals("loadReply")
        		//collect
        		||request.getRequestURI().indexOf("collect/count")>-1
        		||request.getRequestURI().indexOf("collect/list")>-1
        		||method.getName().equals("loadCollect")
        		//sign
        		||request.getRequestURI().indexOf("sign/count")>-1
        		||request.getRequestURI().indexOf("sign/list")>-1
        		||method.getName().equals("loadSign")
        		//noviceTask
        		||request.getRequestURI().indexOf("noviceTask/count")>-1
        		||request.getRequestURI().indexOf("noviceTask/list")>-1
        		||method.getName().equals("loadNoviceTask")
        		//dailyTask
        		||request.getRequestURI().indexOf("dailyTask/count")>-1
        		||request.getRequestURI().indexOf("dailyTask/list")>-1
        		||method.getName().equals("loadDailyTask")
        		//flowWater
        		||request.getRequestURI().indexOf("flowWater/count")>-1
        		
        		
       
        		){
        	return true;
        }else if (sessionAcount!=null) {
        	//确定角色存在
        	if(sessionRole!=null ){
        	//超级管理员
        	if(sessionRole.getName().equals("超级管理员")
        			||sessionRole.getName().equals("运营管理员")
        			||sessionRole.getName().equals("编辑管理员")
        			){
        		return true;
        	}
        	//admin中只许修改自己的值
        	if(sessionRole.getName().equals("用户")){
        		//证书认证
        		if((request.getRequestURI().indexOf("delete")>-1 
        				||request.getRequestURI().indexOf("add")>-1
        				||request.getRequestURI().indexOf("update")>-1 )
        				&& !CertificateBusiness.md5SessionCertificate(request)){
        			throw new MyCertificateException();
        		}
        		
        		//账户不许删除/增加
        		if( request.getRequestURI().indexOf("/acount/delete")>-1 
        				|| request.getRequestURI().indexOf("/acount/add")>-1
        				|| request.getRequestURI().equals("/acount/list")
        				|| request.getRequestURI().indexOf("/acount/update")>-1
        				||method.getName().equals("loadAcount")
        				){
        			//加载自身账户
        			if((	method.getName().equals("loadAcount")
        					|| request.getRequestURI().indexOf("/acount/list")>-1
        					)
        					&& request.getRequestURI().indexOf(sessionAcount.getAcountId().toString())>-1){
        				return true;
        			}
        			//获取合伙人
        			if((request.getRequestURI().indexOf("/acount/list")>-1)
        					&& request.getParameter("masterId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			//不能修改全部
        			if(request.getRequestURI().equals("/acount/update")){
        				throw new MySessionException();
        			}
        			//提交修改自身信息
        			if((request.getRequestURI().indexOf("/acount/update")>-1)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//意见反馈只许增加
        		if( request.getRequestURI().indexOf("/feedback/delete")>-1 
        				|| request.getRequestURI().indexOf("/feedback/update")>-1){
        			throw new MySessionException();
        		}
        		//财务不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/finance/delete")>-1 
        				|| request.getRequestURI().indexOf("/finance/update")>-1 
        				|| request.getRequestURI().indexOf("/finance/list")>-1 
        				|| request.getRequestURI().indexOf("/finance/add")>-1 
        				|| request.getRequestURI().indexOf("/finance/data")>-1 
        				|| request.getRequestURI().indexOf("/finance/daydata")>-1 
        				||method.getName().equals("loadFinance")){
        			//加载自身财务
        			if((method.getName().equals("loadFinance")
        					&& request.getRequestURI().indexOf(sessionFinance.getFinanceId().toString())>-1)){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//版本不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/appVersion/delete")>-1 
        				|| request.getRequestURI().indexOf("/appVersion/update")>-1 
        				|| request.getRequestURI().indexOf("/appVersion/add")>-1){
        			throw new MySessionException();
        		}
        		//文章类型不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/articleCate/delete")>-1 
        				|| request.getRequestURI().indexOf("/articleCate/update")>-1 
        				|| request.getRequestURI().indexOf("/articleCate/add")>-1){
        			throw new MySessionException();
        		}
        		//文章不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/article/delete")>-1 
        				|| request.getRequestURI().indexOf("/article/update")>-1 
        				|| request.getRequestURI().indexOf("/article/add")>-1){
        			throw new MySessionException();
        		}
        		//弹幕不许删除/修改
        		if( request.getRequestURI().indexOf("/barrage/delete")>-1 
        				|| request.getRequestURI().indexOf("/barrage/update")>-1 
        				|| request.getRequestURI().indexOf("/barrage/add")>-1){
        			//增加自身
        			if((request.getRequestURI().indexOf("/barrage/add")>-1)
        					&& request.getParameter("acountId").equals(sessionFinance.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//弹幕投诉不许删除/修改
        		if( request.getRequestURI().indexOf("/complain/delete")>-1 
        				|| request.getRequestURI().indexOf("/complain/update")>-1 
        				|| request.getRequestURI().indexOf("/complain/add")>-1){
        			//增加自身
        			if((request.getRequestURI().indexOf("/complain/add")>-1)
        					&& request.getParameter("acountId").equals(sessionFinance.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//标签不许删除/修改
        		if( request.getRequestURI().indexOf("/label/delete")>-1 
        				|| request.getRequestURI().indexOf("/label/update")>-1 
        				|| request.getRequestURI().indexOf("/label/add")>-1){
        			throw new MySessionException();
        		}
        		//标签文章不许删除/修改
        		if( request.getRequestURI().indexOf("/labelArticle/delete")>-1 
        				|| request.getRequestURI().indexOf("/labelArticle/update")>-1 
        				|| request.getRequestURI().indexOf("/labelArticle/add")>-1){
        			throw new MySessionException();
        		}
        		//文章时间段数据不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/data/delete")>-1 
        				|| request.getRequestURI().indexOf("/data/update")>-1 
        				|| request.getRequestURI().indexOf("/data/add")>-1){
        			throw new MySessionException();
        		}
        		//文章日数据不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/dailyData/delete")>-1 
        				|| request.getRequestURI().indexOf("/dailyData/update")>-1 
        				|| request.getRequestURI().indexOf("/dailyData/add")>-1){
        			throw new MySessionException();
        		}
        		
        		//提现、充值不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/financeDetails/delete")>-1 
        				|| request.getRequestURI().indexOf("/financeDetails/add")>-1
        				|| request.getRequestURI().indexOf("/financeDetails/update")>-1 ){
        			//增加自身
        			if((request.getRequestURI().indexOf("/financeDetails/add")>-1)
        					&& request.getParameter("financeId").equals(sessionFinance.getFinanceId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//图片不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/img/delete")>-1 
        				|| request.getRequestURI().indexOf("/img/update")>-1 
        				|| request.getRequestURI().indexOf("/img/add")>-1){
        			throw new MySessionException();
        		}
        		//公告不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/notice/delete")>-1 
        				|| request.getRequestURI().indexOf("/notice/update")>-1 
        				|| request.getRequestURI().indexOf("/notice/add")>-1){
        			throw new MySessionException();
        		}
        		//角色全不许
        		if( request.getRequestURI().indexOf("/role")>-1 ){
        			throw new MySessionException();
        		}
        		//学堂不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/school/delete")>-1 
        				|| request.getRequestURI().indexOf("/school/update")>-1 
        				|| request.getRequestURI().indexOf("/school/add")>-1){
        			throw new MySessionException();
        		}
        		//评论不许删除/修改
        		if( request.getRequestURI().indexOf("/comment/delete")>-1 
        				|| request.getRequestURI().indexOf("/comment/update")>-1 
        				|| request.getRequestURI().indexOf("/comment/add")>-1 
        				){
        			//提交增加自身信息
        			if((request.getRequestURI().indexOf("/comment/add")>-1)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//回复不许删除/修改
        		if( request.getRequestURI().indexOf("/reply/delete")>-1 
        				|| request.getRequestURI().indexOf("/reply/update")>-1 
        				|| request.getRequestURI().indexOf("/reply/add")>-1 
        				){
        			//提交增加自身信息
        			if((request.getRequestURI().indexOf("/reply/add")>-1)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//收藏不许修改
        		if( request.getRequestURI().indexOf("/collect/delete")>-1 
        				|| request.getRequestURI().indexOf("/collect/update")>-1 
        				|| request.getRequestURI().indexOf("/collect/add")>-1 
        				){
        			//提交增加、删除自身信息
        			if(((request.getRequestURI().indexOf("/collect/delete")>-1) || (request.getRequestURI().indexOf("/collect/add")>-1))
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//签到不许删除/修改
        		if( request.getRequestURI().indexOf("/sign/delete")>-1 
        				|| request.getRequestURI().indexOf("/sign/update")>-1 
        				|| request.getRequestURI().indexOf("/sign/add")>-1 
        				){ 
        			//提交增加签到信息
        			if(request.getRequestURI().indexOf("/sign/add")>-1
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//新手任务不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/noviceTask/delete")>-1 
        				|| request.getRequestURI().indexOf("/noviceTask/update")>-1 
        				|| request.getRequestURI().indexOf("/noviceTask/add")>-1
        				){ 
        			//提交增加新手任务
        			if(request.getRequestURI().indexOf("/noviceTask/add")>-1
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//每日任务不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/dailyTask/delete")>-1 
        				|| request.getRequestURI().indexOf("/dailyTask/update")>-1 
        				|| request.getRequestURI().indexOf("/dailyTask/add")>-1 
        				){ 
        			//提交增加每日任务
        			if(request.getRequestURI().indexOf("/dailyTask/add")>-1
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//流水不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/flowWater/delete")>-1 
        				|| request.getRequestURI().indexOf("/flowWater/update")>-1 
        				|| request.getRequestURI().indexOf("/flowWater/add")>-1 
        				||request.getRequestURI().indexOf("flowWater/list")>-1
                		||method.getName().equals("loadFlowWater")
        				){ 
        			
        			//加载/list流水
        			if((request.getRequestURI().indexOf("/flowWater/list")>-1
        					||method.getName().equals("loadFlowWater")
        					)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		return true;
        	}
        	}
        	
        }
        //如果验证token失败
       throw new MySessionException();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
