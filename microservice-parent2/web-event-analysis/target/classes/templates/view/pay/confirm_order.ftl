<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ include file="/top.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript"> 
//���Ӽ�ⷽ���ײ�����
function addKeyword(){
	$("#num").val(parseInt($("#numdiv").text())+1);
	$("#numdiv").text(parseInt($("#numdiv").text())+1)
	jisuanPrice();
}

// ���ټ�ⷽ���ײ�����
function delKeyword(){
	if(parseInt($("#numdiv").text())>1){
		$("#num").val(parseInt($("#numdiv").text())-1);
		$("#numdiv").text(parseInt($("#numdiv").text())-1)
		jisuanPrice();
	}
}
//����۸�
function jisuanPrice(){
	if ($("#num").val() == '' || $("#num").val() == 0)
		$("#num").val(1);
	var price = "${productPackage.packagePrice}";
	var totalPrice = parseInt($("#num").val()) * parseFloat(price);
	var youhuiPrice = 0;
	if (totalPrice >= 5000)
		youhuiPrice = totalPrice * 0.6;
	else if (totalPrice >= 4000)
		youhuiPrice = totalPrice - 1500;
	else if (totalPrice >= 3000)
		youhuiPrice = totalPrice - 1000;
	else if (totalPrice >= 2000)
		youhuiPrice = totalPrice - 500;
	else if (totalPrice >= 1000)
		youhuiPrice = totalPrice - 100;
	else
		youhuiPrice = totalPrice;
	$('#packagePrice').text(youhuiPrice.toFixed(2)+'Ԫ');
}
</script>
<script type="text/javascript">saveOperateLog('֧��ҳ','1011');</script> 
<body>
	<!-- 
	<section class="heard padding8 rel">
		<h1 class="">΢�ȵ�</h1>
	    <a href="javascript:;" onclick="javascript:history.back();" class="backlist abs"><i class="icon_back float_l"></i>����</a>
	</section>
	 -->
	<form action="<%=njxBasePath %>/pay/confirmOrder.action" method="post" id="confirmForm">
	 	<input type="hidden" value="${flag }" id="orderFlag" />
		<%-- <input type="hidden" value="${searchKeyword }" id="tempSearchKeyword" /> --%>
		<input type="hidden" value="${totalFee}" id="price" name="price" />
		<input type="hidden" value="${useCredit }" id="useCredit" name="useCredit" />
		<input type="hidden" value="${validateDays}" id="validateDays" name="validateDays" />
		<input type="hidden" value="${selectIds }" id="selectIds" name="selectIds" />
		<input type="hidden" value="${productPackageId}" id="productPackageId" name="productPackageId"/>
	<!--��Ҫ���� start-->
	<div id="container">
		<!--���� start-->
		<section>
			<section class="section warnSet">
				<ul class="warnSet1">
					<li>��������</li>
				</ul>
				<ul class="">
					<li class="f_c5">����</li>
					<li></li>
					<li class="align_r">
					<div class="setcount" style="margin-top: 5px;">
                       <a class="float_l" href="javascript:void(0);" onclick="delKeyword()">��</a>
                       <div id="numdiv" class="number" style="float:left">1</div>
                       <input value="1" type="hidden" class="input" name="packageCount" id="num">
                          <a class="float_l" href="javascript:void(0);" onclick="addKeyword()">+</a>    
                    </div>
					</li>
				</ul>
				<ul class="">
					<li class="f_c5">�ܼ�</li>
					<li></li>
					<li class="align_r"><font class="Price" id="packagePrice">${totalFee}Ԫ</font></li>
				</ul>
				<s:if test="#attr.productPackage.keywordServeDays!=0">
					<ul class="">
						<li class="f_c5">��Ч��</li>
						<li class="align_r" style="width: 66%;">${productPackage.keywordServeDays}��</li>
					</ul>
				</s:if>
				
				<ul class="warnSet1">
					<li>ѡ��֧����ʽ</li>
				</ul>
				<ul class="pay">
					<s:if test = "#attr.userPlatform == 2">
						<input type="hidden" value="03" id="payType" name="payType" />
						<li class="li_click"><i></i><em class="icon icon4"></em>΢��֧��</li>
					</s:if>
					<s:elseif test = "#attr.userPlatform == 3">
						<input type="hidden" value="02" id="payType" name="payType" />
						<li class="li_click"><i></i><em class="icon icon2"></em>΢��֧��</li>
					</s:elseif>
					<s:elseif test="#attr.userPlatform== 1">
						<input type="hidden" value="01" id="payType" name="payType" />
						<li class="li_click"><i></i><em class="icon icon1"></em>֧����֧��</li>
					</s:elseif>
					<!-- 
					<li style="border: none;"><i></i><em class="icon icon3"></em>���п�֧��</li>
					 -->
				</ul>
				<!-- 
				<ul class="more">
					<li style="width: 100%; text-align: center;"><a href="#">����֧����ʽ</a></li>
				</ul>
				 -->
			</section>

		</section>
		<section class="updateMto">
			<div class="senior">
				<button type="button" class="buttonAll"
					onclick="confirmOrder()">����֧��</button>
			</div>
		</section>
		<!--���� end-->

		<div class="mb50 clear"></div>
	</div>
	<!--��Ҫ���� end-->
	</form>
	<!--������ -->
	<div class="zhezhao"></div><!--��ɫ͸������-->

	<!--���ɹ������� -->
	<div id="subPOP" class="prompPOP" style="display: none;">
		<div class="prConBox">
		<div class="tit"><h1>�����ɹ�</h1><a href="javascript:void(0)" class="cancel">��</a></div>
		
		<div class="PromptCon">��л��������${searchKeyword }����һ���������ݣ����ǽ�ͨ��˽�Ÿ�֪����������鿴</div>
		<div class="bottom"><a onclick="javascript:location.href = '<%=njxBasePath %>/pOrder.action';" class="submitBtn ">��֪����</a></div>
		</div>
	</div>

	<script src="<%=staticResourcePathH5 %>/js/jquery-2.1.1.min.js?v=<%=SYSTEM_INIT_TIME %>" ></script>
	<script src="<%=staticResourcePathH5 %>/js/fs_forse.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<%@ include file="../../buttom.jsp" %>
</body>
</html>