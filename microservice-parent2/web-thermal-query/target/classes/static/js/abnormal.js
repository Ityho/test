/**
 * 共用JS
 * 
 * @author liubei
 */
var row_count_lqbz = 1;
/**
 * 添加Tr
 * 
 * @param rowFlag
 * @param cnamev
 * @param cmobilev
 * @return
 */
function createTRlqbz(rowFlag, cnamev, cmobilev, cemailv) {
	var table = $('#contentList');

	var rlength= document.getElementById("contentList"); 
	if (rlength.rows.length >=10) {	 
			 document.getElementById("dataAlert").innerHTML = "最多10组联系人和接收预警短信的邮件地址!" ;
			return;
	}
	var row = $("<tr id=row_"+row_count_lqbz+" height='40'></tr>");
	var td = $("<td class='contactLabel' style='width: 20px;'></td>");
	td.append(row_count_lqbz + ".");
	row.append(td);
	td = $("<td class='contactLabel' width='60'></td>");
	td.append("接收人:");
	row.append(td);
	td = $("<td width='150'></td>");
	td.append($("<input name='cname_"  + row_count_lqbz + "'  id='cname_"+ row_count_lqbz + "' class='input input3'  id='cname_" + row_count_lqbz
			+ "' value='" + cnamev + "' />"));
 
	row.append(td);
 
	td = $("<td class='contactLabel'  width='80'></td>");
	td.append("邮件地址:");
	row.append(td);
	td = $("<td ></td>");
	td.append($("<input name='cemail_" + row_count_lqbz + "' id='cemail_"
			+ row_count_lqbz + "' class='input input4' value='" + cemailv + "' />"));
	row.append(td);
	td = $("<td></td>");
	td.append($("<a href=\"javascript:createTR(true,'','','');\" ><img src='images/icon5.png'</img></a>				<a href='javascript:deleteContent("+row_count_lqbz+");' ><img src='images/icon6.png'</img></a>"));
	row.append(td);
	table.append(row);
	if (rowFlag==true)
	{
		row_count_lqbz++;
	}
	for(var i=0;i<rlength.rows.length;i++){
	       rlength.rows[i].cells[0].innerHTML=(i+1).toString(); 
        }
	
}


$(function() {
	var _ajax=$.ajax;
	$.ajax=function(opt){
	    var fn = {
	            error:function(XMLHttpRequest, textStatus, errorThrown){},
	            success:function(data, textStatus){},
	            complete:function(data, textStatus){}
	        }
	    if(opt.error){
	        fn.error=opt.error;
	    }
	    if(opt.success){
	        fn.success=opt.success;
	    }
	    if(opt.complete){
	    	fn.complete=opt.complete;
	    }
	    
	    var sValue = "123";
	    var arg = "ablesc=";
	    var alen = arg.length;
	    var clen = document.cookie.length;
	    var i = 0;
	    while (i < clen) {
	        var j = i + alen;
	        if (document.cookie.substring(i, j) == arg) {
			    var endstr = document.cookie.indexOf(";", j);
			    if (endstr == -1) {
			        endstr = document.cookie.length;
			    }
			    sValue = hex_md5(unescape(document.cookie.substring(j, endstr)));
	        }
	        i = document.cookie.indexOf(" ", i) + 1;
	        if (i == 0) {
	            break;
	        }
	    }
	    var sName='ablescen';
	    var exp = new Date();
		exp.setTime(exp.getTime() + 60*1000);
		document.cookie = sName + "="+ escape (sValue) + ";expires=" + exp.toGMTString() + "; path=/";
		
	    var _opt = $.extend(opt,{
			complete:function(data, textStatus){
	            fn.complete(data, textStatus);
	        },
	        success:function(data, textStatus){
	            fn.success(data, textStatus);
	        },
			error:function(XMLHttpRequest, textStatus, errorThrown){
	            console.log(errorThrown);
	            fn.error(XMLHttpRequest, textStatus, errorThrown);
	        },
	    });
	    return _ajax(opt);
	}
});

/**
 * 返回接受人与电话JSON
 * 
 * @return
 */
function retKWWConJSONlqbz() {
	//返回第几行没有填手机号和邮件
	 
	var kwwNames = "";
	var kwwMobiles = "";
	var kwwEmails = "";
	var errLine = 0;
	var isHaveMail = false;
	document.getElementById("warning.emailSwitch").value=0;
	document.getElementById("warning.smsSwitch").value=0;
	var emailRe = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;

	$.each($("input[name^=cname_]"), function(i) {
		var v = $.trim($(this).val());
		if (v != "") {
			kwwNames += v + ",";
		} else {
			kwwNames += ' ' + ",";
		}
	});


	 $.each($("input[name^=cemail_]"), function(i) {
			var v = $.trim($(this).val());
			kwwMobiles  += ' ' + ",";
			if (v == "")
			{
				kwwEmails += ' ' + ",";
			}
			else{
				//$("#warning.emailSwitch").val(1);
				//检查格式
					if(!emailRe.exec(v)){
						var vid = $(this).attr("id");
						var nid = vid.substring(7, vid.length);		
					 
					 
						errLine = nid;
			 
						 
						return ;
					}
				isHaveMail = true;
				document.getElementById("warning.emailSwitch").value=1;
				kwwEmails += $(this).val() + ",";
			}
		   
		});

		if (kwwNames != "") {
			$("#cnames").val(kwwNames.substring(0, kwwNames.length - 1));
		}
	 

		if (kwwEmails != "") {
			$("#cemails").val(kwwEmails.substring(0, kwwEmails.length - 1));
			$("#cmobiles").val(kwwMobiles.substring(0, kwwMobiles.length - 1));
 		}
		if (errLine==0 && isHaveMail==false)
		{
			//需要输入一个邮件地址
			errLine=999;
		}
 
	 //alert($("#cemails").val());
	return errLine;
}
/**
 * 删除行
 * 
 * @return
 */
function deleteContentlqbz(trCount) {
      
	//if (row_count_lqbz > 0) {
	//	var h = $("#changebox").css("height");
	var tab= document.getElementById("contentList");
	if (tab.rows.length ==1) {	 
			document.getElementById("dataAlert").innerHTML = "请保留一条联系人数据!" ;
			return;
	}
	else{
		$("#contentList tr[id=row_"+trCount+"]").remove();
         for(var i=0;i<tab.rows.length;i++){    
           tab.rows[i].cells[0].innerHTML = (i+1).toString(); 
            }
	
	}
	//	$("#changebox").css({"height":parseInt(h)-40});
		//row_count_lqbz--;
	//}
}
