/**
 * 共用JS
 * 
 * @author liubei
 */
var row_count = 1;
/**
 * 添加Tr
 * 
 * @param rowFlag
 * @param cnamev
 * @param cmobilev
 * @return
 */
function createTR(rowFlag, cnamev, cmobilev, cemailv) {
	var table = $('#contentList');

	var rlength= document.getElementById("contentList"); 
	if (rlength.rows.length >=10) {	 
			 document.getElementById("dataAlert").innerHTML = "最多10组联系人和接收预警短信的邮件地址!" ;
			return;
	}
	var row = $("<tr id=row_"+row_count+" height='40'></tr>");
	var td = $("<td class='contactLabel' style='width: 20px;'></td>");
	td.append(row_count + ".");
	row.append(td);
	td = $("<td class='contactLabel' width='60'></td>");
	td.append("接收人:");
	row.append(td);
	td = $("<td width='150'></td>");
	td.append($("<input name='cname_"  + row_count + "'  id='cname_"+ row_count + "' class='input input3'  id='cname_" + row_count
			+ "' value='" + cnamev + "' />"));
 
	row.append(td);
 
	td = $("<td class='contactLabel'  width='80'></td>");
	td.append("邮件地址:");
	row.append(td);
	td = $("<td ></td>");
	td.append($("<input name='cemail_" + row_count + "' id='cemail_"
			+ row_count + "' class='input input4' value='" + cemailv + "' />"));
	row.append(td);
	td = $("<td></td>");
	td.append($("<a href=\"javascript:createTR(true,'','','');\" ><img src='images/icon5.png'</img></a>				<a href='javascript:deleteContent("+row_count+");' ><img src='images/icon6.png'</img></a>"));
	row.append(td);
	table.append(row);
	if (rowFlag==true)
	{
		row_count++;
	}
	for(var i=0;i<rlength.rows.length;i++){
	       rlength.rows[i].cells[0].innerHTML=(i+1).toString(); 
        }
	
}

/**
 * 返回接受人与电话JSON
 * 
 * @return
 */
function retKWWConJSON() {
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
function deleteContent(trCount) {
      
	//if (row_count > 0) {
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
		//row_count--;
	//}
}

$(function() {
	$(
			"#commonBack,.commonBack,#commonSubmit,#commonAdd,.commonAdd,#commonAddNews,#commonDelete")
			.mouseover(function() {
				$(this).css( {
					"color" : "#ffffff",
					"backgroundColor" : "#aaaaaa"
				});
			});

	$(
			"#commonBack,.commonBack,#commonSubmit,#commonAdd,.commonAdd,#commonAddNews,#commonDelete")
			.mouseout(function() {
				$(this).css( {
					"color" : "#000000",
					"backgroundColor" : "#f5f5f5"
				});
			});

	// 如果有写接受人与电话则自动生成表格
	//if ($.trim($("#cnames").val()) != '') {
		var cnames = $("#cnames").val().split(",");
		var cmobiles = $("#cmobiles").val().split(",");
		var cemails = $("#cemails").val().split(",");
		var table = $('#contentList');
		
		$("#cname_1").val(cnames[0]);
	 
	//	$("#cmobile_1").val(cmobiles[0]);
		$("#cemail_1").val(cemails[0]);
		 
		row_count = 2;
		for ( var i = 2; i <= cnames.length; i++) {
			row_count = i;
			var cnameV = cnames[i - 1];
			var cmobileV = cmobiles[i - 1];
			var cemailV = cemails[i - 1];
			createTR(false, cnameV, cmobileV,cemailV);
			row_count = row_count +1;
		}
		
	//}

	//has not used 添加一组联系人
	$("#addContentList").click(function() {
		var changeHeight = $.trim($(this).attr("changeDivHeight"));
		 alert(changeHeight);
		if (changeHeight) {
			// 如果是单个关键添加修改的位置,需要改变高度
			var h = $("#changebox").css("height");
			$("#changebox").css( {
				"height" : parseInt(h) + 40
			});
		}
		if (row_count >= 10) {
			alert("最多10组联系人和接收预警短信的邮件地址!");
			return false;
		}
		createTR(true, "", "","");
	});

});