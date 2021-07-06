//关闭活动说明，分享
function show(type){
	if(type=='sm'){
		$(".hdsmContent").css("display","block");
		$("#hdsmContent").css("display","block");
	}
	if(type=='fx'){
		$(".fx").css("display","block");
		$("#fx").css("display","block");
		$(".modal-backdrop").css("display","none");
	    $(".gift-dialog").css("display","none");
	    shakeEvent.start();
	}
}

//打开活动说明，分享
function hide(type){
	if(type=='sm'){
		$(".hdsmContent").css("display","none");
		$("#hdsmContent").css("display","none");
	}
	if(type=='fx'){
		$(".fx").css("display","none");
	    $("#fx").css("display","none");
	}
	if(type=='no_focus'){
		$(".bombScreen").css("display","none");
		shakeEvent.start();
	}
}

//活动已结束(可领奖)
$("#actMsg").click(function(){
	var type=$("#actStatus").val();
	if(type=='isEnd'){
    	$("#actMsg").css("display","none");
	}
})

//
//音乐
$(function() {
    function audioAutoPlay(id){
        var audio = document.getElementById(id);
        audio.play();
        document.addEventListener("WeixinJSBridgeReady", function () {  
        	audio.play();
        }, false);
    }
    audioAutoPlay('media');
});
function playvedio(id){
	var status=$("#audio_btn").attr("class");
	if(status=="off video_exist"){
		$("#audio_btn").addClass("rotate");
		var audio = document.getElementById(id);
		audio.muted = false;
	}else{
		$("#audio_btn").removeClass("rotate");
		var audio = document.getElementById(id);
		audio.muted = true;
	}
}
