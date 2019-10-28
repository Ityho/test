
<#include "../../top.ftl" >
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<meta name = "format-detection" content = "telephone=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/app/invitation/mindex.css" />
	<title></title>
</head>
<body>
<div class="mwrap">
	<section class="mhead">
		<div class="mhead-top">
			<div class="mpic">
				<img src="${admin.userHead }" alt="" />
			</div>
			<div class="msay">
				<#if admin.nickname != "">
					<p class="fz16">Hi，我是 ${admin.nickname }</p>
				<#else>
					<p class="fz16">Hi，我是 ${admin.username }</p>
				</#if>
				<p class="fz14">我一直在用微热点进行信息监测,感觉很不错，推荐给你试试啦！</p>
				<i class="micon-sanjiao"></i>
			</div>
		</div>
		<div class="mtryout">
			<form action="" method="post" name="frm">
				<input type="hidden" id="inviteUserId" value="${admin.userId}">
				<div class="label">
					<input class="inpt" type="text" name="" id="inUsername"  placeholder="请输入手机号" />
				</div>
				<div class="mcode">
					<input class="inpt" type="text" name="" id="authcode"  placeholder="请输入验证码" />
					<button id="btn"  type="button" class="getcode"  onclick="getMsmCoad()">获取验证码</button>
				</div>
				<div>
					<button class="btn-mtry" type="button" name="" id=""  onclick="doRegister()">免费试用</button>
				</div>

				<div class="mcheck"><input name="" type="checkbox" value="" checked="checked">阅读并同意《<a class="btn-xieyi" href="javascript:;">微热点用户协议</a>》</div>
			</form>
		</div>
	</section>
	<section class="mcon">
		<div class="mcon-wrap">
			<div class="mcon-title">
				新人福利
			</div>
			<div class="welfare">
				<div class="welf-lf">
					<img src="${staticResourcePathH5}/images/app/invitation/micon_1.png" />
					<p class="p1">监测方案免费用</p>
					<p class="p2">注册即享七天免费信息监测</p>
				</div>
				<div class="welf-rg">
					<img src="${staticResourcePathH5}/images/app/invitation/micon_2.png" />
					<p class="p1">首充送礼</p>
					<p class="p2">最高送30,000微积分</p>
					<p class="p3">微积分可以当钱花哦</p>

				</div>
			</div>
			<a class="btn-guize">查看详细规则&gt;&gt;</a>
		</div>

	</section>
	<section class="mcon-us">
		<div class="mcon-title">
			为什么选择我们
		</div>
		<div class="us-wrap">
			<ul>
				<li>
					<img src="${staticResourcePathH5}/images/app/invitation/micon_5.png" />
					<div class="us-right">
						<h3>新浪微博投资</h3>
						<p>微热点是新浪微博投资的社会化大数据应用平台,</p>
						<p>享有<span>新浪微博的全量数据</span>,亦可监测全网信息。</p>
					</div>
				</li>
				<li>
					<img src="${staticResourcePathH5}/images/app/invitation/micon_4.png" />
					<div class="us-right">
						<h3>团队专业</h3>
						<p>微热点<span>从2009年至今</span>一直专注于互联网信息、 </p>
						<p>商情监测及社会化大数据场景化应用。</p>
					</div>
				</li>
				<li>
					<img src="${staticResourcePathH5}/images/app/invitation/micon_3.png" />
					<div class="us-right">
						<h3>费用亲民</h3>
						<p>微热点免费注册，免费试用，收费亲民,</p>
						<p><span>每天只需0.8元</span>,就可以享受全网信息监测。</p>
					</div>
				</li>
			</ul>
		</div>
	</section>
</div>
<div class="popup success">
	<div class="popup-wrap">
		<img class="micon" src="${staticResourcePathH5}/images/app/invitation/micon_right.png" />
		<h3>领取成功</h3>
		<p>登录微热点App，马上开始信息监测之旅</p>
		<div class="btn-click">
			<a class="getuser" id="downloadApp">立即下载</a>
			<!-- <a href="">关注微信公众号</a> -->
		</div>

		<div class="delete">
			<img src="${staticResourcePathH5}/images/app/invitation/micon_delete.png" />
		</div>
	</div>

