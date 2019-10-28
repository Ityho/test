/**
 * 抽奖
 */

var luck={
	isture: false, //是否正在抽奖
	luckIndex: -1,
	luckDrawName: '',
	index:0,	//当前转动到哪个位置，起点位置
	count:0,	//总共有多少个位置
	timer:0,	//setTimeout的ID，用clearTimeout清除
	speed:20,	//初始转动速度
	times:0,	//转动次数
	cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize:-1,	//中奖位置
	init:function(id){
		if ($("#"+id).find(".luck-itme").length>0) {
			$luck = $("#"+id);
			$units = $luck.find(".luck-itme");
			this.obj = $luck;
			this.count = $units.length;
			$luck.find(".luck-itme-"+this.index).addClass("active");
		};
	},


	roll:function(){
		var index = this.index;
		var count = this.count;
		var luck = this.obj;
		$(luck).find(".luck-itme-"+index).removeClass("active");
		index += 1;
		if (index>count-1) {
			index = 0;
		};
		$(luck).find(".luck-itme-"+index).addClass("active");
		this.index=index;
		return false;
	},
	stop:function(index){
		this.prize=index;
		return false;
	},
	clear:function(){
		clearTimeout(luck.timer);
		luck.prize=-1;
		luck.times=0;
		luck.isture=false; // 标志为 执行完毕
	}
};


function roll(){
	luck.times += 1;
	luck.roll();
	if (luck.times > luck.cycle+10 && luck.prize==luck.index) {	//抽奖停止
		clearTimeout(luck.timer);
		luck.prize=-1;
		luck.times=0;
		luck.isture=false; // 标志为 执行完毕
		$('#luckyDrawResultModal .prize-txt').text('您抽到'+luck.luckDrawName+'!')
		$('#luckyDrawResultModal').modal();
		refreshUser();
		getOtherUserLuckyDrawRecords();
	}else{
		if (luck.times<luck.cycle) {
			luck.speed -= 10;
		}else if(luck.times==luck.cycle) {
			luck.prize = luck.luckIndex;//最终中奖位置
		}else{
			if (luck.times > luck.cycle+10 && ((luck.prize==0 && luck.index==7) || luck.prize==luck.index+1)) {
				luck.speed += 110;
			}else{
				luck.speed += 20;
			}
		}
		if (luck.speed<40) {
			luck.speed=40;
		};

		luck.timer = setTimeout(roll,luck.speed);
	}
	return false;
}


function doLuckyDraw(){
	if(luck.isture)
		return;
	luck.isture = true;
	$.ajax({
		url : actionBase + '/view/usercenterV2/doLuckyDraw.action',
		type : 'POST',
		success : function(result){
			if(result != null){
				if(result.code == '0000'){
					$('#remainLuckyDrawTimes').val(result.remainLuckyDrawTimes);
					//rotateFunc(result.angle, result.drawName);
					luck.luckIndex = result.luckIndex;
					luck.luckDrawName = result.drawName;
					luck.speed=100;
					roll();
				}else{
					luck.isture = false; // 标志为 执行完毕
					showMsgInfo(0, result.message,0);
				}
			}
		}
	});
}