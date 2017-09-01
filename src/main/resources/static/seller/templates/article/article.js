/**
 * 文章JS
 */
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.Article;//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
     	$stateProvider
     	.state("main.articleCateList", {//文章类型列表
            url:"/articleCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.articleCateList=[]; 		
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
			  //$scope.showarticleCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.articleCateListInit();
			  }
			};
			$scope.articleCateListInit=function(){
			  $.get(requestDomainUrl+"/articleCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/articleCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.articleCateList=pld;
			 console.log($scope.articleCateList)
			$scope.$apply();
               });
       });
			};
			$scope.articleCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateArticleCate=function(articleCate){
            $state.go("main.articleCateUpdate",{articleCateId:articleCate.articleCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
           /* $scope.delArticleCate=function(articleCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/articleCate/delete?articleCateId="+articleCate.articleCateId,function(data){
       					console.log($scope.articleCateList)
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
     	.state("main.articleCateAdd", {//文章类型增加
            url:"/articleCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.articleCate={
				    name:'',
				    holder:0
				    };
                     
                     //表单提交
                    $scope.addArticleCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/articleCate/add",
						  type: 'POST',
						  data: $scope.articleCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.articleCate={
									    name:'',
									    holder:0
									    };
       	   				$scope.myAddArticleCateForm.$setPristine();
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
        .state("main.articleCateUpdate", {//更新
            url:"/articleCateUpdate/:articleCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数articleCateId
            		*/
                    $scope.updateArticleCateId=$state.params.articleCateId;
                    console.log($scope.updateArticleCateId)
                     if(!$scope.updateArticleCateId||!myUtils.userVerification.catNum.test($scope.updateArticleCateId)){
                    $state.go("main.articleCateList");
                    return;
                    } 
                    /*
            		*获取参数articleCateId end
            		*/
            		/*
            		*初始化articleCate
            		*/
                    $scope.updateInit=function(articleCateId){
                    $.get(requestDomainUrl+"/articleCate/"+articleCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.articleCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateArticleCateId);
                    /*
            		*初始化articleCate end
            		*/
            		/*
            		*修改articleCate提交
            		*/
            		$scope.updateArticleCateForm=function(){
            		$.post(requestDomainUrl+"/articleCate/update",
            				JSON.parse(angular.toJson($scope.articleCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改articleCate提交 end
            		*/
                    }
                } 
            }
        })
     .state("main.articleData", {//文章数据
            url:"/articleData/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_data.html",
                    controller:function($rootScope,$scope,$state){
					//acountId
					$scope.acountId=$state.params.acountId;
					console.log($scope.acountId)
					$scope.articleData={
					pvs:0,
					uvs:0,
					ips:0,
					nowTotalPrice:0,
					readingNumber:0,
					userNowTotalPrice:0
					};
					function initArticleData(){
					$.get("/article/data?acountId="+$scope.acountId,function(data){
            		if(data.code==200){
            		$scope.articleData=data.list[0];
            		$scope.$apply();
            		}
            		});
					}
					initArticleData();
					//日数据初始化
					function initArticleDayData(num){
					var endDate=myUtils.timeStampToDate(new　Date());
					var startDate=myUtils.timeStampToDate(myUtils.beforeDayToTodayTime(num));
					$.get("/article/daydata?startDate="+startDate+"&endDate="+endDate,function(data){
            		if(data.code==200){
            		$scope.articleDayDataList=data.list.reverse();
            		console.log($scope.articleDayDataList)
            		$scope.$apply();
            		}
            		});
					}
					initArticleDayData(6);
					
                    }
                } 
            }
        }) 
        .state("main.articleList", {//文章列表
            url:"/articleList/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_list.html",
                    controller:function($rootScope,$scope,$state){
          
            /*
            *列表
            */
            $scope.articleList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=5;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			//默认acountId,type,isRecommend,fixedRecommend
			$scope.acountId=$state.params.acountId;
			$scope.type=null;
			$scope.isRecommend=null;
			$scope.fixedRecommend=null;
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber,acountId,type,isRecommend,fixedRecommend){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showNoticeListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.articleListInit(acountId,type,isRecommend,fixedRecommend);
			  }
			};
			 /*
            *类型列表
            */
            $scope.articleTypeList=function(holder){
            $.get("/articleCate/list?holder="+holder,function(data){
            $scope["articleCateList"+holder]=data;
            });
            };
            //公共
             $scope.articleTypeList(0);
             if($rootScope.sessionRole.name=='超级管理员' || $rootScope.sessionRole.name=='运营管理员'){
            	 //平台方
            	 $scope.articleTypeList(1);
             }
             //企业号
             $scope.articleTypeList(2);
             //个人号
             $scope.articleTypeList(3);
            /*
            *类型列表end
            */
			$scope.articleListInit=function(acountId,type,isRecommend,fixedRecommend){
			var countUrl="/article/count";
			var listUrl="/article/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber;
			var i=0;//控制计数器
			function params(name,value){
			if(value){
			i++;
			if(i==1){
			countUrl+="?"+name+"="+value;
			}else{
			countUrl+="&"+name+"="+value;
			}
			listUrl+="&"+name+"="+value;
			}
			}
			params("acountId",acountId);
			params("type",type);
			params("isRecommend",isRecommend);
			params("fixedRecommend",fixedRecommend);
			
			  $.get(countUrl,function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          //当前页码
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
          
        //初始化
  $.get(listUrl,function(pld){
           $scope.articleList=pld;
			 console.log($scope.articleList)
			$scope.$apply();
               });
       });
			};
			$scope.articleListInit($scope.acountId,$scope.type,$scope.isRecommend,$scope.fixedRecommend);
			/*
            *列表end
            */
            /*
            *根据参数获取列表
            */
            $scope.articleListByParams=function(acountId,type,isRecommend,fixedRecommend){
            $scope.acountId=acountId;
            $scope.type=type;
            $scope.isRecommend=isRecommend;
            $scope.fixedRecommend=fixedRecommend;
            $scope.currentPage=1;//重置
            $scope.articleListInit($scope.acountId,$scope.type,$scope.isRecommend,$scope.fixedRecommend);
            };
            /*
            *根据参数获取列表end
            */
			/*
            *数据
            */
            $scope.dataByArticle=function(articleId){
            $state.go("main.dataByArticle",{articleId:article.articleId});
            };
			/*
            *数据end
            */
			/*
            *修改
            */
            $scope.updateArticle=function(article){
            $state.go("main.articleUpdate",{articleId:article.articleId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delArticle=function(article){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get("/article/delete?articleId="+article.articleId,function(data){
       					console.log($scope.articleList)
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
			/*
            *删除end
            */
           /*
            *修改
            */
            $scope.updateArticle=function(article){
            $state.go("main.articleUpdate",{article:JSON.stringify({acountId:article.acountId,articleId:article.articleId})});
            };
			/*
            *修改end
            */
                    }
                } 
            }
        }) 
        .state("main.articleAdd", {//文章增加
            url:"/articleAdd/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_add.html",
                    controller:function($rootScope,$scope,$state){
            		$scope.article={
            		acountId:$state.params.acountId||1000,
            		content:'',
            		unitPrice:20,
            		totalPrice:20000000,
            		totalPrice:20000000,
            		userUnitPrice:2,
            		isRecommend:0,
            		fixedRecommend:0,
            		model:'2',//默认2ip计费
            		status:"正常",
            		imgList:[]};
            		//$scope.selectState=1;
            		var editor=$rootScope.myWangEditor("articleAddEditor");
				    
                     
                 		//上传文章封面图片
                 		$("#articleImgFileUpload").change(function(){
                 			//最多上传三张
                 			 if($scope.article.imgList.length>=3){
                 			 myUtils.myLoadingToast("最多上传三张");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#articleImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#articleImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:"http://"+ $rootScope.imgUploadDomain+"/img/add",
                 				        success:function(data){
				                 			var img={
				                 			imgId:'',
				                 			imgAddress:'',
				                 			number:'',
				                 			articleId:''
				                 			};
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				            img.number=$scope.article.imgList.length+1;
                 				           img.imgAddress=data;
                 				            $scope.article.imgList.push(img);
                 				            console.log(JSON.parse(angular.toJson($scope.article.imgList)))
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
            		$scope.delImg=function(img){
            		myUtils.myLoginOut("确定删除吗？", function(){
       				$.get("/img/delete?imgId="+img.imgId,function(data){
       					console.log($scope.imgList)
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
                    $scope.addArticleForm=function(){
                    if(parseFloat($scope.article.unitPrice)<0.2){
                    return myUtils.myLoadingToast("单价最低为0.2元");
                    return ;
                    }
                    if(parseFloat($scope.article.totalPrice)<2000){
                    return myUtils.myLoadingToast("总价最低为2000元");
                    return ;
                    }
                    $scope.article.content =editor.$txt.html();
                    	$.ajax({
						  url: "/article/add",
						  type: 'POST',
						  contentType:"application/json",
						  data: angular.toJson($scope.article),
						  success: function(data){
						  if(data.code==200){
       	   				$scope.article={
            		acountId:$state.params.acountId||1000,
            		content:'',
            		isRecommend:0,
            		fixedRecommend:0,
            		model:'CPC',
            		status:"正常",
            		imgList:[]};
       	   				editor.$txt.html("<p><br/></p>");
       	   				$scope.myAddArticleForm.$setPristine();
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
        .state("main.articleUpdate", {//文章更新
            url:"/articleUpdate/:article",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/article/article_update.html",
                    controller:function($rootScope,$scope,$state){
                    $scope.aritlceParams=JSON.parse($state.params.article);
                  	if(!$scope.aritlceParams.acountId||!myUtils.userVerification.catNum.test($scope.aritlceParams.acountId)){
                  	$state.go("main.articleList",{acountId:null});
                  	return;
                  	}
                  	if(!$scope.aritlceParams.articleId||!myUtils.userVerification.catNum.test($scope.aritlceParams.articleId)){
                  	$state.go("main.articleList",{acountId:$state.params.acountId});
                  	return;
                  	}
                  	$scope.article={
            		acountId:$scope.aritlceParams.acountId,
            		articleId:$scope.aritlceParams.articleId,
            		content:'',
            		status:"正常",
            		imgList:[]};
            		var editor=$rootScope.myWangEditor("articleUpdateEditor");
            		/*
            		*初始化article
            		*/
                    $scope.updateInit=function(articleId){
                    $.get("/article/"+articleId,function(data){
                    console.log(data)
       	   				if(data.code==200){
       	   				$scope.article=data.list[0];
       	   				if($scope.article.redirectUrl){
       	   				$scope.selectState=1;
       	   				}else{
       	   				$scope.selectState=2;
       	   				
       	   				}
       	   				editor.$txt.html($scope.article.content);
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.article.articleId);
                    /*
            		*初始化article end
            		*/
            		//上传文章封面图片
                 		$("#articleImgFileUpload").change(function(){
                 			//最多上传三张
                 			 if($scope.article.imgList.length>=3){
                 			 myUtils.myLoadingToast("最多上传三张");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#articleImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#articleImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:"http://"+ $rootScope.imgUploadDomain+"/img/add",
                 				        success:function(data){
				                 			var img={
				                 			imgId:'',
				                 			imgAddress:'',
				                 			number:'',
				                 			articleId:''
				                 			};
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				            img.number=$scope.article.imgList.length+1;
                 				           img.imgAddress=data;
                 				            $scope.article.imgList.push(img);
                 				            console.log(JSON.parse(angular.toJson($scope.article.imgList)))
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
            		$scope.delImg=function(img){
            		myUtils.myLoginOut("确定删除吗？", function(){
       				$.get("/img/delete?imgId="+img.imgId,function(data){
       					console.log($scope.imgList)
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
            		/*
            		*修改article提交
            		*/
            		$scope.updateArticleForm=function(){
            		$scope.article.content =editor.$txt.html();
       	   				$.ajax({
						  url: "/article/update",
						  type: 'POST',
						  contentType:"application/json",
						  data: angular.toJson($scope.article),
						  success: function(data){
						  if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
            		};
            		/*
            		*修改article提交 end
            		*/
                    }
                } 
            }
        });
     	});	