</div>
<!--弹框2-->
<div class="popup clause">
	<div class="popup-wrap">
		<div class="pop-title">
			<h3>《微热点网站服务条款》</h3>
		</div>
		<div class="clause-con">
			<p>本服务条款是微热点网站（http://wyq.sina.com，以下称为“本网站”）与用户（下称为“您”），共同缔结的对双方具有约束力的有效契约。</p>
			<p>微热点向用户提供本网站上所展示的产品与服务（下称“微热点”、“微热点服务”、“本服务”），并将不断更新服务内容，最新的微热点服务以本网站上的相关产品及服务介绍的页面展示以及向用户实际提供的为准。</p>
			<p>一、总则</p>

			<p>1.1 您确认：您在使用本服务之前，已经充分阅读、理解并接受本服务条款的全部内容（特别是以加粗及/或下划线标注的内容），一旦您选择“同意”并完成注册流程或使用本服务，即表示您同意遵循本服务条款之所有约定。</p>

			<p>1.2 您同意：微热点有权随时对本服务条款及相应的服务规则内容进行单方面的变更，并有权以消息推送、网页公告等方式予以公布，而无需另行单独通知您；若您在本服务条款内容公告变更后继续使用本服务的，表示您已充分阅读、理解并接受修改后的协议内容，也将遵循修改后的条款内容使用本服务；若您不同意修改后的服务条款，您应立即停止使用本服务。</p>
			<p>二、账户</p>
			<p>2.1 注册</p>
			<p>2.1.1 注册者资格</p>
			<p>2.1.1.1 您确认在您完成注册程序或以其他微热点允许的方式实际使用本服务时，您应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织（以下统称为“法律主体”）。</p>
			<p>2.1.1.2 若您是未成年人或限制民事行为能力人，则您不具备前述主体资格，您及您的监护人应承担因您的不当注册行为而导致的一切后果，且微热点有权注销（永久冻结）您的账户。</p>
			<p>2.1.2 注册、账户</p>
			<p>2.1.2.1 在您按照注册页面提示填写信息、阅读并同意本服务条款且完成全部注册程序后，或在您按照激活页面提示填写信息、阅读并同意本服务条款且完成全部激活程序后，或您以其他微热点允许的方式实际使用本网站服务时，您即受本服务条款约束。您可以使用您确认手机号码作为登录手段进入本网站。</p>
			<p>2.1.2.2 目前微热点允许一个法律主体拥有多个微热点账户，但一个微热点账户仅能对应唯一的法律主体。除非有法律规定或生效法律文书确定，或者符合微热点公布的条件，否则您不得以任何方式转让、赠与或让他人继承您的微热点账户。同时，在进行符合条件的微热点账户转让、赠与或继承时，微热点有权要求您、及/或受让受赠者、或您的继承人提供合格的文件材料并按照微热点要求的操作流程办理。</p>
			<p>2.1.2.3 通常情况下，您的微热点账户是您在本网站进行一切活动的唯一身份识别依据，每一个微热点账户都可以在本网站独立开展活动。但在下列情形下，微热点有权根据自己的判断，对同一及/或关联法律主体拥有的多个微热点账户进行统一处理，包括但不限于：</p>
			<p>2.1.2.3.1多个微热点账户之间存在一项或多项注册信息相同、代为付款、购买的产品或服务用于同一目的，或其他关联情形，并存在违反法律法规、本服务条款、微热点各产品条款或其他微热点规则的行为，且微热点通过结合其他相关证据足以判断上述微热点账户实际属于同一法律主体或同一团体的；</p>
			<p>2.1.2.3.2 其他微热点有充足理由需要对多个微热点账户进行统一处理的情形。</p>
			<p>2.1.3 信息</p>
			<p>2.1.3.1 在完成注册或激活流程时，您应当按照法律法规要求，按相应页面的提示准确提供并及时更新您的资料，以使之真实、及时，完整和准确。</p>
			<p>2.1.3.2 您应当准确填写并及时更新您提供的联系电话、联系地址、邮政编码等联系方式，以便微热点与您进行有效联系，因通过这些联系方式无法与您取得联系，导致您在使用微热点服务过程中产生任何损失或增加费用的，应由您完全独自承担。您了解并同意，您有义务保持您提供的联系方式的有效性，如有变更需要更新的，您应按微热点的要求进行操作。</p>
			<p>2.2 账户安全</p>
			<p>2.2.1 您须自行负责对您的微热点账户和密码保密，且须对您在该登录名和密码下发生的所有活动（包括但不限于获取信息、转发信息、撰写报告或提交各类规则协议、网上续签协议或购买服务等）承担责任。您同意：(a)如发现任何人未经授权使用您的微热点账户和密码，或发生违反保密规定的任何其他情况，您会立即通知微热点；及(b)确保您在每个上网时段结束时，以正确步骤离开网站。微热点不能也不会对因您未能遵守本款规定而发生的任何损失或损毁负责。您理解微热点对您的请求采取行动需要合理时间，微热点对在采取行动前已经产生的后果（包括但不限于您的任何损失）不承担任何责任。</p>
			<p>2.2.2 除非有法律规定或司法裁定，且征得微热点的同意，否则，您的登录和密码不得以任何方式转让、赠与或继承（与账户相关的财产权益除外）。</p>
			<p>2.2.3您理解并同意，微热点有权按照国家司法、行政、军事、安全等机关（包括但不限于公安机关、检察机关、法院、海关、税务机关、安全部门等）的要求向上述机关提交您的个人注册信息和使用记录。</p>
			<p>2.3 账户注销</p>
			<p>2.3.1 微热点保留在您违反国家、地方法律法规规定或违反本服务条款的情况下，中止或终止为您提供部分或全部服务、直至注销微热点账户的权利。</p>
			<p>2.3.2 微热点登录名的注销</p>
			<p>2.3.2.1 违反微热点公布的任何服务协议/条款、管理规范等规范内容；</p>
			<p>2.3.2.2 破坏或试图破坏微热点系统正常的使用秩序；</p>
			<p>2.3.2.3 任何使用含有微热点名称、品牌且对他人有误导嫌疑或任何使用某种中英文(全称或简称)、数字、域名等意图表示或映射与微热点具有某种关系的；</p>
			<p>2.3.2.4微热点根据自行合理判断，认为可能是与如上行为性质相同或产生如上类似风险的其他情况。</p>
			<p>三、网站服务使用守则</p>
			<p>为有效保障您使用本服务的合法权益，您理解并同意接受以下规则：</p>
			<p>3.1 您通过包括但不限于以下方式向微热点发出的指令，均视为您本人的指令，不可撤回或撤销，您应自行对微热点执行前述指令所产生的任何结果承担责任。</p>
			<p>3.1.1 通过您的微热点账户和密码进行的所有操作；</p>
			<p>3.1.2 通过与您的账号绑定的手机号码向微热点发送的全部信息；</p>
			<p>3.1.3 通过与您的账号绑定的其他硬件、终端、软件、代号、编码、代码、其他账户名等有形体或无形体向微热点发送的信息；</p>
			<p>3.1.4 其他微热点与您约定或微热点认可的其他方式。</p>
			<p>3.2 您在使用本服务过程中，微热点网站上出现的关于网站操作的提示或微热点发送到您手机的信息（短信或推送等）内容是您使用本服务的相关规则，您使用本服务即表示您同意接受本服务的相关规则。您了解并同意微热点有权单方修改服务的相关规则，而无须征得您的同意，服务规则应以您使用服务时的页面提示（或发送到该手机的短信或电话等）为准，您同意并遵照服务规则是您使用本服务的前提。</p>
			<p>3.3 微热点可能会以电子邮件（或发送到您手机的短信或电话等）方式通知您服务进展情况以及提示您进行下一步的操作，但微热点不保证您能够收到或者及时收到该邮件（或发送到该手机的短信或电话等），且不对此承担任何后果。因此，在服务过程中您应当及时登录到本网站查看和进行操作。因您没有及时查看和对服务状态进行修改或确认或未能提交相关申请而导致的任何纠纷或损失，微热点不负任何责任。</p>
			<p>3.4 您授权微热点可以通过向第三方审核您的身份和资格，取得您使用本服务的相关资料。</p>
			<p>3.5 在您开始使用微热点的某一产品或服务前，可能需要和微热点就这一产品或服务签订单独的服务协议。您只有在接受该服务协议的全部内容后方可使用该产品或服务；如您不同意该服务协议的部分或者全部的，请您不要进行后续操作。</p>
			<p>3.6 在您使用微热点服务时，微热点有权依照相应的产品/及或服务收费介绍、订单及/或相关协议向您收取服务费用。微热点拥有制订及调整服务费之权利，具体服务费用以您使用本服务时页面上所列之收费方式公告或您与微热点达成的其他书面协议为准。</p>
			<p>3.7 特别提示</p>
			<p>3.7.1 微热点服务开通后，即使您未新增服务项目或资源，亦未进行新的操作，但因占用资源的收费依旧会发生。</p>
			<p>3.7.2 微积分作为微热点的预支付站内货币，享受特别折扣优惠，一旦购买，不支持退换，请您酌情考虑后购买。</p>
			<p>3.8 申诉及处理</p>
			<p>3.8.1 在您使用微热点服务的过程中，有可能因为存在本服务条款第5.3条所列情形之一，而被微热点采取了包括但不限于停止全部或部分服务、限制服务的功能措施，微热点将通过邮件、站内信、短信或电话等方式通知您按照相应的程序进行申诉。</p>
			<p>3.8.2 您通过申诉程序，向微热点申请解除上述限制或冻结或恢复服务的，应按照微热点的要求，如实提供身份证明及相关资料，以及微热点要求的其他信息或文件，以便微热点进行核实。您应充分理解您的申诉并不必然被允许，微热点有权依照自行判断来决定是否同意您的申诉请求。</p>
			<p>3.8.3 您理解并同意，如果您拒绝如实提供身份证明及相关资料的，或未能通过微热点审核的，微热点有权长期冻结该等账户且长期限制该等产品或者服务的部分或全部功能。</p>
			<p>3.9 关于第三方</p>
			<p>3.9.1 如果您通过使用本服务，将获取使用来自第三方的任何产品或服务，您还可能受限于该等第三方的相关条款和条件，微热点对此不予过问亦不承担任何责任，本服务条款不影响您与该第三方的法律关系。</p>
			<p>3.9.2 您确认并同意，微热点的各关联公司均为本服务条款的第三方受益人，且微热点关联公司有权直接强制执行并依赖本服务条款中授予其利益的任何规定。除此之外，任何第三方均不得作为本服务条款的第三方受益人。</p>
			<p>四、 您的权利和义务</p>
			<p>4.1 您有权利享受微热点提供的互联网技术和信息服务，并有权利在接受微热点提供的服务时获得微热点的技术支持、咨询等服务，服务内容详见本网站相关产品介绍。</p>
			<p>4.2 您保证不会利用技术或其他手段破坏或扰乱本网站及微热点其他服务。</p>
			<p>4.3 您应尊重微热点及其他第三方的知识产权和其他合法权利，并保证在发生侵犯上述权益的违法事件时尽力保护微热点及其股东、雇员、合作伙伴等免于因该等事件受到影响或损失；微热点保留您侵犯微热点合法权益时终止向您提供服务并不退还任何款项的权利。</p>
			<p>4.4 对由于您向微热点提供的联络方式有误以及您用于接受微热点邮件的电子邮箱安全性、稳定性不佳而导致的一切后果，您应自行承担责任，包括但不限于因您未能及时收到微热点的相关通知而导致的后果和损失。</p>
			<p>4.5您保证：</p>
			<p>4.5.1.您使用微热点产品或服务时将遵从国家、地方法律法规、行业惯例和社会公共道德，不会利用微热点提供的服务进行存储、发布、传播如下信息和内容：违反国家法律法规政策的任何内容（信息）；违反国家规定的政治宣传和/或新闻信息；涉及国家秘密和/或安全的信息；封建迷信和/或淫秽、色情、下流的信息或教唆犯罪的信息；博彩有奖、赌博游戏；违反国家民族和宗教政策的信息；妨碍互联网运行安全的信息；侵害他人合法权益的信息和/或其他有损于社会秩序、社会治安、公共道德的信息或内容;您同时承诺不得为他人发布上述不符合国家规定和/或本服务条款约定的信息内容提供任何便利;</p>
			<p>4.5.2. 使用微热点产品或服务时，应遵守您与微热点签订的服务条款和您确认同意的订购页面的内容，包括但不限于：</p>
			<p>4.5.2.1. 您应按时付款；</p>
			<p>4.5.2.2. 不应出现任何破坏或试图破坏网络安全的行为等；</p>
			<p>如您违反上述保证，微热点除有权根据相关服务条款采取删除信息、中止服务、终止服务的措施，并有权限制您账户如新购产品或服务、续费等部分或全部功能，如因您上述行为给微热点造成损失的，您应予赔偿。
			</p>
			<p>4.6 若您使用的某项服务中包含可下载的微热点软件，则微热点仅授予您非独占性的、不可转让的、非商业运营目的的个人使用许可。除非微热点另有明示或与您另有约定外，您不得复制、修改、发布、出售或出租服务或所含软件的任何部分，也不得进行反向工程或试图提取该软件的源代码。</p>
			<p>五、 微热点的权利和义务</p>
			<p>5.1 微热点应根据您选择的服务以及交纳款项的情况向您提供合格的网络技术和信息服务。</p>
			<p>5.2微热点承诺对您资料采取对外保密措施，不向第三方披露您资料，不授权第三方使用您资料，除非：</p>
			<p>5.2.1 依据本服务条款或者您与微热点之间其他服务协议、合同、在线条款等规定可以提供；</p>
			<p>5.2.2 依据法律法规的规定应当提供；</p>
			<p>5.2.3 行政、司法等职权部门要求微热点提供；</p>
			<p>5.2.4 您同意微热点向第三方提供；</p>
			<p>5.2.5 微热点解决举报事件、提起诉讼而提交的；</p>
			<p>5.2.6 微热点为防止严重违法行为或涉嫌犯罪行为发生而采取必要合理行动所必须提交的；</p>
			<p>5.2.7 微热点为向您提供产品、服务、信息而向第三方提供的，包括微热点通过第三方的技术及服务向您提供产品、服务、信息的情况。</p>
			<p>六、 隐私及其他个人信息的保护</p>
			<p>一旦您同意本服务条款或使用本服务，您即同意微热点按照以下条款来使用和披露您的个人信息。</p>
			<p>6.1 登录名和密码</p>
			<p>在您注册帐户时，微热点会要求您设置微热点账户登录名和密码来识别您的身份，您的帐号是您的手机号码，也是唯一你通过验证码找回您的密码的方式。您仅可通过您设置的密码来使用该账户，如果您泄漏了密码，您可能会丢失您的个人识别信息。该账户和密码因任何原因受到潜在或现实危险时，您应该立即和微热点取得联系，在微热点采取行动前，微热点对此不负任何责任。</p>
			<p>6.2 用户信息</p>
			<p>您完成账户注册时，可以向微热点提供您的昵称和电子邮件地址，您还可以选择来填写相关附加信息（包括但不限于您公司所在的省份和城市、时区和邮政编码、姓名）。为有针对性地向您提供新的服务和机会，您了解并同意微热点及其关联公司将通过您的电子邮件地址或该手机通知您这些信息。</p>
			<p>6.3 登录记录</p>
			<p>为了保障您使用本服务的安全以及不断改进服务质量，微热点将记录并保存您登录和使用本服务的相关信息，但微热点承诺不将此类信息提供给任何第三方（除双方另有约定或法律法规另有规定及微热点关联公司外）。</p>
			<p>6.4外部链接</p>
			<p>本网站可能含有到其他网站的链接，但微热点对其他网站的隐私保护措施不负任何责任。微热点可能在任何需要的时候增加商业伙伴或共用品牌的网站。</p>
			<p>6.5安全</p>
			<p>微热点仅按现有技术提供相应的安全措施来使微热点掌握的信息不丢失，不被滥用和变造。这些安全措施包括向其他服务器备份数据和对用户密码加密。尽管有这些安全措施，但微热点不保证这些信息的绝对安全。</p>
			<p>七、 系统中断或故障</p>
			<p>系统可能因下列状况无法正常运作，使您无法使用各项互联网服务时，微热点不承担损害赔偿责任，该状况包括但不限于：</p>
			<p>7.1 微热点在系统停机维护期间。</p>
			<p>7.2 电信设备出现故障不能进行数据传输的。</p>
			<p>7.3 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成微热点系统障碍不能执行业务的。</p>
			<p>7.4 由于黑客攻击、电信部门技术调整或故障、网站升级、银行方面的问题等原因而造成的服务中断或者延迟。</p>

			<p>八、 责任范围及责任限制</p>
			<p>8.1 微热点仅对本服务条款中列明的责任承担范围负责。</p>
			<p>8.2 本服务之合作单位，所提供之服务品质及内容由该合作单位自行负责。</p>
			<p>8.3 您了解并同意，因您使用本服务、违反本服务条款或在您的账户下采取的任何行动，而导致的任何第三方索赔，应且仅应由您本人承担。如果由此引起微热点及其关联公司、员工、客户和合作伙伴被第三方索赔的，您应负责处理，并承担由此造成的全部责任。</p>
			<p>8.4 因互联网条件和服务能力的限制，微热点不保证信息服务的内容是绝对完整和准确的，微热点提供的内容仅供用户参考。微热点的所有内容来源于网络，微热点仅对互联网内容提供抓取、搜索和归纳汇总服务，对于因互联网源内容不准确、源内容侵权、源内容抓取不完整、抓取到的内容分析不准确等原因引起的任何直接的、间接的、惩罚性的、特殊的、派生的损失（包括业务损失、收益损失、利润损失、使用数据、商誉或其他经济利益的损失），不论是如何产生的，也不论是由对本服务条款的违约（包括违反保证）还是由侵权造成的，均不负有任何责任，即使事先已被告知此等损失的可能性。另外即使本服务条款规定的排他性救济没有达到其基本目的，也应排除微热点对上述损失的责任。</p>
			<p>8.5 除本服务条款另有规定或微热点与您就某一具体产品及/或服务另有约定外，在任何情况下，您同意微热点对本服务条款所承担的赔偿责任总额不超过向您收取的当月服务费用总额。</p>

			<p>九、 完整协议</p>
			<p>9.1 本服务条款由本服务条款与本网站公示的各项规则组成，相关名词可互相引用参照，如有不同理解，以本服务条款为准。</p>
			<p>9.2 本服务条款的章节标题仅为行文方便而设，不具有法律或合同效力。</p>
			<p>9.3 您对本服务条款理解和认同，您即对本服务条款所有组成部分的内容理解并认同，一旦您使用本服务，您和微热点即受本服务条款所有组成部分的约束。</p>
			<p>9.4 本服务条款部分内容被有管辖权的法院认定为违法的，不因此影响其他内容的效力。</p>

			<p>十、商标、知识产权的保护</p>
			<p>10.1 除第三方产品或服务外，本网站上的架构、页面设计，均由微热点或微热点关联企业依法拥有其知识产权，包括但不限于商标权、专利权、著作权、商业秘密等。</p>
			<p>10、2微热点抓取的信息内容，都来源于互联网，微热点仅为用户提供个性化信息搜索服务。</p>
			<p>10.2 非经微热点或微热点关联企业书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、散布、发行或公开发表本网站上程序或内容。</p>
			<p>10.3 尊重知识产权是您应尽的义务，如有违反，您应承担损害赔偿责任。</p>

			<p>十一、通知送达</p>
			<p>11.1 您理解并同意，微热点可依据自行判断，通过网页公告、电子邮件、手机短信或常规的信件传送等方式向您发出通知，且微热点可以信赖您所提供的联系信息是完整、准确且当前有效的；上述通知于发送之日视为已送达收件人。</p>
			<p>11.2 除非本服务条款另有约定或微热点与您另行签订的协议明确规定了通知方式，您发送给微热点的通知，应当通过微热点对外正式公布的通信地址、传真号码、电子邮件地址等联系信息进行送达。</p>

			<p>十二、 法律适用与管辖</p>
			<p>本服务条款之效力、解释、变更、执行与争议解决均适用中华人民共和国法律。因本服务条款产生之争议，均应依照中华人民共和国法律予以处理，并提交上海市浦东新区人民法院审判。</p>


		</div>
		<a onclick="del()">已阅读并知悉</a>
		<div class="delete">
			<img src="${staticResourcePathH5}/images/app/invitation/micon_delete.png" />
		</div>
	</div>

