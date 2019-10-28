//增加极验验证码
function doGeeTest(options){
	if(options){
		var default_options = {
			product:"float",
			captcha:"#float-captcha",
			wait:"#wait"
		}
		$.extend(default_options, options);
		$.ajax({
			url :actionBase+"/startCaptchaServlet?t=" + (new Date()).getTime(), // 加随机数防止缓存
			type : "get",
			dataType : "json",
			success : function(data) {
				// 调用 initGeetest 初始化参数
				// 参数1：配置参数
				// 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
				initGeetest({
					gt : data.gt,
					challenge : data.challenge,
					new_captcha : data.new_captcha, // 用于宕机时表示是新验证码的宕机
					offline : !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
					product : options.product, // 产品形式，包括：float，popup
					width: "100%"
				// 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
				}, handler);
			}
		});
		var handler = function(captchaObj) {
			// 将验证码加到id为captcha的元素里
			if(options.product != "bind"){
				captchaObj.appendTo(options.captcha);
			}
			captchaObj.onReady(function() {
				$(options.wait).hide();
				if(options.product == "bind" && !options.selector){
					captchaObj.verify(); //显示验证码
				}
			}).onSuccess(function(data) {
				var validate = captchaObj.getValidate();
				if (!validate) {
					return;
				}
				$.ajax({
					url : actionBase+"/verifyLoginServlet?t=" + (new Date()).getTime(), // 进行二次验证
					type : "post",
					dataType : "json",
					data : {
						// 二次验证所需的三个值
						geetest_challenge : validate.geetest_challenge,
						geetest_validate : validate.geetest_validate,
						geetest_seccode : validate.geetest_seccode,
						type : options.type
					},
					success : function(data) {
						if(options.callback){
							options.callback(data)
						}
					}
				});
			}).onError(function(){
				//your code
			});
			
			if(options.product == "bind" && options.selector){
				$(options.selector).on("click",function(){
					// some code
					// 检测验证码是否ready, 验证码的onReady是否执行
					captchaObj.verify(); //显示验证码
					// some code
				});
			}
		};
	};
};
var geeTestFlag = true;
function isUnseal(data){
	//极验搜索校验
	var geeTestData;
	doGeeTest({type:4,
		product:"bind",
		//selector:"#advSearchSubmit",
		callback:function(result){
			geeTestData = result;
			if(geeTestData && geeTestData.status == 'success'){
				doUnseal();
			}
		}
	});
	
}
function doUnseal(){
 	$.get("doUnseal",function(data) {
		if(data&& data.obj.code == "0000"){
			geeTestFlag = true;
//			goSearch(2);
			searchReload();
		}
	});
 	
}