<script>
 function getOs()  
{ 
    var OsObject = ""; 
   if(navigator.userAgent.indexOf("MSIE")>0) { 
        return "MSIE"; 
   } 
   if(navigator.userAgent.indexOf("Firefox")>0){ 
        return "Firefox"; 
   } 
   if(navigator.userAgent.indexOf("Chrome")>0){ 
        return "Chrome"; 
   } 
   if(navigator.userAgent.indexOf("Safari")>0) { 
        return "Safari"; 
   }   
} 

function SetCwinHeight(){
	 
	var navType= getOs();
	var b_height=0;
	if (navType=="Chrome"||navType=="Safari"){
		b_height = document.documentElement.scrollHeight;
	} 
	else{
		b_height = document.body.scrollHeight;
	}
	b_height = b_height + 100;
	if (b_height < 900){
		b_height = 900;
	}
	var c_iframe = parent.document.getElementById('iframeContent');
	if (c_iframe){
		c_iframe.height = b_height;
	}
}


</script>