</div>
<!--弹框3-->
<div class="popup check-guize">
	<div class="popup-wrap">
		<div class="pop-title">
			<h3>活动规则</h3>
		</div>
		<div class="check-guize-con">
			<p>1.本活动仅限新用户参与，新用户指从未注册过微热点产品的用户，同一账号，手机号，设备号视为同一用户，仅可以参加一次活动。</p>
			<p>2.奖励将会直接发送到您已验证的手机号码的账户上，您可以通过手机号码验证登录即可查收，免费监测有效期为七天，请及时使用哦~</p>
			<p>3.通过非法手段获得的奖励，我司有权收回并追究其法律责任</p>
			<p>4.本活动最终解释权归上海蜜度信息技术有限公司所有</p>





		</div>
		<a onclick="del()">已阅读并知悉</a>
		<div class="delete">
			<img src="${staticResourcePathH5}/images/app/invitation/micon_delete.png" />
		</div>
	</div>

</div>

<div class="popup-mask"></div>
<script src="${staticResourcePathH5}/js/app/invite/wyrem.js"></script>
<script src="${staticResourcePathH5}/js/app/invite/jquery.min.js"></script>
<script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
<script src='${staticResourcePathH5}/js/weixinNewConfig.js'></script>
<script>
	$(function(){
		weixinLinkShare({wxAuthUrl:njxBasePath+'/report/getWeixinConfig.action',
			title:"HI，微热点邀请您免费使用信息监测啦~",desc:"新用户可享30,000微积分好礼，社会化大数据搜索，网络信息尽在掌握~",imgUrl:"http://h5.51wyq.cn/images/wxActivity/share.jpg",
			link:location.href.split('#')[0]});
	})
