/**
 * 兑换商城JS
 */
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.ConvertMall;//请求数据url
	//var requestDomainUrl="http://mall.newzhuan.cn";//请求数据url
	//var requestDomainUrl="http://"+location.hostname+":8081";//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	//var imgUploadDomainUrl="http://img.newzhuan.com";//请求图片上传url
	
     	$stateProvider
     	.state("main.merCateList", {//商品类型列表
            url:"/merCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.merCateList=[]; 		
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
			  //$scope.showmerCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.merCateListInit();
			  }
			};
			$scope.merCateListInit=function(){
			  $.get(requestDomainUrl+"/merCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/merCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.merCateList=pld;
			 console.log($scope.merCateList)
			$scope.$apply();
               });
       });
			};
			$scope.merCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateMerCate=function(merCate){
            $state.go("main.merCateUpdate",{merCateId:merCate.merCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delMerCate=function(merCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/merCate/delete?merCateId="+merCate.merCateId,function(data){
       					console.log($scope.merCateList)
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
     	.state("main.merCateAdd", {//商品类型增加
            url:"/merCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.merCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addMerCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/merCate/add",
						  type: 'POST',
						  data: $scope.merCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.merCate={
									    name:''
									    };
       	   				$scope.myAddMerCateForm.$setPristine();
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
        .state("main.merCateUpdate", {//更新
            url:"/merCateUpdate/:merCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数merCateId
            		*/
                    $scope.updateMerCateId=$state.params.merCateId;
                    console.log($scope.updateMerCateId)
                     if(!$scope.updateMerCateId||!myUtils.userVerification.catNum.test($scope.updateMerCateId)){
                    $state.go("main.merCateList");
                    return;
                    } 
                    /*
            		*获取参数merCateId end
            		*/
            		/*
            		*初始化merCate
            		*/
                    $scope.updateInit=function(merCateId){
                    $.get(requestDomainUrl+"/merCate/"+merCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.merCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateMerCateId);
                    /*
            		*初始化merCate end
            		*/
            		/*
            		*修改merCate提交
            		*/
            		$scope.updateMerCateForm=function(){
            		$.post(requestDomainUrl+"/merCate/update",
            				JSON.parse(angular.toJson($scope.merCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改merCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.merList", {//列表
            url:"/merList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.merList=[]; 		
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
			  //$scope.showmerCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.merListInit();
			  }
			};
			$scope.merListInit=function(){
			  $.get(requestDomainUrl+"/mer/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/mer/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.merList=pld;
			 console.log($scope.merList)
			$scope.$apply();
               });
       });
			};
			$scope.merListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateMer=function(mer){
            $state.go("main.merUpdate",{merId:mer.merId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delMer=function(mer){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/mer/delete?merId="+mer.merId,function(data){
       					console.log($scope.merList)
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
        .state("main.merUpdate", {//更新
            url:"/merUpdate/:merId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_update.html",
                    controller:function($rootScope,$scope,$state){
                    var editor=$rootScope.myWangEditor("merUpdateEditor");
                  	/*
            		*获取参数merId
            		*/
                    $scope.updateMerId=$state.params.merId;
                    console.log($scope.updateMerId)
                     if(!$scope.updateMerId||!myUtils.userVerification.catNum.test($scope.updateMerId)){
                    $state.go("main.merList");
                    return;
                    } 
                    /*
            		*获取参数merId end
            		*/
                    /*
                     *获取参数merCate 
                     */
                    $scope.merCateListInit=function(){
                        $.get(requestDomainUrl+"/merCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.merCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.merCateListInit();
                    /*
                     *获取参数merCate end
                     */
            		/*
            		*初始化mer
            		*/
                    $scope.updateInit=function(merId){
                    $.get(requestDomainUrl+"/mer/"+merId,function(data){
                    	if(data.code==200){
                    		$scope.mer=data.list[0];
                    		editor.$txt.html($scope.mer.detail);
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateMerId);
                    /*
            		*初始化merCate end
            		*/
            		/*
            		*修改mer提交
            		*/
            		$scope.updateMerForm=function(){
            		$scope.mer.detail =editor.$txt.html();
            		$.ajax({
            			url:requestDomainUrl+"/mer/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.mer),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		/*
            		*修改mer提交 end
            		*/
            		
            		 /*
            		**上传商品图片
            		*/
                 		$("#merImgFileUpload").change(function(){
                 			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
                 					&&($scope.imgConfigWidth>1200
                 					||$scope.imgConfigWidth<=0
                 					||$scope.imgConfigHeight<=0
                 					||$scope.imgConfigHeight>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			//最多上传八张
                 			 if($scope.mer.merImgList.length>=8){
                 			 myUtils.myLoadingToast("最多上传八张");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#merImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#merImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
				                 			var merImg={
				                 			merImgId:'',
				                 			imgAddress:'',
				                 			orderNum:'',
				                 			merId:''
				                 			};
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           var maxMerImg=$scope.mer.merImgList[$scope.mer.merImgList.length-1];
                 				          if(maxMerImg){
                  				        	 merImg.orderNum=maxMerImg.orderNum+1; 
                  				          }else{
                  				        	  merImg.orderNum=1;
                  				          }
                 				           merImg.imgAddress=data;
                 				            $scope.mer.merImgList.push(merImg);
                 				            console.log(JSON.parse(angular.toJson($scope.mer.merImgList)))
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                 	/**
                 	*删除商品图片
            		*/
            		$scope.delMerImg=function(merImg){
            		myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/merImg/delete?merImgId="+merImg.merImgId,function(data){
       					console.log($scope.merImgList)
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
        .state("main.merAdd", {//商品增加
            url:"/merAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.mer={
				    title:'',
				    merCateId:'',
				    merImgList:[],
				    discount:1,
				    status:1
				    };
				    var editor=$rootScope.myWangEditor("merAddEditor");
				    editor.$txt.html("");
				    /*
                     *获取参数merCate 
                     */
                    $scope.merCateListInit=function(){
                        $.get(requestDomainUrl+"/merCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.merCateList=data.list;
           	   			$scope.mer.merCateId=$scope.merCateList[0].merCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.merCateListInit();
                    /*
                     *获取参数merCate end
                     */
                        /*
                		**上传商品图片
                		*/
                     		$("#merImgFileUpload").change(function(){
                     			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
                     					&&($scope.imgConfigWidth>1200
                     					||$scope.imgConfigWidth<=0
                     					||$scope.imgConfigHeight<=0
                     					||$scope.imgConfigHeight>1200)
                     					){
                     			myUtils.myLoadingToast("图片尺寸不符合");
                    			 return;
                    			 }
                     			//最多上传八张
                     			 if($scope.mer.merImgList.length>=8){
                     			 myUtils.myLoadingToast("最多上传八张");
                     			 return;
                     			 }
                     			myUtils.fileUpload(
                     				    {inputfile:$("#merImgFileUpload"),
                     				    ajaxObj:{
                     				        formData:[
                     				            {key:"editorUpload",value:$("#merImgFileUpload").get(0).files[0]}
                     				            ],
                     				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                     				        success:function(data){
    				                 			var merImg={
    				                 			merImgId:'',
    				                 			imgAddress:'',
    				                 			orderNum:'',
    				                 			merId:''
    				                 			};
                     				            if(data){
                     				            myUtils.myPrevToast("上传成功",null,"remove");
                     				           var maxMerImg=$scope.mer.merImgList[$scope.mer.merImgList.length-1];
                     				          if(maxMerImg){
                     				        	 merImg.orderNum=maxMerImg.orderNum+1; 
                     				          }else{
                     				        	  merImg.orderNum=1;
                     				          }
                     				           merImg.imgAddress=data;
                     				            $scope.mer.merImgList.push(merImg);
                     				            console.log(JSON.parse(angular.toJson($scope.mer.merImgList)))
                     				            $scope.$apply();
                     				            }
                     				        }
                     				    }
                     				}
                     				);
                     		});
                     	/**
                     	*删除商品图片
                		*/
                		$scope.delMerImg=function(merImg){
                		myUtils.myLoginOut("确定删除吗？", function(){
           				$.get(requestDomainUrl+"/merImg/delete?merImgId="+merImg.merImgId,function(data){
           					console.log($scope.merImgList)
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
                    $scope.addMerForm=function(){
                    	$scope.mer.detail =editor.$txt.html();
                    	$.ajax({
						  url: requestDomainUrl+"/mer/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.mer),
						  success: function(data){
						  if(data.code==200){
							  $scope.mer={
									    title:'',
									    merCateId:$scope.mer.merCateId,
									    merImgList:[],
									    discount:1,
									    status:1
									    };
							  editor.$txt.html("");  
       	   				$scope.myAddMerForm.$setPristine();
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
        .state("main.merOrderList", {//列表
            url:"/merOrderList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_order_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.merOrderList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			//查询条件
			$scope.search={
				orderNumber:null,//订单号
				acountId:null,//账户ID
				status:1//状态
			};
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber,search){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showmerCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.merOrderListInit(search);
			  }
			};
			$scope.merOrderListInit=function(search){
				var countUrl=requestDomainUrl+"/merOrder/count";
				//var countUrl=requestDomainUrl+"/merOrder/list?pageNum=1&pageSize=999999999";
				var listUrl=requestDomainUrl+"/merOrder/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber;
				var i=0;//控制计数器
				function params(name,value){
				if(value!==null && value!==""){
				i++;
				if(i==1){
				countUrl+="?"+name+"="+value;
				}else{
				countUrl+="&"+name+"="+value;
				}
				listUrl+="&"+name+"="+value;
				}
				}
				if(search.orderNumber){					
				params("orderNumber",search.orderNumber);
				}
				if(search.acountId){					
					params("acountId",search.acountId);
				}
				/*if(search.status!=null){		
					params("status",search.status);
				}*/
			  $.get(countUrl,function(cd){
				  console.log(cd)
           		//$scope.totalNumber=cd.list.length;             
				  $scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(listUrl,function(pld){
           $scope.merOrderList=pld;
			 console.log($scope.merOrderList)
			$scope.$apply();
               });
       });
			};
			$scope.merOrderListInit($scope.search);
			/*
            *列表end
            */
			/*
			 *查询
			 */
			$scope.searchMerOrderForm=function(search){
				console.log(search)
				 $scope.currentPage=1;//重置
				$scope.toPage( $scope.currentPage, $scope.search);
				//$scope.merOrderListInit($scope.search);
			}
			/*
			 *查询end
			 */
			
			/*
            *修改订单状态 状态，0已下单-未支付，1已支付-未发货，2已发货-未完成，3申请退款，4已退款，5拒绝退款,6已完成
            */
			//$scope.showStatus=0;//默认0显示结果，1，显示修改，
			$scope.changeStatus=function(orderMer){
				orderMer.$$status=1;//默认0显示结果，1，显示修改，
				//$scope.showStatus=1;
			};
			$scope.orderMerStatus=[
				{id:-1,value:'全部'},
				{id:0,value:'已下单-未支付'},
				{id:1,value:'已支付-未发货'},
				{id:2,value:'已发货-未完成'},
				{id:3,value:'申请退款'},
				{id:4,value:'已退款'},
				{id:5,value:'拒绝退款'},
				{id:6,value:'已完成'}
				];
            $scope.updateOrderMerStatus=function(orderMer){
            	$.get(requestDomainUrl+"/orderMer/update",
            			{
            				orderMerId:orderMer.orderMerId,
            				status:orderMer.status
            			}
            			,function(data){
                	if(data.code==200){
                		//location.reload();
                		orderMer.$$status=0;
                		$scope.$apply();
                		myUtils.myLoadingToast("加载成功" ); 
                	}else{
                		myUtils.myLoadingToast("加载失败");   	   				
                	}
                });
            };
			/*
            *修改end
            */
            /*
             *快递公司,0暂无,1顺丰速运SF,2中通快递ZTO,3圆通速递YTO,4申通快递STO,5百世快递BestExpress,6韵达快递YUNDA,7中国邮政EMS,8宅急送ZJS,9FedEx联邦,10京东物流
             *11德邦物流,12安能物流,13优速快递,14天天快递,15汇通快递,16国通快递,17全峰快递,18菜鸟物流
             */
            $scope.changeCourier=function(orderMer){
            	orderMer.$$courier=1;//默认0显示结果，1，显示修改，
            };
            $scope.orderMerCourier=[
            	{id:0,value:'暂无'},
            	{id:1,value:'顺丰速运SF'},
            	{id:2,value:'中通快递ZTO'},
            	{id:3,value:'圆通速递YTO'},
            	{id:4,value:'申通快递STO'},
            	{id:5,value:'百世快递BestExpress'},
            	{id:6,value:'韵达快递YUNDA'},
            	{id:7,value:'中国邮政EMS'},
            	{id:8,value:'宅急送ZJS'},
            	{id:9,value:'FedEx联邦'},
            	{id:10,value:'京东物流'},
            	{id:11,value:'德邦物流'},
            	{id:12,value:'安能物流'},
            	{id:13,value:'优速快递'},
            	{id:14,value:'天天快递'},
            	{id:15,value:'汇通快递'},
            	{id:16,value:'国通快递'},
            	{id:17,value:'全峰快递'},
            	{id:18,value:'菜鸟物流'}
            	];
            $scope.updateOrderMerCourier=function(orderMer){
            	$.get(requestDomainUrl+"/orderMer/update",
            			{
            		orderMerId:orderMer.orderMerId,
            		courierCompany:orderMer.courierCompany,
            		courierNumber:orderMer.courierNumber
            			}
            	,function(data){
            		if(data.code==200){
            			orderMer.$$courier=0;
            			$scope.$apply();
            			//location.reload();
            			myUtils.myLoadingToast("加载成功" ); 
            		}else{
            			myUtils.myLoadingToast("加载失败");   	   				
            		}
            	});
            };
            /*
             *修改end
             */
            /*
             *下载excel
             */
            $scope.downloadMerOrderForm=function(){
            	//location.href=requestDomainUrl+"/merOrder/downloadXls?pageSize=1000000000&status="+$scope.download.id;
            	var url=requestDomainUrl+"/merOrder/downloadXls?pageSize=1000000000";
            	if($scope.search.status!=-1){
            		url+="&status="+$scope.search.status;
            	}
            	myUtils.myPrevToast("加载中",function(){
            	var form=$("<form>");//定义一个form表单  
                 form.attr("style","display:none");  
                 form.attr("target","");  
                 form.attr("method","post");  
                 form.attr("action",url);  
                 var input1=$("<input>");  
                 input1.attr("type","hidden");  
                 //input1.attr("name","exportData");  
                // input1.attr("value",(new Date()).getMilliseconds());  
                 $("body").append(form);//将表单放置在web中  
                 form.append(input1);  
                 form.submit();//表单提交  
                 form.remove();
                 myUtils.myPrevToast("加载完成",null,"remove"); 
         /*   $.ajax({
            		timeout:100000,
            		xhrFields:{
            			withCredentials: true 
            		},
            		url:requestDomainUrl+"/merOrder/downloadXls?pageSize=1000000000&status="+$scope.download.id,
            		success:function(data){
            			//location.href=requestDomainUrl+"/merOrder/downloadXls?pageSize=1000000000&status="+$scope.download.id;
            			//location.href=data;
            			console.log(data)
            			myUtils.myPrevToast("加载完成",null,"remove"); 
            		}
            	});	
             */
            	//location.href=requestDomainUrl+"/merOrder/downloadXls?pageSize=1000000000&status="+$scope.download.id;
            	//myUtils.myPrevToast("加载完成",null,"remove"); 
            	},"add");
            	
            };
            /*
             *下载excelend
             */
                    }
                } 
            }
        })
        .state("main.merOrderAcount", {//商品订单人
            url:"/merOrderAcount/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/convertMall/mer_order_acount.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数acountId
            		*/
                    $scope.acountId=$state.params.acountId;
                     if(!$scope.acountId||!myUtils.userVerification.catNum.test($scope.acountId)){
                    $state.go("main.merOrderList");
                    return;
                    } 
                    /*
            		*获取参数acountId end
            		*/
            		/*
            		*初始化acount
            		*/
                    $scope.acountInit=function(acountId){
                    $.get("/acount/"+acountId,function(data){
       	   				if(data.code==200){
       	   				$scope.acount=data.list[0];
       	   				$scope.$apply();
       	   				console.log($scope.acount)
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.acountInit($scope.acountId);
                    /*
            		*初始化acount end
            		*/
                    }
                } 
            }
        });
     	});	