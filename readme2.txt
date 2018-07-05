#Version 3.0
图片服务器：8000
推送：8001
任务调度：8002
兑换商城：8081
书城：8082

按照数字对应文字的方式，正数为收益，负数为消费
收益：
1.新手任务 (总计：26000积分+2000积分)
  0.首次收徒+20000积分
  1.绑定手机号+2000积分  
  2.绑定微信+2000积分 
  3.阅读资讯1分钟+2000积分
  4.观看视频1分钟+2000积分（延后开放）
  
2.日常任务(总计：每日任务830积分)
  1.新闻分享   0/1        30积分
  2.阅读资讯   0/10        100积分
  3.分享朋友圈收徒  0/1     100积分
  4.分享到微信群收徒  0/1    100积分
  5.红包抽奖赚多多   0/1     500积分
3.达人奖励 （总计：徒弟每天获得200积分奖励完成总计30000积分，邀请成功即可获得3000积分）
  1.5000
  2.4000
  3.3000
  4.3000
  5.3000
  6.4000
  7.5000
  8.转发推广文章    （推广类型）200积分/阅读，（其他正常文章）50积分/阅读。
  9.APP广告点击（200积分）
4.文章阅读
  1.阅读文章收益   200积分
5.徒弟进贡
  1.日常任务   日常任务积分*进贡比例   如：阅读资讯 100*0.2=20积分
  2.文章阅读   200*0.2=40
6.推广分成
  1.推广CPS分成 （根据广告分成）
7.签到
  1.签到积分 （最高7次，不连续，重置为第一次,连续为5000）
  	第一天领50积分；
	第二天领100积分；
	第三天领100积分；
	第四天领1500积分；
	第五天领150积分；
	第六天领200积分；
	第七天领5000积分；
消费：
-1.兑换商品
  1.兑换商城兑换商品   （根据商品价格）
-2.书城消费
  0.混合消费
  1.真钱消费
  2.积分消费
  
  
广告类型

cps 积分狂赚list
cpsad 积分狂赚轮播图
bookad 书城轮播图
findad 发现界面轮播图
shopad 兑换界面轮播图



_______________________
ConvertMall
#修改商品比率
update mer_tb 
set 
old_price=old_price*100,
price=price*100,
sale_money=sale_money*100
#修改订单商品比率
update order_mer_tb 
set 
price=price*100,
total_price=total_price*100

SevenSeconds
#修改文章
update article_tb 
set 
user_unit_price=user_unit_price*100,
unit_price=unit_price*100,
user_now_total_price=user_now_total_price*100,
total_price=total_price*100,
now_total_price=now_total_price*100

#修改账户
update finance_tb 
set 
money=money*100,
recharge=recharge*100,
consume=consume*100,
withdrawals=withdrawals*100,
self_profit=self_profit*100,
partner_profit=partner_profit*100,
base_profit=base_profit*100

#清空日常任务
#清空流水表