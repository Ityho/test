<meta charset="UTF-8">
<#assign staticResourcePath = request.contextPath />
<#assign njxBasePath = request.getContextPath() />
<#--String njxBasePath = request.getContextPath();-->
<#assign uri = request.requestUri />
<#--<#assign actionBase = systemConfig.getCdnResourcesPath() + "web" />-->
		<title>freemarker</title>
		<script type="text/javascript">
            var njxBasePath='${njxBasePath}';
            var staticResourcePath = '${staticResourcePath}';
            <#--var actionBase='${actionBase}';-->
            var actionBase='${staticResourcePath}';
            var qrCodeImg = '${qrCodeImg}';
        </script>

