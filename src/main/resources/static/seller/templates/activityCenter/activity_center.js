/**
 * 活动中心JS
 */
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.ActivityCenter;//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	
     	$stateProvider
     	.state("main.activityCateList", {//活动类型列表
            url:"/activityCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.activityCateList=[]; 		
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
			  //$scope.showactivityCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.activityCateListInit();
			  }
			};
			$scope.activityCateListInit=function(){
			  $.get(requestDomainUrl+"/activityCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/activityCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.activityCateList=pld;
			 console.log($scope.activityCateList)
			$scope.$apply();
               });
       });
			};
			$scope.activityCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateActivityCate=function(activityCate){
            $state.go("main.activityCateUpdate",{activityCateId:activityCate.activityCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
           /* $scope.delactivityCate=function(activityCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/activityCate/delete?activityCateId="+activityCate.activityCateId,function(data){
       					console.log($scope.activityCateList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };*/
			/*
            *删除end
            */
                    }
                } 
            }
        })
     	.state("main.activityCateAdd", {//活动类型增加
            url:"/activityCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.activityCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addActivityCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/activityCate/add",
						  type: 'POST',
						  data: $scope.activityCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.activityCate={
									    name:''
									    };
       	   				$scope.myAddActivityCateForm.$setPristine();
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
        })
        .state("main.activityCateUpdate", {//更新
            url:"/activityCateUpdate/:activityCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数activityCateId
            		*/
                    $scope.updateActivityCateId=$state.params.activityCateId;
                    console.log($scope.updateActivityCateId)
                     if(!$scope.updateActivityCateId||!myUtils.userVerification.catNum.test($scope.updateActivityCateId)){
                    $state.go("main.activityCateList");
                    return;
                    } 
                    /*
            		*获取参数activityCateId end
            		*/
            		/*
            		*初始化activityCate
            		*/
                    $scope.updateInit=function(activityCateId){
                    $.get(requestDomainUrl+"/activityCate/"+activityCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.activityCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateActivityCateId);
                    /*
            		*初始化activityCate end
            		*/
            		/*
            		*修改activityCate提交
            		*/
            		$scope.updateActivityCateForm=function(){
            		$.post(requestDomainUrl+"/activityCate/update",
            				JSON.parse(angular.toJson($scope.activityCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改activityCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.activityList", {//列表
            url:"/activityList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_list.html",
                    controller:function($rootScope,$scope,$state){
            
            //列表
            
            $scope.activityList=[]; 		
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
			  //$scope.showactivityCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.activityListInit();
			  }
			};
			$scope.activityListInit=function(){
			  $.get(requestDomainUrl+"/activity/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/activity/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.activityList=pld;
			 console.log($scope.activityList)
			$scope.$apply();
               });
       });
			};
			$scope.activityListInit();
            //列表end
            
			
            //修改
            $scope.updateActivity=function(activity){
            $state.go("main.activityUpdate",{activityId:activity.activityId});
            };
            //修改end
            //跳转
            $scope.activityLink=function(activity){
            	location.href="http://"+domainManager.SevenSeconds+"/activity_details.html?activityId="+activity.activityId;
            };
            //跳转end
            
			
            //删除
            
          /*  $scope.delActivity=function(activity){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/activity/delete?activityId="+activity.activityId,function(data){
       					console.log($scope.activityList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };*/
			
            //删除end
            
                    }
                } 
            }
        })
        .state("main.activityUpdate", {//更新
            url:"/activityUpdate/:activityId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_update.html",
                    controller:function($rootScope,$scope,$state){
                    	//初始化datetimerpicker
                    	//开始时间：  
                    	$('#startDateId').datetimepicker({
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd hh:ii:ss',
                    		startDate : new Date(),
                    		autoclose: true
                    	}).on('changeDate',function(e){  
                    		var nowDate = e.date; 
                    		//保证开始时间是结束时间的开头  
                    	    $('#endDateId').datetimepicker('setStartDate',nowDate);
                    	});  
                    	//结束时间：  
                    	$('#endDateId').datetimepicker({ 
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd hh:ii:ss',
                    		startDate : new Date(),
                    		autoclose: true
                    	}).on('changeDate',function(e){  
                    		 var nowDate = e.date;
                    		 //保证结束时间是开始时间的结尾
                    	    $('#startDateId').datetimepicker('setEndDate',nowDate); 
                    	});  
                    	 var editor=$rootScope.myWangEditor("activityUpdateEditor");
                    //获取参数activityId
                    $scope.updateActivityId=$state.params.activityId;
                   // console.log($scope.updateActivityId)
                     if(!$scope.updateActivityId||!myUtils.userVerification.catNum.test($scope.updateActivityId)){
                    $state.go("main.activityList");
                    return;
                    } 
                    
            		//获取参数activityId end
            		
                    
                     //获取参数activityCate 
                     
                    $scope.activityCateListInit=function(){
                        $.get(requestDomainUrl+"/activityCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.activityCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.activityCateListInit();
                    
                     //获取参数activityCate end
                     
            		
            		//初始化activity
                    $scope.updateInit=function(activityId){
                    $.get(requestDomainUrl+"/activity/"+activityId,function(data){
                    	if(data.code==200){
                    		$scope.activity=data.list[0];
                    		editor.$txt.html($scope.activity.content);
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateActivityId);
                    
            		//初始化activityCate end
            		
            		
            		//修改activity提交
            		$scope.updateActivityForm=function(){
            			$scope.activity.content =editor.$txt.html();
            		$.ajax({
            			url:requestDomainUrl+"/activity/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.activity),
                    	//data:JSON.parse(angular.toJson($scope.activity)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		
            		//修改activity提交 end
            		 
            		//上传活动图片
            		//上传文章封面图片
             		$("#activityImgFileUpload").change(function(){
             			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
             					&&($scope.imgConfigWidth>1200
             					||$scope.imgConfigWidth<=0
             					||$scope.imgConfigHeight<=0
             					||$scope.imgConfigHeight>1200)
             					){
             			myUtils.myLoadingToast("图片尺寸不符合");
            			 return;
            			 }
             			myUtils.fileUpload(
             				    {inputfile:$("#activityImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#activityImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.activity.imgAddress=data;
             				          $rootScope.formDisabled=false;
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
        })
        .state("main.activityAdd", {//活动增加
            url:"/activityAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_add.html",
                    controller:function($rootScope,$scope,$state){
                    	//初始化datetimerpicker
                    	//开始时间：  
                    	$('#startDateId').datetimepicker({
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd hh:ii:ss',
                    		startDate : new Date(),
                    		autoclose: true
                    	}).on('changeDate',function(e){  
                    		var nowDate = e.date; 
                    		//保证开始时间是结束时间的开头  
                    	    $('#endDateId').datetimepicker('setStartDate',nowDate);
                    	});  
                    	//结束时间：  
                    	$('#endDateId').datetimepicker({ 
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd hh:ii:ss',
                    		startDate : new Date(),
                    		autoclose: true
                    	}).on('changeDate',function(e){  
                    		 var nowDate = e.date;
                    		 //保证结束时间是开始时间的结尾
                    	    $('#startDateId').datetimepicker('setEndDate',nowDate); 
                    	});    
				    $scope.activity={
				    title:'',
				    summary:'',
				    activityCateId:'',
				    imgAddress:'',
				    status:1,
				    content:''
				    };
				    var editor=$rootScope.myWangEditor("activityAddEditor");
                     //获取参数activityCate 
                    $scope.activityCateListInit=function(){
                        $.get(requestDomainUrl+"/activityCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.activityCateList=data.list;
           	   			$scope.activity.activityCateId=$scope.activityCateList[0].activityCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.activityCateListInit();
                     //获取参数activityCate end
                     
                		//上传文章封面图片
                 		$("#activityImgFileUpload").change(function(){
                 			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
                 					&&($scope.imgConfigWidth>1200
                 					||$scope.imgConfigWidth<=0
                 					||$scope.imgConfigHeight<=0
                 					||$scope.imgConfigHeight>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#activityImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#activityImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.activity.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                        
                     //表单提交
                    $scope.addActivityForm=function(){
                    	$scope.activity.content =editor.$txt.html();
                    	$.ajax({
						  url: requestDomainUrl+"/activity/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.activity),
						  success: function(data){
						  if(data.code==200){
							  $scope.activity={
									    title:'',
									    activityCateId:$scope.activityCateList[0].activityCateId,
									    imgAddress:'',
									    summary:'',
									    status:1,
									    content:''
									    };
							  editor.$txt.html("<p><br/></p>");
       	   				$scope.myAddActivityForm.$setPristine();
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
        }) 
        .state("main.activityUserList", {//列表
            url:"/activityUserList/:activityId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/activityCenter/activity_user_list.html",
                    controller:function($rootScope,$scope,$state){
                    	//获取参数activityId
                        $scope.activityId=$state.params.activityId;
                       // console.log($scope.updateActivityId)
                         if(!$scope.activityId||!myUtils.userVerification.catNum.test($scope.activityId)){
                        $state.go("main.activityList");
                        return;
                        } 
                        
                		//获取参数activityId end
            //列表
            $scope.activityUserList=[]; 		
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
			  //$scope.showactivityCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.activityUserListInit();
			  }
			};
			$scope.activityUserListInit=function(){
			  $.get(requestDomainUrl+"/activityUser/count?activityId="+$scope.activityId,function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/activityUser/list?activityId="+$scope.activityId+"&pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.activityUserList=pld;
			 console.log($scope.activityUserList)
			$scope.$apply();
               });
       });
			};
			$scope.activityUserListInit();
			
            //列表end
            
                    }
                } 
            }
        });
     	});	