</script>
<script type="text/javascript">
	$('.delete').on('click', function() {
		del();
	});

	$('.popup-mask').on('click', function() {
		$(this).hide();
		$('.popup').hide();
		$('body').removeClass('isSoscll');
		$('html').removeClass('isSoscll');
	})

	function del() {
		$('.popup-mask').css('display', 'none');
		$('.popup').hide();
		$('body').removeClass('isSoscll');
		$('html').removeClass('isSoscll');
	}
	/*阅读*/

	$('.btn-xieyi').on('click', function() {
		$('.clause').show();
		$('.popup-mask').css('display', 'block');
		$('body').addClass('isSoscll');
		$('html').addClass('isSoscll');
	});
	$('.btn-guize').on('click', function() {
		$('.check-guize').show();
		$('.popup-mask').css('display', 'block');
		$('body').addClass('isSoscll');
		$('html').addClass('isSoscll');
	});

	//手机号
	function checkPhone(){
		var phone = document.getElementById('phone').value;
		if(!(/^1[34578]\d{9}$/.test(phone))){
			alert("手机号码有误，请重填");
			return false;
		}
	}

</script>
<script type="text/javascript">
	var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
	function getMsmCoad(){
		authcode = $.trim($("#authcode").val());
		if($("#inUsername").val() == '') {
			alert("请输入手机号码！");
			return false;
		}
		if(!mobile_filter.test($("#inUsername").val())) {
			alert("手机号码不正确！");
			return false;
		}
		//验证码倒计时
		var countdown = 60;

		var obj = $("#btn");
		settime(obj);
		obj.css("background",'rgb(196, 196, 196)')

		function settime(obj) { //发送验证码倒计时
			if(countdown == 0) {
				obj.attr('disabled', false);
				obj.html("获取验证码");
				countdown = 60;
				return;
			} else {
				obj.attr('disabled', true);
				obj.html("重新发送(" + countdown + ")");
				countdown--;
			}
			setTimeout(function() {
				settime(obj)
			}, 1000)
		}
		$.ajax({
			type:"post",
			url:njxBasePath + "/user/checkLoginMobile",
			data:{
				'user.mobile':$("#inUsername").val()
			},
			cache:false,
			success: function(data){
				if(data==1) {
					alert("该号码已被使用！");
					return false;
				} else {
					$.ajax({
						type:"post",
						url:njxBasePath+"/user/sendRegVcode",
						data:{
							'mobile':$("#inUsername").val()
						},
						cache:false,
						success: function(data, textStatus){
							if(data && data.code == "0000") {
								alert("短信验证码已成功发送到您的手机，请尽快输入！");
							}else if(data && data.code != "0000"){
								alert(data.message);
							}else{
								alert("获取验证码异常！");
							}
						},
						error:function(data){}
					});
				}
			}
		});
	}
