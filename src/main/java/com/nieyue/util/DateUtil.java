package com.nieyue.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 日期格式化类
 * @author yy
 *
 */
public class DateUtil {
	/**
	 *@param n为小时段，默认24段，即
	 * 段时间的时间设置
	 * @return
	 */
	public static Date getDayPeriod(int n ){ 
		if(n<=0 ||n>24){
			n=24;
		}
		//long dayseconds=86400;//一天的秒数；
		long daystart=getStartTime().getTime()/1000;//当日初始秒数
		Date date =new Date();
		long daynow = date.getTime()/1000;//现在的秒数
		long daycha=daynow-daystart;//相差秒数
		
		long every=24/n;//如n=3;every=8 即8小时记录一次
		long everyseconds=every*60*60;//小时段的秒数
		long nown=daycha/everyseconds;//第几次
		Date rd = new Date((nown*everyseconds+daystart)*1000);
		return rd;
	}  
	/**
	 *@param n为小时段，默认24段，即
	 * 段时间的时间设置
	 * @return
	 */
	public static Date getDayPeriod(int n ,Date date){ 
		if(n<=0 ||n>24){
			n=24;
		}
		//long dayseconds=86400;//一天的秒数；
		long daystart=getStartTime().getTime()/1000;//当日初始秒数
		 date =new Date();
		long daynow = date.getTime()/1000;//现在的秒数
		long daycha=daynow-daystart;//相差秒数
		
		long every=24/n;//如n=3;every=8 即8小时记录一次
		long everyseconds=every*60*60;//小时段的秒数
		long nown=daycha/everyseconds;//第几次
		Date rd = new Date((nown*everyseconds+daystart)*1000);
		return rd;
	}  
	/**
	 * 获取当日开始时间
	 * @return
	 */
	 public static Date getStartTime(){  
		Date date =new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		long nd = date.getTime()/1000*1000;
		return new Date(nd);
	}  
	  
	/**
	 * 获取当日结束时间
	 * @return
	 */
	 public static Date getEndTime(){  
		Date date =new Date();
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		long nd = date.getTime()/1000*1000+999;
		return new Date(nd);
	} 
	 /**
	  * 获取某日开始
	  * @return
	  */
	 public static Date getStartTime(Date date){  
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			long nd = date.getTime()/1000*1000;
		 return new Date(nd);
	 } 
	 /**
	  * 获取当前时间到当日结束时间差  
	  * 单位 ： 秒
	  * @return
	  */
	 public static long currentToEndTime(){
		 Date date=new Date();
		 long miao = (getEndTime().getTime()-date.getTime())/1000;
		 return miao;
	 } 
	/**
	 * 格式化时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime(){
	Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
	String nowTime="";
	nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
	return nowTime;
	}
	/**
	 * 格式化时间yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentTimeDay(){
	Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置显示格式
	String nowTime="";
	nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
	return nowTime;
	}
	/**
	 * date格式化时间 format
	 * @return
	 */
	public static String dateFormatSimpleDate(Date date,String format){
		DateFormat df = new SimpleDateFormat(format);//设置显示格式
		String nowTime="";
		nowTime= df.format(date);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	/**
	 * 格式化时间"yyyyMMddHHmmss
	 * @return
	 */
	public static String getOrdersTime(){
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	/**
	 * 格式化时间"yyyyMMdd
	 * @return
	 */
	public static String getImgDir(){
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMdd");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
		return nowTime;
	}
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();  
        String t = String.valueOf(time/1000);  
        return t;  
    }  
    /**
     * 获取精准时间格式存储数据库
     * @param args
     */
    public static Date getFormatCurrentTime(){  
    	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	 String time=getCurrentTime();

    	 Timestamp time1 = null;
		try {
			time1 = new Timestamp(sdf.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
    	return time1;  
    }  
    /**
     * 获取两日期之间的相隔天数
     * @param args
     * @throws ParseException 
     */
    public static Long getSeparatedTime(Date d1,Date d2) { 
    	Date d1starttime = getStartTime(d1);
    	Date d2starttime = getStartTime(d2);
		if(isSameDate(d1starttime, d2starttime)){//同一天
			return 0l;
		}
		else if(Math.abs((d2.getTime()-d1.getTime()))<=3600*24*1000l){//差24小时以内算一天
			return 1l;
		}
		else{
			long daysBetween = Math.abs((d2.getTime()-d1.getTime())/(3600*24*1000l));//两日期之间相隔的天数 	
			//long daysBetween = Math.abs(d2.getDate()-d1.getDate());//两日期之间相隔的天数 	
			return daysBetween;  
		}
    }  
    /**
     * 获取从起始日期开始几天后的日期
     * @param args
     * @throws ParseException 
     */
    public static Date getFirstToDay(Date firstDate,long coupleDay) {  
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date lastDate = new Date(coupleDay*3600*24*1000+firstDate.getTime());//两日期之间相隔的天数 
    	return lastDate;  
    }  
    /**
     * 获取从起始时间开始之后几分钟的时间
     * @param args
     * @throws ParseException 
     */
    public static Date getFirstToSecondsTime(Date firstDate,long coupleTime) {  
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date lastDate = new Date(coupleTime*60*1000+firstDate.getTime());//两日期之间相隔的天数 
    	return lastDate;  
    }  
    /**
     * 格式化时间从yyyy-MM-dd HH:mm:ss到Wed Mar 02 09:19:00 CST 2016
     * @param args
     * @throws ParseException 
     */
    public static Date parseDate(String date) throws ParseException {  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date da = sdf.parse(date);
    	return da;  
    }  
    /**
     * 是否同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                        .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    //  输出结果：  
    //  timeStamp=1417792627  
    //  date=2014-12-05 23:17:07  
    //  1417792627  
    //static HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    public static void main(String[] args) {  
    	System.out.println(getCurrentTime());
    	System.out.println(getOrdersTime());
    	String timeStamp = timeStamp();  
        System.out.println("timeStamp="+timeStamp);  
          
        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");  
        System.out.println("date="+date);  
          
        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");  
        System.out.println(timeStamp2); 
        System.out.println(getFormatCurrentTime().toLocaleString());
      //  HttpSession session = request.getSession();
       // session.setAttribute("nieyue", "dsfsdfsdf");
        //String nieyue = (String) session.getAttribute("nieyue");
       // System.out.println(session); 
        System.out.println(getFirstToDay(new Date(), 1).toLocaleString());
        System.out.println(getFirstToSecondsTime(new Date(), 1).toLocaleString());
        System.out.println(getImgDir());
        Date d1=new Date("2017/08/12 23:13:46");
        Date d2=new Date("2017/08/14 0:38:02");
        System.out.println(getSeparatedTime(new Date("2017/08/30 06:49:58"),new Date("2017/09/01 08:58:30")));
        
        
        /*System.out.println(dateFormatSimpleDate(getDayPeriod(8,new Date()),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(getDayPeriod(3).getTime());
        System.out.println(getStartTime().getTime());
        System.out.println(getEndTime().getTime());*/
        
       // DateUtil.getSeparatedTime(s0.getCreateDate(), new Date())
       System.out.println(MyDESutil.getMD5("jiaxingyufa"));
       
       System.out.println(new Random().nextInt(4)+1);
       String aaaaa=null;
      // System.out.println(aaaaa.equals("ret"));
       System.err.println(DateUtil.getSeparatedTime( new Date(),DateUtil.getFirstToDay(new Date(), 30))>30);
    System.out.println(Integer.MAX_VALUE);
    }  
}
