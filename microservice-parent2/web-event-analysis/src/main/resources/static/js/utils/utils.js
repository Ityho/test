// 创建并提交FORM
function createForm(url, type, target, params) {
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = type || 'POST';
    temp.target = target || '_self';
    if (params != null) {
        for(var x in params) {
            var opt = document.createElement('input');
            opt.type = 'hidden';
            opt.name = x;
            opt.value = params[x];
            temp.appendChild(opt);
        }
    }
    document.body.appendChild(temp);
    temp.submit();
}
function openNewPage(url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", "_blank");
    a.setAttribute("id", "openwin");
    document.body.appendChild(a);
    a.click();
}
function sendPost(urlPath,param,callBack) {
    $.ajax({
        url :  '${njxBasePath}'+urlPath,
        type : 'POST',
        data : param,
        success : function(result) {
        	if(!isEmpty(result)){
                callBack(result);
			}
        }
    });
}


Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
function setIframeHeight(id){
    try{
        var iframe = document.getElementById(id);
        if(iframe.attachEvent){
            iframe.attachEvent("onload", function(){
                iframe.height =  iframe.contentWindow.getBodyHeight();
            });
            return;
        }else{
            iframe.onload = function(){
                iframe.height = iframe.contentWindow.getBodyHeight();
            };
            return;
        }
    }catch(e){
        throw new Error('setIframeHeight Error');
    }
}
/**
 * 滑动验证码的POST请求
 * @param url
 * @param params
 * @param callback
 * @param demo
 */
function sendChartPost(url, params, callback, demo) {
    if(demo != null && demo.length > 3){
        $("#" + demo).removeAttr("_echarts_instance_");
    }
    $.ajax({
        url : url,
        data : params,
        async : true,
        type : "post",
        success : function(data) {
            if(data&& data.code == "8888" && geeTestFlag){
                geeTestFlag = false;
                isUnseal(data);
            }else{
                callback(data, demo);
            }
        }
    });
}
/**
 * 去掉所有的html标记
 */
function delHtmlTag(str){
    return str.replace(/<[^>]+>/g,"");
}
//判断字符是否为空的方法
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}
function getAppTotalNum(totalNum){

    return getWanInt2(totalNum);

    var totalNum=totalNum;
    if(totalNum>=100000000){
        var total=(totalNum/100000000).toFixed(2);
        var arr=total.toString().split(".")
        if(arr[1].substring(1,2)>4){
            return "近"+(totalNum/100000000).toFixed(1)+"亿";
        }else{
            return (totalNum/100000000).toFixed(1)+"亿+";
        }
    }else if(totalNum>=10000){
        var tot=(totalNum%getWanInt(totalNum));
        if(parseInt(tot.toString().substring(0,1))>4){
            if((totalNum/10000000).toFixed(0)==10){
                return "近1亿";
            }else{
                return "近"+getWanInt((totalNum/10000).toFixed(0))+"万";
            }
        }else{
            return getWanInt((totalNum/10000).toFixed(0))+"万+";
        }

//		var total=(totalNum/10000).toFixed(1);
//		var arr=total.toString().split(".");
//		if(parseInt(arr[1].substring(0,1))>4){
//			if((totalNum/10000000).toFixed(0)==10){
//				return "近1亿";
//			}else{
//				return "近"+getWanInt((totalNum/10000).toFixed(0))+"万";
//			}
//		}else{
//			return getWanInt((totalNum/10000).toFixed(0))+"万+";
//		}
    }
//	else if(totalNum>=1000){
//		var total=(totalNum/1000).toFixed(3);
//		var arr=total.toString().split(".")
//		if(parseInt(arr[1].substring(0,1))>4){
//			if((totalNum/1000).toFixed(0)==10){
//				return "近"+getWanInt((totalNum/10000).toFixed(0))+"万";
//			}else{
//				return "近"+getWanInt((totalNum/1000).toFixed(0))+"千";
//			}
//		}else{
//			return getWanInt((totalNum/1000).toFixed(0))+"千+";
//		}
//	}
    else{
        return totalNum;
    }
}
/**
 * 1000多变成1000
 */
function getWanInt(number){
    var numS=number.toString();
    var length=numS.length;
    var times=1;
    for(var i=0;i<(length-1);i++){
        times=times*10;
    }
    var first=numS.substring(0,1);
    return first*times;
}
/**
 * 几千多变成1000
 */
function getWanTwoInt(number){
    var numS=number.toString();
    var length=numS.length;
    var times=1;
    for(var i=0;i<(length-1);i++){
        times=times*10;
    }
    var first=1;
    return first*times;
}

function getWanInt3(number){

    var numS=number.toString();
    var length=numS.length;

    var x = 0;
    switch(length){
        case 1:
            x = 1;
            break;
        case 2:
            x = 10;
            break;
        case 3:
            x = 100;
            break;
        case 4:
            x = 1000;
            break;
        case 5:
            x = 10000;
            break;
        case 6:
            x = 100000;
            break;
        case 7:
            x = 1000000;
            break;
        case 8:
            x = 10000000;
            break;
        case 9:
            x = 100000000;
            break;
        default:
            break;
    }
    return x;
}


function getWanInt2(number){

    var str= "";
    if( number < 100000000){

        var x = getWanInt3(number);
        var xx = number/x;
        var y = Math.round(number/x);
        var number2 = y*x;
        var x2 =  getWanInt3(number2);

        if (x < 10000){
            if (x2 >= 10000){
                str = "近"+Math.round(number2/x2)*(x2/10000) +"万";
            }else{
                if(xx > y){
                    str = y*x+"+";
                }else{
                    str = "近"+y*x;
                }
            }
        }else{
            if (x2 >= 100000000){
                str = "近"+Math.round(number2/x2)*(x2/100000000) +"亿";
            }else{
                if(xx > y){
                    str = y*(x/10000) +"万+";
                }else{
                    str = "近"+y*(x/10000) +"万";
                }
            }
        }
    }else if (number >= 100000000){
        total = (number/100000000000).toFixed(2);
        var arrTot=total.toString().split(".");
        if(parseInt(arrTot[1].substring(1,2))>4){
            str = "近"+(number/100000000).toFixed(1)+"亿";
        } else {
            str = (number/100000000).toFixed(1)+"亿+";
        }
    }
    return str;
}
/**
 * m到n的随机数
 * @param m
 * @param n
 * @returns
 */
function sum (m,n){
    var num = Math.floor(Math.random()*(m - n) + n);
    return num;
}

