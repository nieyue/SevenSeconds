//封装了轮播图操作js
var slideshow={
		scroll:function(){ 
	        function animate(obj,target){
			// 首先清除掉定时器
			clearInterval(obj.timer);
			//console.log(target);
			// 用来判断 是+ 还是 -  即说明向左走还是向右走
			
			var speed = obj.offsetLeft < target ? 15 : -20;
			//console.log(obj.offsetLeft);
			obj.timer = setInterval(function(){
				var result = target - obj.offsetLeft;//它们的差值不会超过speed
				//console.log(result);
				//console.log(speed);
				//console.log(obj.offsetLeft);
				obj.style.left = obj.offsetLeft + speed + "px";
				//console.log(obj.style.left);
				// 有可能有小数的存在，所以在这里要做个判断 			
		        if (Math.abs(result) <= Math.abs(speed)) {
		        	clearInterval(obj.timer);
		        	obj.style.left = target + "px";
		        }
			},10);
	        }
	       //获得元素 
	       var scroll = document.getElementById("scroll"); // 获得大盒子
	       var ul = document.getElementById("ul"); // 获得ul
	       var ulLis = ul.children;// 获得ul的盒子 以此来生成ol中li的个数
	       var liWidth = ul.children[0].offsetWidth;// 获得每个li的宽度
	       //console.log(liWidth);
	       // 操作元素
	       // 因为要做无缝滚动，所以要克隆第一张，放到最后一张后面
	       // 1. 克隆元素
	       ul.appendChild(ul.children[0].cloneNode(true));
	       
	       // 2.创建ol 和li
		var ol = document.createElement("ol");//创建ol元素
		scroll.appendChild(ol);// 把ol放到scroll盒子里面去
		for (var i=0;i<ulLis.length-1;i++) {
			var li = document.createElement("li");// 创建li元素
			li.innerHTML = i + 1;// 给li里面添加文字  1 2 3 4 5
			ol.appendChild(li);// 将li元素添加到ol里面
		}
		ol.children[0].className = "current";// ol中的第一个li背景色为purple

	       // 3. 动画开始  (鼠标经过第几个小圆点，就要展示第几张图片，并且小圆点的颜色也发生变化)
		var olLis = ol.children;
		for (var i = 0; i < olLis.length;i++) {
			// 给ol中的每个li添加一个属性 index  用来获得当前第几个li的索引号
			olLis[i].index = i;
			olLis[i].onmouseover = function(){
				for (var j = 0;j<olLis.length;j++) {
					// 清空所有的 类名
					olLis[j].className = "";
				}
				// 给当前的li添加一个类名
				this.className = "current";
				
				// 接着调用动画函数 ，根据第几个li 展示第几张图片 第一个参数谁做动画  第二个参数 走多少
				animate(ul,-this.index * liWidth);
				// 接着让小圆点和 key 等于当前的索引号
				circle = key = this.index;
			}
		}


	       // 4. 添加定时器  让图片自动轮播
	       var timer = null; // 轮播图的定时器
	       var key = 0;// 控制播放的张数
	      var circle = 0;// 控制小圆点
	       
	       timer = setInterval(autoplay,4000);// 自动轮播
	       
	       function autoplay(){
	           //console.log(ul.style.left);
	           /*自动轮播时,要对播放的张数key进行一个判断,如果播放的张数超过ulLis.length-1,
	           就要重头开始播放.  因为我们克隆了第一张并将其放在最后面,所以我们要从第二张图片开始播放*/
	           key++; // 先++
	           if(key > ulLis.length-1){// 后判断
	               
	               var u=ul.style.left = 0; // 迅速调回
	               //console.log(u);
	               key = 1; // 因为第6张是第一张，所以播放的时候是从第2张开始播放
	           }
	           // 动画部分
	           //console.log(-key*liWidth);
	           animate(ul,-key*liWidth);

	           // 小圆点circle   当显示第几张图片是，对应的小圆点的颜色也发生变化 
			
			/*同理,对小圆点也要有一个判断*/
			circle++;
			if (circle > olLis.length-1) {
				circle = 0;
			}
			// 小圆点颜色发生变化
			for (var i = 0 ; i < olLis.length;i++) {
				// 先清除掉所用的小圆点类名
				olLis[i].className = ""; 
			}
			// 给当前的小圆点 添加一个类名
			olLis[circle].className = "current";
			
		}
		
		// 最后，鼠标经过大盒子停止定时器
		scroll.onmouseover = function(){
			clearInterval(timer)
		}
		// 鼠标离开大盒子时 开启定时器
		scroll.onmouseout = function(){
			timer = setInterval(autoplay,4000);  
			
		}   	  	

	 }  	  	
	 	
}