//封装了wangEditor、图片上传等组件
var newsUtils={
		domain:function(){
			var flag=true;//shengchan
			//var flag=false;
		function domainFtn(flag){
			var domain={};
			if(flag){
		domain={
			//SevenSeconds:'www.newzhuan.cn'
			  SevenSeconds:'39.108.60.100:8015'
		};	
			}else{
		domain={
				SevenSeconds:'localhost:8080'
			};	
			}
			return domain;
			
		}	
		return domainFtn(flag);
		},
		//1、创建wangEditor编辑器  需要传入创建编辑器的div 对象
		createEditor:function(e){
			var editor = new wangEditor(e);
			//editor.config.uploadImgUrl = 'http://localhost:8080/bbs/';//editorUpload
			editor.config.uploadImgUrl = 'http://localhost:8080/background/news/pic';//editorUpload
			editor.config.uploadImgFileName = 'fileName';
			editor.create();
		    return editor;
		},
		
		//2、图片上传  需要传入点击的file的对象,和旁边显示图片名字的文本框对象,form表单对象，以及图片显示的div对象
		createPicUpload:function(e1,e2,e3,e4){
			//建一个数组，用于接收返回的图片url，count用于标记图片张数
			var picArray=new Array();
			var count=0;
			//图片 
			$(e1).change(function(){
				//初始化图片格式、大小 
				var options={
						picType:["jpg","jpeg","gif","png","bmp"],
						picSize:1
				}
				var file=$(e1).get(0).files[0];
				//判断是否上传了图片
				if(typeof(file)=="undefined"){
					alert("请上传图片！");
					return false;
				}
				//图片格式判断 
				var fileType=file.type.substr(file.type.lastIndexOf("/")+1);
				//console.log(file);
				if($.inArray(fileType,options.picType)<0){
					alert("图片格式错误!");
					return false;
				} 
				//图片大小判断 
				var size=file.size;
				if(Math.round(size/1024*100/1024)/100>options.picSize){//B 转为MB
					alert("图片大小不能超过1MB");
					return false;
				}
				//判断图片张数，限制最多只能上传3张图片 
				console.log(picArray.length);
				if(picArray.length>2){
					alert("最多只能上传3张图片！"); 
					return false;
				}
				//获取图片名字，如果验证通过了，那么显示图片名字    
				//console.log(file.name);
				$(e2).val(file.name);
				//如果验证通过了，传到后台，并上传到服务器 ,并返回URL，进行显示 
				var formDate=new FormData($(e3)[0]);
				$.ajax({
					url:"/background/news/img",
					data:formDate,
					type:"post",
					enctype:'multipart/form-data',
					timeout: 10000,  //1秒 
					contentType: false,  //告诉jQuery不要去处理发送的数据
			        processData: false,  //告诉jQuery不要去设置Content-Type请求头 
					success:function(result){
						if(result.code==100){
							//获取图片url，添加到数组中 
							var url=result.extend.msg;
							picArray.push(url); 
							//从数组中获取图片的url，动态及时显示上传的图片  
							$(e4).append("<img alt='' src='"+picArray[count]+"'/>");
							count++;
						}else{
							alert(result.extend.msg);
						}
					}
				});
				//var localUrl=window.URL.createObjectURL(file);  //存放在本地的位置：也就是浏览器缓存空间上  
				//$("#ig1").attr("src",localUrl);
				//$("#ig1").css("display","block");
			});
			return picArray;
		},
		
		//3、封装mouseover mouseout click
		creatDys:function(e1,e2,count){
			
			$(e1).find(e2)[count].style.backgroundColor="#584b46";
			
			$(e1).find(e2).each(function(i,e){
				$(e).on({
					mouseover:function(){
						$(e).css({"background-color":"#584b46"});
						/* document.querySelector("#"+id).style.backgroundColor="#f8f8f8";
						document.querySelector("#"+id).style.borderRadius="5px";
						//document.querySelector("#"+id).find("a").style.color="white";
						$("#"+id).find("a").css("color","#333"); */
					},
					mouseout:function(){
						//console.log(count);
						/*if(i==3){
							$(".hide_div").css("display","none");
						}*/
						if(i==count&&i!=0){
							$(e).css({"background-color":"#584b46"});
							return;
						}
						$(e).css("background-color","");
						
					},
					click:function(){
						var id=$(e).attr("id");
						if(id.indexOf("info1")!=-1){
							window.location.href="index.html";
						}
						if(id.indexOf("info2")!=-1){
							window.location.href="index.html";	
						}
						if(id.indexOf("info3")!=-1){
							window.location.href="aboutUs.html";
						}
						if(id.indexOf("info4")!=-1){
							//window.location.href="newsDynamic.html";
							$("#up_p,#down_p").toggle();
							$(".hide_div").slideToggle();
						}
						if(id.indexOf("info5")!=-1){
							window.location.href="customerMessage.html";
						}
						if(id.indexOf("info6")!=-1){
							window.location.href="contactUs.html";
						}
						/*$(e1).find(e2)[count].style.backgroundColor="";
						count=i;
						if(i==0){
							$(e1).find(e2)[count+1].style.backgroundColor="#584b46";
							count=1;
						}
						$(e1).find(e2)[count].style.backgroundColor="#584b46";*/
					}
				});
			});
		}
}

	 









