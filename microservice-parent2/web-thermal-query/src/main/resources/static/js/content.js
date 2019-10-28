
function GetRandom(n){
	GetRandomn=Math.floor(Math.random()*n+1);
	return GetRandomn;
}


function MapChart(result){
	 require.config({
        paths: {
        	echarts: 'js/echarts'
        }
    });
	 var config = require(
           [  
            'echarts',
            'echarts/chart/map'
           ],
           function (ec) {
        	    echarts = ec;
				drawMapEChart("container",config, result);
           }
       );
}

