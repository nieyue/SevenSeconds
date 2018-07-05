/**
 * 应用中心JS
 */
//初始化书籍频道1经典必玩，2热门推荐，3，最热必玩
mainApp.value('appTypes',[
	{id:1,value:'最热必玩'},
	{id:2,value:'热门推荐'},
	{id:3,value:'H5专栏'}
	]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.AppCenter;//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	
     	$stateProvider
     	.state("main.appCateList", {//应用类型列表
            url:"/appCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.appCateList=[]; 		
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
			  //$scope.showAppCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.appCateListInit();
			  }
			};
			$scope.appCateListInit=function(){
			  $.get(requestDomainUrl+"/appCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/appCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.appCateList=pld;
			 console.log($scope.appCateList)
			$scope.$apply();
               });
       });
			};
			$scope.appCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateAppCate=function(appCate){
            $state.go("main.appCateUpdate",{appCateId:appCate.appCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
           /* $scope.delAppCate=function(appCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/appCate/delete?appCateId="+appCate.appCateId,function(data){
       					console.log($scope.appCateList)
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
     	.state("main.appCateAdd", {//应用类型增加
            url:"/appCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.appCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addAppCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/appCate/add",
						  type: 'POST',
						  data: $scope.appCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.appCate={
									    name:''
									    };
       	   				$scope.myAddAppCateForm.$setPristine();
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
        .state("main.appCateUpdate", {//更新
            url:"/appCateUpdate/:appCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数appCateId
            		*/
                    $scope.updateAppCateId=$state.params.appCateId;
                    console.log($scope.updateAppCateId)
                     if(!$scope.updateAppCateId||!myUtils.userVerification.catNum.test($scope.updateAppCateId)){
                    $state.go("main.appCateList");
                    return;
                    } 
                    /*
            		*获取参数appCateId end
            		*/
            		/*
            		*初始化appCate
            		*/
                    $scope.updateInit=function(appCateId){
                    $.get(requestDomainUrl+"/appCate/"+appCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.appCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateAppCateId);
                    /*
            		*初始化appCate end
            		*/
            		/*
            		*修改appCate提交
            		*/
            		$scope.updateAppCateForm=function(){
            		$.post(requestDomainUrl+"/appCate/update",
            				JSON.parse(angular.toJson($scope.appCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改appCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.appList", {//列表
            url:"/appList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_list.html",
                    controller:function($rootScope,$scope,$state,appTypes){
            $scope.appTypes=appTypes;
            //列表
            $scope.appList=[]; 		
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
			  //$scope.showAppCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.appListInit();
			  }
			};
			$scope.appListInit=function(){
			  $.get(requestDomainUrl+"/app/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/app/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.appList=pld;
			 console.log($scope.appList)
			$scope.$apply();
               });
       });
			};
			$scope.appListInit();
            //列表end
            
			
            //修改
            $scope.updateApp=function(app){
            $state.go("main.appUpdate",{appId:app.appId});
            };
            //修改end
            //删除
            $scope.delApp=function(app){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/app/delete?appId="+app.appId,function(data){
       					console.log($scope.appList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
            //删除end
            
                    }
                } 
            }
        })
        .state("main.appUpdate", {//更新
            url:"/appUpdate/:appId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_update.html",
                    controller:function($rootScope,$scope,$state,appTypes){
                    	$scope.appTypes=appTypes;
                    //获取参数appId
                    $scope.updateAppId=$state.params.appId;
                   // console.log($scope.updateAppId)
                     if(!$scope.updateAppId||!myUtils.userVerification.catNum.test($scope.updateAppId)){
                    $state.go("main.appList");
                    return;
                    } 
                    
            		//获取参数appId end
            		
                    
                     //获取参数appCate 
                     
                    $scope.appCateListInit=function(){
                        $.get(requestDomainUrl+"/appCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.appCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.appCateListInit();
                    
                     //获取参数appCate end
                     
            		
            		//初始化app
                    $scope.updateInit=function(appId){
                    $.get(requestDomainUrl+"/app/"+appId,function(data){
                    	if(data.code==200){
                    		$scope.app=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateAppId);
                    
            		//初始化appCate end
            		
            		
            		//修改app提交
            		$scope.updateAppForm=function(){
            		$.ajax({
            			url:requestDomainUrl+"/app/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.app),
                    	//data:JSON.parse(angular.toJson($scope.app)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		
            		//修改app提交 end
            		 
            		//上传应用图片
            		//上传封面图片
             		$("#appImgFileUpload").change(function(){
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
             				    {inputfile:$("#appImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#appImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.app.imgAddress=data;
             				          $rootScope.formDisabled=false;
             				            $scope.$apply();
             				            }
             				        }
             				    }
             				}
             				);
             		});
             		//上传详情图片
             		$("#appImgFileUploadDetails").change(function(){
             			if(($scope.imgConfigWidthDetails && $scope.imgConfigHeightDetails)
             					&&($scope.imgConfigWidthDetails>1200
             					||$scope.imgConfigWidthDetails<=0
             					||$scope.imgConfigHeightDetails<=0
             					||$scope.imgConfigHeightDetails>1200)
             					){
             			myUtils.myLoadingToast("图片尺寸不符合");
            			 return;
            			 }
             			//最多上传五张
             			 if($scope.app.appImgList.length>=5){
             			 myUtils.myLoadingToast("最多上传五张");
             			 return;
             			 }
             			myUtils.fileUpload(
             				    {inputfile:$("#appImgFileUploadDetails"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#appImgFileUploadDetails").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidthDetails+"&height="+$scope.imgConfigHeightDetails,
             				        success:function(data){
			                 			var img={
			                 			imgId:'',
			                 			imgAddress:'',
			                 			number:'',
			                 			appId:''
			                 			};
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           var maxImg=$scope.app.appImgList[$scope.app.appImgList.length-1];
               				          if(maxImg){
                				        	 img.number=maxImg.number+1; 
                				          }else{
                				        	  img.number=1;
                				          }
             				           img.imgAddress=data;
             				            $scope.app.appImgList.push(img);
             				            console.log(JSON.parse(angular.toJson($scope.app.appImgList)))
             				            $scope.$apply();
             				            }
             				        }
             				    }
             				}
             				);
             		});
             		
             		/**
             	*删除图片
        		*/
        		$scope.delAppImg=function(appImg){
        		myUtils.myLoginOut("确定删除吗？", function(){
   				$.get(requestDomainUrl+"/appImg/delete?appImgId="+appImg.appImgId,function(data){
   					console.log($scope.appImgList)
   	   				if(data.code==200){
   	   				myUtils.myLoadingToast("删除成功", function(){
   	   				location.reload();
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
        })
        .state("main.appAdd", {//应用增加
            url:"/appAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_add.html",
                    controller:function($rootScope,$scope,$state,appTypes){
                    	$scope.appTypes=appTypes;
				    $scope.app={
				    type:$scope.appTypes[0].id,
				    source:'',
				    version:'',
				    title:'',
				    summary:'',
				    appCateId:'',
				    capacity:'',
				    androidUrl:'',
				    iosUrl:'',
				    imgAddress:'',
				    status:1,
				    appImgList:[],
				    content:''
				    };
                     //获取参数appCate 
                    $scope.appCateListInit=function(){
                        $.get(requestDomainUrl+"/appCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.appCateList=data.list;
           	   			$scope.app.appCateId=$scope.appCateList[0].appCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.appCateListInit();
                     //获取参数appCate end
                     
                		//上传封面图片
                 		$("#appImgFileUpload").change(function(){
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
                 				    {inputfile:$("#appImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#appImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.app.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                       
                 		
                 		//上传详情图片
                 		$("#appImgFileUploadDetails").change(function(){
                 			if(($scope.imgConfigWidthDetails && $scope.imgConfigHeightDetails)
                 					&&($scope.imgConfigWidthDetails>1200
                 					||$scope.imgConfigWidthDetails<=0
                 					||$scope.imgConfigHeightDetails<=0
                 					||$scope.imgConfigHeightDetails>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			//最多上传五张
                 			 if($scope.app.appImgList.length>=5){
                 			 myUtils.myLoadingToast("最多上传五张");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#appImgFileUploadDetails"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#appImgFileUploadDetails").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidthDetails+"&height="+$scope.imgConfigHeightDetails,
                 				        success:function(data){
				                 			var img={
				                 			imgId:'',
				                 			imgAddress:'',
				                 			number:'',
				                 			appId:''
				                 			};
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           var maxImg=$scope.app.appImgList[$scope.app.appImgList.length-1];
                   				          if(maxImg){
                    				        	 img.number=maxImg.number+1; 
                    				          }else{
                    				        	  img.number=1;
                    				          }
                 				           img.imgAddress=data;
                 				            $scope.app.appImgList.push(img);
                 				            console.log(JSON.parse(angular.toJson($scope.app.appImgList)))
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                 		
                 		/**
                 	*删除图片
            		*/
            		$scope.delAppImg=function(appImg){
            		myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/appImg/delete?appImgId="+appImg.appImgId,function(data){
       					console.log($scope.appImgList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            		};
                     //表单提交
                    $scope.addAppForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/app/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.app),
						  success: function(data){
						  if(data.code==200){
							  $scope.app={
									    type:$scope.appTypes[0].id,
									    source:'',
									    version:'',
									    title:'',
									    summary:'',
									    appCateId:$scope.appCateList[0].appCateId,
									    capacity:'',
									    androidUrl:'',
									    iosUrl:'',
									    imgAddress:'',
									    status:1,
									    appImgList:[],
									    content:''
									    };
       	   				$scope.myAddAppForm.$setPristine();
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
        });
     	});	