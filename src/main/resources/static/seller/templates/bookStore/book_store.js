/**
 * 书城JS
 */
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.BookStore;//请求数据url
	//var requestDomainUrl="http://"+location.hostname+":8082";//请求数据url
	//var requestDomainUrl="http://book.newzhuan.cn";//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	//var imgUploadDomainUrl="http://img.newzhuan.cn";//请求图片上传url
	
     	$stateProvider
     	.state("main.bookCateList", {//书类型列表
            url:"/bookCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.bookCateList=[]; 		
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
			  //$scope.showbookCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.bookCateListInit();
			  }
			};
			$scope.bookCateListInit=function(){
			  $.get(requestDomainUrl+"/bookCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/bookCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.bookCateList=pld;
			 console.log($scope.bookCateList)
			$scope.$apply();
               });
       });
			};
			$scope.bookCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateBookCate=function(bookCate){
            $state.go("main.bookCateUpdate",{bookCateId:bookCate.bookCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delbookCate=function(bookCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/bookCate/delete?bookCateId="+bookCate.bookCateId,function(data){
       					console.log($scope.bookCateList)
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
     	.state("main.bookCateAdd", {//书类型增加
            url:"/bookCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.bookCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addBookCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/bookCate/add",
						  type: 'POST',
						  data: $scope.bookCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.bookCate={
									    name:''
									    };
       	   				$scope.myAddBookCateForm.$setPristine();
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
        .state("main.bookCateUpdate", {//更新
            url:"/bookCateUpdate/:bookCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数bookCateId
            		*/
                    $scope.updateBookCateId=$state.params.bookCateId;
                    console.log($scope.updateBookCateId)
                     if(!$scope.updateBookCateId||!myUtils.userVerification.catNum.test($scope.updateBookCateId)){
                    $state.go("main.bookCateList");
                    return;
                    } 
                    /*
            		*获取参数bookCateId end
            		*/
            		/*
            		*初始化bookCate
            		*/
                    $scope.updateInit=function(bookCateId){
                    $.get(requestDomainUrl+"/bookCate/"+bookCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.bookCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateBookCateId);
                    /*
            		*初始化bookCate end
            		*/
            		/*
            		*修改bookCate提交
            		*/
            		$scope.updateBookCateForm=function(){
            		$.post(requestDomainUrl+"/bookCate/update",
            				JSON.parse(angular.toJson($scope.bookCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改bookCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.bookList", {//列表
            url:"/bookList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_list.html",
                    controller:function($rootScope,$scope,$state){
            
            //列表
            
            $scope.bookList=[]; 		
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
			  //$scope.showbookCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.bookListInit();
			  }
			};
			$scope.bookListInit=function(){
			  $.get(requestDomainUrl+"/book/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/book/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.bookList=pld;
			 console.log($scope.bookList)
			$scope.$apply();
               });
       });
			};
			$scope.bookListInit();
			
            //列表end
            
			
            //修改
            
            $scope.updateBook=function(book){
            $state.go("main.bookUpdate",{bookId:book.bookId});
            };
			
            //修改end
            
			
            //删除
            
            $scope.delBook=function(book){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/book/delete?bookId="+book.bookId,function(data){
       					console.log($scope.bookList)
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
        .state("main.bookUpdate", {//更新
            url:"/bookUpdate/:bookId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_update.html",
                    controller:function($rootScope,$scope,$state){
            		//获取参数bookId
            		
                    $scope.updateBookId=$state.params.bookId;
                   // console.log($scope.updateBookId)
                     if(!$scope.updateBookId||!myUtils.userVerification.catNum.test($scope.updateBookId)){
                    $state.go("main.bookList");
                    return;
                    } 
                    
            		//获取参数bookId end
            		
                    
                     //获取参数bookCate 
                     
                    $scope.bookCateListInit=function(){
                        $.get(requestDomainUrl+"/bookCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.bookCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.bookCateListInit();
                    
                     //获取参数bookCate end
                     
            		
            		//初始化book
            		
                    $scope.updateInit=function(bookId){
                    $.get(requestDomainUrl+"/book/"+bookId,function(data){
                    	if(data.code==200){
                    		$scope.book=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateBookId);
                    
            		//初始化bookCate end
            		
            		
            		//修改book提交
            		
            		$scope.updateBookForm=function(){
            			console.log($scope.book)
            		$.ajax({
            			url:requestDomainUrl+"/book/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.book),
                    	//data:JSON.parse(angular.toJson($scope.book)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		
            		//修改book提交 end
            		 
            		//上传书图片
            		//上传文章封面图片
             		$("#bookImgFileUpload").change(function(){
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
             				    {inputfile:$("#bookImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#bookImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.book.imgAddress=data;
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
        .state("main.bookAdd", {//书增加
            url:"/bookAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.book={
				    title:'',
				    bookCateId:'',
				    bookImgAddress:'',
				    summary:'',
				    author:'',
				    recommend:0,
				    cost:0,
				    status:1
				    };
                     //获取参数bookCate 
                    $scope.bookCateListInit=function(){
                        $.get(requestDomainUrl+"/bookCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.bookCateList=data.list;
           	   			$scope.book.bookCateId=$scope.bookCateList[0].bookCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.bookCateListInit();
                     //获取参数bookCate end
                     
                		//上传文章封面图片
                 		$("#bookImgFileUpload").change(function(){
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
                 				    {inputfile:$("#bookImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#bookImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.book.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                        
                     //表单提交
                    $scope.addBookForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/book/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.book),
						  success: function(data){
						  if(data.code==200){
							  $scope.book={
									    title:'',
									    bookCateId:$scope.bookCateList[0].bookCateId,
									    bookImgAddress:'',
									    summary:'',
									    author:'',
									    recommend:0,
									    cost:0,
									    status:1
									    };
       	   				$scope.myAddBookForm.$setPristine();
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
           .state("main.bookChapterList", {//列表
            url:"/bookChapterList/:bookId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_chapter_list.html",
                    controller:function($rootScope,$scope,$state){
                    	//获取参数bookId
                        $scope.bookId=$state.params.bookId;
                       // console.log($scope.updateBookId)
                         if(!$scope.bookId||!myUtils.userVerification.catNum.test($scope.bookId)){
                        $state.go("main.bookList");
                        return;
                        } 
                		//获取参数bookId end
            //列表
            $scope.bookChapterList=[]; 		
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
			  //$scope.showbookCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.bookChapterListInit();
			  }
			};
			$scope.bookChapterListInit=function(){
			  $.get(requestDomainUrl+"/bookChapter/count?bookId="+$scope.bookId,function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/bookChapter/list?bookId="+$scope.bookId+"&pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.bookChapterList=pld;
			 console.log($scope.bookChapterList)
			$scope.$apply();
               });
       });
			};
			$scope.bookChapterListInit();
            //列表end
            
			
            //修改
            $scope.updateBookChapter=function(bookChapter){
            $state.go("main.bookChapterUpdate",{bookId:bookChapter.bookId,bookChapterId:bookChapter.bookChapterId});
            };
            //修改end
            
            //删除
            $scope.delBookChapter=function(bookChapter){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/bookChapter/delete?bookChapterId="+bookChapter.bookChapterId,function(data){
       					console.log($scope.bookChapterList)
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
         .state("main.bookChapterAdd", {//书章节增加
            url:"/bookChapterAdd/:bookId/:number",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_chapter_add.html",
                    controller:function($rootScope,$scope,$state){
            	//获取参数bookId,number
                $scope.bookId=$state.params.bookId;
                $scope.number=$state.params.number;
                 if(!$scope.bookId||!myUtils.userVerification.catNum.test($scope.bookId)){
                $state.go("main.bookList");
                return;
                } 
                 //含0正整数
                 if(!$scope.number||!myUtils.userVerification.postNum.test($scope.number)){
                	 $state.go("main.bookChapterList",{bookId:$scope.bookId});
                	 return;
                 } 
                 if($scope.number==99999999){
                	 $scope.number=0;
                 }else{
                $scope.number=parseInt($scope.number)+1;//最新章
                 }
        		//获取参数bookId,number end
				    $scope.bookChapter={
				    number:$scope.number,
				    title:'',
				    content:'',
				    cost:0,
				    status:1,
				    bookId:$scope.bookId
				    };
                        
                     //表单提交
                    $scope.addBookChapterForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/bookChapter/add",
						  type: 'POST',
						 // contentType:'application/json',
						  data: JSON.parse(angular.toJson($scope.bookChapter)),
						  success: function(data){
						  if(data.code==200){
							  console.log($scope.bookId)
							  $scope.number=parseInt($scope.number)+1;
							  $scope.bookChapter={
									    number:$scope.number,
									    title:'',
									    content:'',
									    cost:0,
									    status:1,
									    bookId:$scope.bookId
									    };
       	   				$scope.myAddBookChapterForm.$setPristine();
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
         .state("main.bookChapterUpdate", {//更新
            url:"/bookChapterUpdate/:bookId/:bookChapterId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_chapter_update.html",
                    controller:function($rootScope,$scope,$state){
            		//获取参数bookChapterId
            		
                    $scope.bookId=$state.params.bookId;
                    $scope.bookChapterId=$state.params.bookChapterId;
                     if(!$scope.bookChapterId||!myUtils.userVerification.catNum.test($scope.bookChapterId)){
                    $state.go("main.bookChapterList");
                    return;
                    } 
            		//获取参数bookChapterId end
            		
            		//初始化bookChapter
                    $scope.updateInit=function(bookChapterId){
                    $.get(requestDomainUrl+"/bookChapter/"+bookChapterId,function(data){
                    	if(data.code==200){
                    		$scope.bookChapter=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.bookChapterId);
            		//初始化bookChapter end
            		
            		
            		//修改bookChapterId提交
            		$scope.updateBookChapterForm=function(){
            			console.log($scope.bookChapter)
            		$.ajax({
            			url:requestDomainUrl+"/bookChapter/update",
            			type:'POST',
            			//contentType:'application/json',
                    	data:$scope.bookChapter,
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		//修改bookChapter提交 end
                    }
                } 
            }
        })
        .state("main.bookOrderList", {//列表
            url:"/bookOrderList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_order_list.html",
                    controller:function($rootScope,$scope,$state){
            //列表
            $scope.bookOrderList=[]; 		
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
			  //$scope.showbookCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.bookOrderListInit();
			  }
			};
			$scope.bookOrderListInit=function(){
			  $.get(requestDomainUrl+"/bookOrder/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/bookOrder/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.bookOrderList=pld;
			 console.log($scope.bookOrderList)
			$scope.$apply();
               });
       });
			};
			$scope.bookOrderListInit();
			
            //列表end
            
			
            //修改订单状态 状态，状态，0已下单，1已支付，2申请退款，3已退款，4拒绝退款,5已完成
            
			//$scope.showStatus=0;//默认0显示结果，1，显示修改，
			$scope.changeStatus=function(bookOrderDetail){
				bookOrderDetail.$$status=1;//默认0显示结果，1，显示修改，
				//$scope.showStatus=1;
			};
			$scope.bookOrderDetailStatus=[
				{id:0,value:'已下单'},
				{id:1,value:'已支付'},
				{id:2,value:'申请退款'},
				{id:3,value:'已退款'},
				{id:4,value:'拒绝退款'},
				{id:5,value:'已完成'}
				];
            $scope.updateBookOrderDetailStatus=function(bookOrderDetail){
            	$.get(requestDomainUrl+"/bookOrderDetail/update",
            			{
            				bookOrderDetailId:bookOrderDetail.bookOrderDetailId,
            				status:bookOrderDetail.status
            			}
            			,function(data){
                	if(data.code==200){
                		location.reload();
                		myUtils.myLoadingToast("加载成功" ); 
                	}else{
                		myUtils.myLoadingToast("加载失败");   	   				
                	}
                });
            };
			
            //修改end
            
                    }
                } 
            }
        })
        .state("main.bookOrderAcount", {//书订单人
            url:"/bookOrderAcount/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/bookStore/book_order_acount.html",
                    controller:function($rootScope,$scope,$state){
                  	
            		//获取参数acountId
                    $scope.acountId=$state.params.acountId;
                     if(!$scope.acountId||!myUtils.userVerification.catNum.test($scope.acountId)){
                    $state.go("main.bookOrderList");
                    return;
                    } 
            		//获取参数acountId end
            		
            		
            		//初始化acount
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
            		//初始化acount end
            		
                    }
                } 
            }
        });
     	});	