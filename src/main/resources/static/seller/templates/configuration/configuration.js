/**
 * 配置JS
 */
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.ScheduleJob;//sheduleJob请求数据url
	//var requestDomainUrl="http://task.newzhuan.cn";//sheduleJob请求数据url
	//var requestDomainUrl="http://"+location.hostname+":8001";//sheduleJob请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	//var imgUploadDomainUrl="http://img.newzhuan.cn";//请求图片上传url
	
     	$stateProvider
     	.state("main.shareDomain", {//分享域名
            url:"/shareDomain",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/share_domain.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.domain={
                    shareDomain:location.host//默认
                    }
                    //初始化
                    $scope.initShareDomain=function(){
                    $.get("/shareDomain/get",
                    	function(data){
       	   				if(data.code==200){
       	   				  $scope.domain={
                    shareDomain:data.list[0].shareDomain//默认
                    };
                     $scope.$apply();
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initShareDomain();
                    //分享域名
            	 $scope.updateShareDomainForm=function(){
                    	$.post("/shareDomain/update",
                    	{
                    	shareDomain:$scope.domain.shareDomain
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    }
                } 
            }
        }).state("main.adDomain", {//广告域名
            url:"/adDomain",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/ad_domain.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.domain={
                    adDomain:location.host//默认
                    }
                    //初始化
                    $scope.initAdDomain=function(){
                    $.get("/adDomain/get",
                    	function(data){
       	   				if(data.code==200){
       	   				  $scope.domain={
                    adDomain:data.list[0].adDomain//默认
                    }
                    $scope.$apply();
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initAdDomain();
                    //跨域域名
            	 $scope.updateAdDomainForm=function(){
                    	$.post("/adDomain/update",
                    	{
                    	adDomain:$scope.domain.adDomain
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    }
                } 
            }
        }).state("main.ssDomain", {//三俗域名
            url:"/ssDomain",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/ss_domain.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.domain={
                    ssDomain:location.host//默认
                    }
                    //初始化
                    $scope.initSsDomain=function(){
                    $.get("/ssDomain/get",
                    	function(data){
       	   				if(data.code==200){
       	   				  $scope.domain={
                    ssDomain:data.list[0].ssDomain//默认
                    }
                    $scope.$apply();
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initSsDomain();
                    //跨域域名
            	 $scope.updateSsDomainForm=function(){
                    	$.post("/ssDomain/update",
                    	{
                    	ssDomain:$scope.domain.ssDomain
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    }
                } 
            }
        }).state("main.gmwDomain", {//光明网域名
            url:"/gmwDomain",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/gmw_domain.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.domain={
                    score:1,
                    gmwDomain:'',
                    }
                    $scope.gmwDomainList=[];
                    //初始化
                    $scope.initGmwDomain=function(){
                    $.get("/gmwDomain/list",
                    	function(data){
       	   				if(data.code==200){
       	   				  $scope.gmwDomainList=data.list//默认
                    
                    $scope.$apply();
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initGmwDomain();
                    //增加
            	 $scope.addGmwDomainForm=function(){
                    	$.post("/gmwDomain/add",
                    	{
                    	//score:$scope.domain.score,
                    	value:$scope.domain.gmwDomain
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("增加成功");
       	   				$scope.initGmwDomain();
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    //删除
                     $scope.delGmwDomain=function(gmw){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get("/gmwDomain/del",
       				{value:gmw},
       				function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				$scope.initGmwDomain();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
                    }
                } 
            }
        }) .state("main.scaleIncrement", {//合伙人增量
            url:"/scaleIncrement",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/scale_increment.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.scale={
                    scaleValue:0
                    };
                    //初始化
                    $scope.initScaleIncrement=function(){
                    $.get("/scaleIncrement/get",
                    	function(data){
       	   				if(data.code==200){
       	   				$scope.scale={
                    	scaleValue:data.list[0].scaleIncrement
                   		 };
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initScaleIncrement();
                    //全局受益人比例增量
            	 $scope.updateScaleIncrementForm=function(){
                    	$.post("/scaleIncrement/update",
                    	{
                    	scaleValue:$scope.scale.scaleValue
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    }
                } 
            }
        }) 
        .state("main.sensitiveWord", {//敏感词
            url:"/sensitiveWord",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/sensitive_word.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.sw={sensitiveWord:''};
                    $scope.sensitiveWordList=[];
                    //初始化
                    $scope.initSensitiveWord=function(){
                    $.get("/sensitiveWord/list",
                    	function(data){
       	   				if(data.code==200){
       	   				  $scope.sensitiveWordList=data.list//默认
                    
                    $scope.$apply();
       	   				myUtils.myLoadingToast("获取成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("获取失败");   	   				
       	   				}
       	   				});
                    };
                    $scope.initSensitiveWord();
                    //直接添加增加
            	 $scope.addSensitiveWordForm=function(){
                    	$.post("/sensitiveWord/add",
                    	{
                    	//score:$scope.domain.score,
                    	word:$scope.sw.sensitiveWord
                    	},
                    	function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("增加成功");
       	   				$scope.initSensitiveWord();
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				});
                    };
                    	//txt文件上传添加增加
                 	//	$("#sensitiveWordFileUpload").change(function(){
                 		$(document).on("change","#sensitiveWordFileUpload",function(){
                 			myUtils.fileUpload(
                 				    {inputfile:$("#sensitiveWordFileUpload"),
                 				    photoExt:[".jpg",".apk",".icon",".gif",".txt"],
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"sensitiveWordUpload",value:$("#sensitiveWordFileUpload").get(0).files[0]}
                 				            ],
                 				        url:"/sensitiveWord/request",
                 				        success:function(data){
                 				            console.log(data)
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				            myUtils.myLoadingToast("增加成功");
       	   									$scope.initSensitiveWord();
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                 		
                    }
                } 
            }
        }) .state("main.appVersionList", {//app版本列表
            url:"/appVersionList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/app_version_list.html",
                    controller:function($rootScope,$scope,$state){
                  
            /*
            *列表
            */
            $scope.appVersionList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			//初始化请求
			$scope.platform='';
			$scope.status='';
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber,platform,status){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showNoticeListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.appVersionListInit(platform,status);
			  }
			};
			$scope.appVersionListInit=function(platform,status){
			  $.get("/appVersion/count?platform="+platform+"&status="+status,function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
          
        //初始化
  $.get("/appVersion/list?platform="+platform+"&status="+status+"&pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.appVersionList=pld;
			 console.log($scope.appVersionList)
			$scope.$apply();
               });
       });
			};
			$scope.appVersionListInit($scope.platform,$scope.status);
			/*
            *列表end
            */
			/*
            *删除
            */
            $scope.delAppVersion=function(appVersion){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get("/appVersion/delete?appVersionId="+appVersion.appVersionId,function(data){
       					console.log($scope.appVersion)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				$scope.appVersionListInit();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
			/*
            *删除end
            */
			/*
            *根据平台查询
            */
            $scope.appVersionListByPlatform=function(platform,status){
            $scope.platform=platform;
            $scope.status=status;
            	$scope.appVersionListInit(platform,status);
            };
			/*
            *根据平台查询end
            */
                    }
                } 
            }
        })
        .state("main.appVersionAdd", {//app版本增加
            url:"/appVersionAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/app_version_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.appVersion={
				    name:'',
				    content:'',
				    link:''
				    };
                     
                 		//上传链接
                 		$("#appVersionLinkFileUpload").change(function(){
                 			//最多上传1张
                 			 if($scope.appVersion.link){
                 			 myUtils.myLoadingToast("最多上传一个");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#appVersionLinkFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#appVersionLinkFileUpload").get(0).files[0]}
                 				            ],
                 				        url:"http://"+ $rootScope.imgUploadDomain+"/img/add",
                 				        success:function(data){
                 				            console.log(data)
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				            $scope.appVersion.link=data;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                     //表单提交
                    $scope.addAppVersionForm=function(){
                    	$.ajax({
						  url: "/appVersion/add",
						  type: 'POST',
						  data: $scope.appVersion,
						  success: function(data){
						  if(data.code==200){
       	   			 $scope.appVersion={
				    platform:0,
				    name:'',
				    type:0,
				    content:'',
				    status:0,
				    link:''
				    };
       	   				$scope.myAddAppVersionForm.$setPristine();
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("添加成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("添加失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
                    };
                    }
                } 
            }
        }).state("main.scheduleJobList", {//任务工作列表
            url:"/scheduleJobList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/schedule_job_list.html",
                    controller:function($rootScope,$scope,$state){
                  
            /*
            *列表
            */
            $scope.scheduleJobList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showNoticeListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.scheduleJobListInit();
			  }
			};
			$scope.scheduleJobListInit=function(){
			  $.get(requestDomainUrl+"/scheduleJob/count",function(cd){
           		$scope.totalNumber=cd.list[0];             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
          
        //初始化
  $.get(requestDomainUrl+"/scheduleJob/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.scheduleJobList=pld;
			 console.log($scope.scheduleJobList)
			$scope.$apply();
               });
       });
			};
			$scope.scheduleJobListInit();
			/*
            *列表end
            */
			/*
            *删除
            */
            $scope.delScheduleJob=function(scheduleJob){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/scheduleJob/delete?scheduleJobId="+scheduleJob.scheduleJobId,function(data){
       					console.log($scope.appVersion)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				$scope.scheduleJobListInit();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
			/*
            *删除end
            */
            //修改
            $scope.updateScheduleJob=function(scheduleJob){
            	$state.go("main.scheduleJobUpdate",{scheduleJobId:scheduleJob.scheduleJobId});
            	
            };
			/*
            *根据平台查询
            */
//            $scope.appVersionListByPlatform=function(platform,status){
//            $scope.platform=platform;
//            $scope.status=status;
//            	$scope.appVersionListInit(platform,status);
//            };
			/*
            *根据平台查询end
            */
                    }
                } 
            }
        }) .state("main.scheduleJobAdd", {//任务计划增加
            url:"/scheduleJobAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/schedule_job_add.html",
                    controller:function($rootScope,$scope,$state){
                    	//初始化datetimerpicker
                    	$(".form_datetime").datetimepicker({
                            language: 'zh-CN',
                            format: 'yyyy-mm-dd hh:ii:ss',
                 	          autoclose: true
                          });
                    	
                    	//时间转cron
                    	function dateToCron(timeStamp){
                    		var date = new Date(timeStamp);
                    		Y = date.getFullYear();
                    		M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) ;
                    		D = date.getDate() ;
                    		h = date.getHours() ;
                    		m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes());
                    		s = (date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds()); 
                    	return s+' '+m+' '+h+" "+D+" "+M+" "+"? "+Y; 
                    	}
                    	//job类型
                    	$scope.typeModels=[
                    		{id:1,value:'文章推送'},{id:2,value:'书籍推送'}]
                    	//job状态
                    	$scope.jobStatusModels=[
                    		{key:'NORMAL',value:'正常'},{key:'PAUSED',value:'暂停'}]
                    	
				    $scope.scheduleJob={
				    jobStatus:'NORMAL',
				    cronExpressionDate:'',
				    cronExpression:'',
				    description:'',
				    type:1,
				    };
                	//cron时间改变
                	$scope.scheduleJobCronExpressionDateChange=function(scheduleJob){
                		scheduleJob.cronExpression=dateToCron(scheduleJob.cronExpressionDate);
                	};
                     //表单提交
                    $scope.addScheduleJobForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/scheduleJob/add",
						  type: 'POST',
						  data: $scope.scheduleJob,
						  success: function(data){
						  if(data.code==200){
       	   				$scope.scheduleJob={
       	   						jobStatus:'NORMAL',
       	   						cronExpressionDate:'',
       	   						cronExpression:'',
       	   						description:'',
       	   						type:1,
       	   				};
       	   				$scope.myAddScheduleJobForm.$setPristine();
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("添加成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("添加失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
                    };
                    
                    }
                } 
            }
        }).state("main.scheduleJobUpdate", {//任务计划更新
            url:"/scheduleJobUpdate/:scheduleJobId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/configuration/schedule_job_update.html",
                    controller:function($rootScope,$scope,$state){
                    	//初始化datetimerpicker
                    	$(".form_datetime").datetimepicker({
                            language: 'zh-CN',
                            format: 'yyyy-mm-dd hh:ii:ss',
                 	          autoclose: true
                          });
                    	
                    	//时间转cron
                    	function dateToCron(timeStamp){
                    		var date = new Date(timeStamp);
                    		Y = date.getFullYear();
                    		M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) ;
                    		D = date.getDate() ;
                    		h = date.getHours() ;
                    		m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes());
                    		s = (date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds()); 
                    	return s+' '+m+' '+h+" "+D+" "+M+" "+"? "+Y; 
                    	}
                    	//job类型
                    	$scope.typeModels=[
                    		{id:1,value:'文章推送'},{id:2,value:'书籍推送'}]
                    	//job状态
                    	$scope.jobStatusModels=[
                    		{key:'NORMAL',value:'正常'},{key:'PAUSED',value:'暂停'}]
                    	/*
                		*获取参数scheduleJobId
                		*/
                        $scope.updateScheduleJobId=$state.params.scheduleJobId;
                        console.log($scope.updateScheduleJobId)
                         if(!$scope.updateScheduleJobId||!myUtils.userVerification.catNum.test($scope.updateScheduleJobId)){
                        $state.go("main.scheduleJobList");
                        return;
                        } 
                        /*
                		*获取参数scheduleJobId end
                		*/
                    	/*
                		*初始化scheduleJob
                		*/
                        $scope.updateInit=function(scheduleJobId){
                        $.get(requestDomainUrl+"/scheduleJob/load?scheduleJobId="+scheduleJobId,function(data){
           	   				if(data.code==200){
           	   				$scope.scheduleJob=data.list[0];
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.updateInit($scope.updateScheduleJobId);
                        /*
                		*初始化scheduleJob end
                		*/
                      //cron时间改变
                    	$scope.scheduleJobCronExpressionDateChange=function(scheduleJob){
                    		//cron表达式改变
                    		scheduleJob.cronExpression=dateToCron(scheduleJob.cronExpressionDate);
                    	};
                     //表单提交
                    $scope.updateScheduleJobForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/scheduleJob/update",
						  type: 'POST',
						  data: $scope.scheduleJob,
						  success: function(data){
						  if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("修改失败"); 
						  }
						});
                    };
                    
                    }
                } 
            }
        });
     	});	