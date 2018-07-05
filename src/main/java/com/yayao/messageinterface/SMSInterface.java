package com.yayao.messageinterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumQueryRequest;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumQueryResponse;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里大于短信
 * @author yy
 *
 */
@Configuration
public class SMSInterface {
	public  String url = "http://gw.api.taobao.com/router/rest";
	//创建应用时，TOP颁发的唯一标识，TOP通过App Key来鉴别应用的身份。调用接口时必须传入的参数。  
	@Value("${myPugin.alibabaSmsAppkey}")
	String alibabaSmsAppkey;
	//App Secret是TOP给应用分配的密钥，开发者需要妥善保存这个密钥，这个密钥用来保证应用来源的可靠性，防止被伪造。  
	@Value("${myPugin.alibabaSmsAppsecret}")
	String alibabaSmsAppsecret;
	@Value("${myPugin.alibabaSmsSignName}")
	String alibabaSmsSignName;
	//用户注册
	@Value("${myPugin.alibabaSmsTemplateCodeAcountRegister}")
	String alibabaSmsTemplateCodeAcountRegister;
	//修改密码
	@Value("${myPugin.alibabaSmsTemplateCodePasswordUpdate}")
	String alibabaSmsTemplateCodePasswordUpdate;
    /** 
     * SessionKey简单的说就是代表卖家的登录session 
     * SessionKey是用户身份的标识，应用获取到了SessionKey即意味着应用取得了用户的授权，可以替用户向TOP请求用户的 
     */  
    public  String sessionKey = "*********";
	/**
	 *短信发送
	 *@param extend 验证码
	 *@param recNum 手机号
	 *@param stc 模板码 1用户注册，2修改密码
	 */
    public  String SmsNumSend(String extend,String recNum,Integer stc){
    	//String smsTemplatecode="";
    	String smsTemplatecode=alibabaSmsTemplateCodeAcountRegister;
    	if(stc==1){
    		smsTemplatecode=alibabaSmsTemplateCodeAcountRegister;
    		//smsTemplatecode="SMS_89885232";
    	}else if(stc==2){
    		smsTemplatecode=alibabaSmsTemplateCodePasswordUpdate;
    		
    	}
    	// alibabaSmsAppkey: 23431362
    	//alibabaSmsAppsecret: dfbbbfe72864929214f23f48c901a638
    	 // alibabaSmsSignName: 雅耀
    	//TaobaoClient client = new DefaultTaobaoClient(url, "LTAIgDJerZxD8twJ", "9PuLZNoEQRPDJm465MxL1JIXQY9Msh");
    	//TaobaoClient client = new DefaultTaobaoClient(url, "23431362", "dfbbbfe72864929214f23f48c901a638");
    	TaobaoClient client = new DefaultTaobaoClient(url, alibabaSmsAppkey, alibabaSmsAppsecret);
    	AlibabaAliqinFcSmsNumSendRequest  req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(extend);
		req.setSmsType("normal");
		//req.setSmsFreeSignName(alibabaSmsSignName);
		req.setSmsFreeSignName("雅耀");
		req.setRecNum(recNum);
		req.setSmsParamString("{\"code\":\""+extend+"\",\"product\":\""+recNum+"\"}");
		//req.setSmsTemplateCode("SMS_13026944");
		req.setSmsTemplateCode(smsTemplatecode);
		//req.setExtendCode("1234");
		//req.setExtendName("1234");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			 rsp = client.execute(req);
			//System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsp.getBody();
    }
    /**
     *短信发送记录查询
     */
    public  String SmsNumQuery(String recNum){
    	TaobaoClient client = new DefaultTaobaoClient(url, alibabaSmsAppkey, alibabaSmsAppsecret);
    	AlibabaAliqinFcSmsNumQueryRequest req = new AlibabaAliqinFcSmsNumQueryRequest();
    	//req.setBizId("101688180762^1102237830646");
    	req.setRecNum(recNum);
    	req.setQueryDate("20160524");
    	req.setCurrentPage(1L);
    	req.setPageSize(40L);
    	AlibabaAliqinFcSmsNumQueryResponse rsp = null;
		try {
			rsp = client.execute(req);
			//System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsp.getBody();
		
    }
    /**
     * 综合测试
     * @param args
     * @throws ApiException
     */
    
//    public static void SmsNumTotal() throws ApiException{
//    	TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//    	ItemsGetRequest req = new ItemsGetRequest();
//    	req.setFields("product_id,outer_id");
//		req.setProductId(86126527L);
//		req.setCid(50012286L);
//		req.setProps("10005:10027;10006:29729");
//		ItemsGetResponse rsp = client.execute(req);
//    	System.out.println(rsp.getBody());
//    }
//    
    
    public static void main(String[] args) throws ApiException {
    	System.out.println(new SMSInterface().SmsNumSend(String.valueOf((int)Math.random()*9000+1000),"15111336587",1));
    	
    	//System.out.println(SmsNumQuery("15111336587"));
    	//SmsNumTotal();
	}
}