</script>
<script type="text/javascript">
	var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
	function doRegister(){
		if($("#inUsername").val() == '') {
			alert("请输入手机号码！");
			return;
		}
		if(!mobile_filter.test($("#inUsername").val())) {
			alert("手机号码不正确!");
			return;
		}

		if($("#authcode").val() == '') {
			alert("请输入验证码!");
			return;
		}
		$.ajax({
			type:"post",
			url:njxBasePath + "/user/checkLoginMobile",
			data:{
				'user.mobile':$("#inUsername").val()
			},
			cache:false,
			success: function(data){
				if(data==1) {
					alert("该号码已被使用！");
					return false;
				} else {
					$.ajax({
						type:"post",
						url:njxBasePath+"/user/sendRegVcode?mobile="+$("#inUsername").val(),
						data:{authcode:$("#authcode").val()},
						cache:false,
						success: function(data){
							if(data && data.code == "0000" && data.succ){
								$.ajax({
									type:"post",
									url:njxBasePath+"/user/doRegisterByInvite",
									data:{
										'mobile':$("#inUsername").val(),
										'inviteUserId':$("#inviteUserId").val()
									},
									cache:false,
									success: function(data){
										if(data.status == 1) {
											$('.success').show();
											$('.popup-mask').css('display', 'block');
											$('body').addClass('isSoscll');
											$('html').addClass('isSoscll');
											return;
										}else if(data.status == 2){
											alert(data.obj);
											return;
										}else{
											alert("领取失败~");
											return;
										}
									},
									error:function(data){}
								});
							}else if(data && data.code == "0000" && !data.succ){
								alert("验证码错误，请重新输入！");
								return;
							}else if(data && data.code != "0000"){
								alert(data.message);
								return;
							}else{
								alert("验证异常!");
								return;
							}
						}
					});
				}
			}
		});
	}
</script>
<script type="text/javascript">
	$(function() {// 初始化内容
		var ua = navigator.userAgent.toLowerCase();
		if (/iphone|ipad|ipod/.test(ua)) {
			$("#downloadApp").attr("href","https://apps.apple.com/cn/app/wei-yu-qing/id953382632");
		} else if (/android/.test(ua)) {
			$("#downloadApp").attr("href","http://app.51wyq.cn/android/wyq.apk");
		}
	});
</script>
<script type="text/javascript">
	document.title='HI，微热点邀请您免费使用信息监测啦~';
</script>
<#include "../../buttom.ftl" >
</body>

</html>