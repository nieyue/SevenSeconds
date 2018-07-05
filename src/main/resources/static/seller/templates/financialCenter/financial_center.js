/**
 * 金融JS
 */
//初始化书籍频道
mainApp.value('financialRecommend',[
	{id:0,value:'不推'},
	{id:1,value:'封推'},
	{id:2,value:'首投推'},
	{id:3,value:'复投推'}
	]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.FinancialCenter;//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
     	$stateProvider
     	.state("main.financialCateList", {//书类型列表
            url:"/financialCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.financialCateList=[]; 		
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
			  //$scope.showfinancialCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.financialCateListInit();
			  }
			};
			$scope.financialCateListInit=function(){
			  $.get(requestDomainUrl+"/financialCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/financialCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.financialCateList=pld;
			 console.log($scope.financialCateList)
			$scope.$apply();
               });
       });
			};
			$scope.financialCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateFinancialCate=function(financialCate){
            $state.go("main.financialCateUpdate",{financialCateId:financialCate.financialCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delFinancialCate=function(financialCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/financialCate/delete?financialCateId="+financialCate.financialCateId,function(data){
       					console.log($scope.financialCateList)
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
                    }
                } 
            }
        })
     	.state("main.financialCateAdd", {//书类型增加
            url:"/financialCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.financialCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addFinancialCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/financialCate/add",
						  type: 'POST',
						  data: $scope.financialCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.financialCate={
									    name:''
									    };
       	   				$scope.myAddFinancialCateForm.$setPristine();
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
        .state("main.financialCateUpdate", {//更新
            url:"/financialCateUpdate/:financialCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数financialCateId
            		*/
                    $scope.updateFinancialCateId=$state.params.financialCateId;
                    console.log($scope.updateFinancialCateId)
                     if(!$scope.updateFinancialCateId||!myUtils.userVerification.catNum.test($scope.updateFinancialCateId)){
                    $state.go("main.financialCateList");
                    return;
                    } 
                    /*
            		*获取参数financialCateId end
            		*/
            		/*
            		*初始化financialCate
            		*/
                    $scope.updateInit=function(financialCateId){
                    $.get(requestDomainUrl+"/financialCate/"+financialCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.financialCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateFinancialCateId);
                    /*
            		*初始化financialCate end
            		*/
            		/*
            		*修改financialCate提交
            		*/
            		$scope.updateFinancialCateForm=function(){
            		$.post(requestDomainUrl+"/financialCate/update",
            				JSON.parse(angular.toJson($scope.financialCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改financialCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.financialList", {//列表
            url:"/financialList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_list.html",
                    controller:function($rootScope,$scope,$state,financialRecommend){
                    	$scope.financialRecommend=financialRecommend;//注入
            //列表
            
            $scope.financialList=[]; 		
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
			  //$scope.showfinancialCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.financialListInit();
			  }
			};
			$scope.financialListInit=function(){
			  $.get(requestDomainUrl+"/financial/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/financial/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.financialList=pld;
			 console.log($scope.financialList)
			$scope.$apply();
               });
       });
			};
			$scope.financialListInit();
			
            //列表end
            
			
            //修改
            
            $scope.updateFinancial=function(financial){
            $state.go("main.financialUpdate",{financialId:financial.financialId});
            };
			
            //修改end
            
			
            //删除
            
            $scope.delfinancial=function(financial){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/financial/delete?financialId="+financial.financialId,function(data){
       					console.log($scope.financialList)
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
        .state("main.financialUpdate", {//更新
            url:"/financialUpdate/:financialId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_update.html",
                    controller:function($rootScope,$scope,$state,financialRecommend){
                    	$scope.financialRecommend=financialRecommend;//注入
            		//获取参数financialId
            		
                    $scope.updateFinancialId=$state.params.financialId;
                   // console.log($scope.updateFinancialId)
                     if(!$scope.updateFinancialId||!myUtils.userVerification.catNum.test($scope.updateFinancialId)){
                    $state.go("main.financialList");
                    return;
                    } 
                    
            		//获取参数financialId end
            		
                    
                     //获取参数financialCate 
                     
                    $scope.financialCateListInit=function(){
                        $.get(requestDomainUrl+"/financialCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.financialCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.financialCateListInit();
                    
                     //获取参数financialCate end
                     
            		
            		//初始化financial
            		
                    $scope.updateInit=function(financialId){
                    $.get(requestDomainUrl+"/financial/"+financialId,function(data){
                    	if(data.code==200){
                    		$scope.financial=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateFinancialId);
                    
            		//初始化financialCate end
            		
            		
            		//修改financial提交
            		
            		$scope.updateFinancialForm=function(){
            			console.log($scope.financial)
            		$.ajax({
            			url:requestDomainUrl+"/financial/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.financial),
                    	//data:JSON.parse(angular.toJson($scope.financial)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		
            		//修改financial提交 end
            		 
            		//上传书图片
            		//上传文章封面图片
             		$("#financialImgFileUpload").change(function(){
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
             				    {inputfile:$("#financialImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#financialImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.financial.imgAddress=data;
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
        .state("main.financialAdd", {//书增加
            url:"/financialAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/financialCenter/financial_add.html",
                    controller:function($rootScope,$scope,$state,financialRecommend){
                    $scope.financialRecommend=financialRecommend;//注入
				    $scope.financial={
				    title:'',
				    term:'',
				    recommend:0,
				    imgAddress:'',
				    strategy:'',
				    annualTransformation:0,
				    netAddInterest:0,
				    comprehensiveAnnualTransformation:0,
				    projectTotalMoney:0,
				    investmentMoney:0,
				    startInvestmentMoney:0,
				    interestMethod:'',
				    repaymentMethod:'',
				    redirectUrl:'',
				    financialCateId:'',
				    status:1
				    };
                     //获取参数financialCate 
                    $scope.financialCateListInit=function(){
                        $.get(requestDomainUrl+"/financialCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.financialCateList=data.list;
           	   			$scope.financial.financialCateId=$scope.financialCateList[0].financialCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.financialCateListInit();
                     //获取参数financialCate end
                     
                		//上传文章封面图片
                 		$("#financialImgFileUpload").change(function(){
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
                 				    {inputfile:$("#financialImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#financialImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.financial.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                        
                     //表单提交
                    $scope.addFinancialForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/financial/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.financial),
						  success: function(data){
						  if(data.code==200){
							  $scope.financial={
									    title:'',
									    term:'',
									    recommend:0,
									    imgAddress:'',
									    strategy:'',
									    annualTransformation:0,
									    netAddInterest:0,
									    comprehensiveAnnualTransformation:0,
									    projectTotalMoney:0,
									    investmentMoney:0,
									    startInvestmentMoney:0,
									    interestMethod:'',
									    repaymentMethod:'',
									    redirectUrl:'',
									    financialCateId:$scope.financialCateList[0].financialCateId,
									    status:1
									    };
       	   				$scope.myAddFinancialForm.$setPristine();
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
        ;
     	});	