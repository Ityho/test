
	//��һ������������
	function slideDownStep1(dist){  // dist �»��ľ��룬������������ģ������Ч��
		var slideDown1 = document.getElementById("slideDown1"),
			slideDown2 = document.getElementById("slideDown2");
		slideDown2.style.display = "none";
		slideDown1.style.display = "block";
		slideDown1.style.height = (parseInt("20px") - dist) + "px";
	}
	//�ڶ�����������Ȼ���ɿ���
	function slideDownStep2(){ 
		var slideDown1 = document.getElementById("slideDown1"),
			slideDown2 = document.getElementById("slideDown2");
		slideDown1.style.display = "none";
		slideDown1.style.height = "20px";
		slideDown2.style.display = "block";
		//ˢ������
		//location.reload();
	}
	//��������ˢ����ɣ��ع�֮ǰ״̬
	function slideDownStep3(){ 
		var slideDown1 = document.getElementById("slideDown1"),
			slideDown2 = document.getElementById("slideDown2");
		slideDown1.style.display = "none";
		slideDown2.style.display = "none";
	}


	//contentId��ʾ��������¼��󶨣�way==>x��ʾˮƽ����Ĳ�����y��ʾ��ֱ����Ĳ���
	function k_touch(contentId,way){ 
		var _start = 0,
			_end = 0,
		_content = document.getElementById(contentId);
		_content.addEventListener("touchstart",touchStart,false);
		_content.addEventListener("touchmove",touchMove,false);
		_content.addEventListener("touchend",touchEnd,false);
		function touchStart(event){ 
			//var touch = event.touches[0]; //���ֻ�ȡҲ���ԣ����Ѳ��Ƽ�ʹ��

			var touch = event.targetTouches[0];
			if(way == "x"){ 
				_start = touch.pageX;
			}else{ 
				_start = touch.pageY;
			}
		}
		function touchMove(event){ 
			var touch = event.targetTouches[0];
			if(way == "x"){ 
				_end = (_start - touch.pageX);
			}else{ 
				_end = (_start - touch.pageY);
				//�»���ִ�в���
				if(_end < -50){
					slideDownStep1(_end);
				}
			}

		}
		function touchEnd(event){ 
			if(_end >=-50){ 
				console.log(_start+"�󻬻��ϻ�  "+_end);
			}else if(way=="y"){ 
				console.log(_start+"�һ����»�"+_end);
				pullDownFresh();
				_end = 0;
				//ˢ�³ɹ���
				//ģ��ˢ�³ɹ����������
				//setTimeout(function(){ 
				//	slideDownStep3();
				//},2500);
			}
		}
	}
	