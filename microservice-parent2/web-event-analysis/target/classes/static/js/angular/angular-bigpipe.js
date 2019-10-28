//var Bigpipe=function(){
//	this.dataAry=[];
//}
//
//Bigpipe.prototype.ready=function(key,callback){
//	if(this.dataAry[key]){
//		callback(this.dataAry[key]);
//	}
//}
//
//Bigpipe.prototype.set=function(key,data){
//	this.dataAry[key] = data;
////	for(var i=0;i<callbacks.length;i++){
////		callbacks[i].call(this,data);
////	}
//}
//先初始化需要的bigpipe模块
//var bigpipe=new Bigpipe();


var Bigpipe=function(){
	this.callbacks={};
}

Bigpipe.prototype.ready=function(key,callback){
	if(!this.callbacks[key]){
		this.callbacks[key]=[];
	}
	this.callbacks[key].push(callback);
}

Bigpipe.prototype.set=function(key,data){
	var callbacks=this.callbacks[key]||[];
	for(var i=0;i<callbacks.length;i++){
		callbacks[i].call(this,data);
	}
}
//先初始化需要的bigpipe模块
var bigpipe=new Bigpipe();