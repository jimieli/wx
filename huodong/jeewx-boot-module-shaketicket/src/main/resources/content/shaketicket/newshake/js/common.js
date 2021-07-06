$(function(){
	shakeEvent = new Shake({ threshold:10, timeout:1000 });
	shakeEvent.start();
	window.addEventListener('shake', gift_shake, false);
	
	var touchstart_is = 0;
	$('body').on('touchstart', function(){
		if (touchstart_is == 1) return;
		touchstart_is = 1;
		$('#audio_shake')[0].play();
		$('#audio_shake')[0].pause();
	});
});

var sbox;
var gift_shake = function(){
	shakeEvent.stop();
	$('#audio_shake')[0].play();
	var actStatus=$("#actStatus").val();
	if(actStatus=='isEnd'){
		var dlg = box_dialog({ title:'友情提示~', content:'活动已结束！' });
		dlg.find('.foot .-gifts').text('关闭').click(function(){
			dlg.remove();
			shakeEvent.start();
		});return;
	}
	var actCount=$("#actCount").val();
	var userCount=$("#userCount").val();
	if(actCount!=0&&parseInt(actCount)<=parseInt(userCount)){
		var dlg = box_dialog({ title:'友情提示~', content:'抽奖总次数已用完！' });
		dlg.find('.foot .-gifts').text('关闭').click(function(){
			dlg.remove();
			shakeEvent.start();
		});return;
	}
	var count=$("#count").html();//剩余抽奖次数。
	if(parseInt(count)<=0){
		var dlg = box_dialog({ title:'友情提示~', content:'今日抽奖次数已用完！' });
		dlg.find('.foot .-gifts').text('关闭').click(function(){
			dlg.remove();
			shakeEvent.start();
		});return;
	}
	$('.shakeimg').rotate({angle:25});
	setTimeout(function(){$('.shakeimg').rotate({angle:0});}, 500);
	setTimeout(function(){ sbox = $('<div class="msgtips search"><div>正在搜寻奖品...</div></div>').appendTo(document.body); }, 1200);
	setTimeout(function(){
		   shakeTicket();
	    }, 1500 + ~~(Math.random() * 1500));
};

function shakeTicket() {
	var basePath=$("#basePath").val();
	var actId = $("#actId").val();
	var openid = $("#openid").val();
	var subscribe = $("#subscribe").val();
	var jwid = $("#jwid").val();
	var url = "../shaketicket/shakeTicket.do";
	$.getJSON(url, {"actId": actId,"openid" : openid,"subscribe":subscribe,"jwid" : jwid}, function(data,status,xhr){	
		if (!!sbox) sbox.remove();
		if (data.success) {
			try {
				var drawStatus = data.attributes.shaketicketRecord.drawStatus;//抽奖状态
				var awardsId = data.attributes.shaketicketRecord.id;//奖品id
				var awardsCode = data.attributes.shaketicketRecord.awardCode;//奖品code
				var awardsName = data.attributes.shaketicketAward.awardsName;//奖品名称
				var jwid = data.attributes.shaketicketAward.jwid;//奖品名称
				var owner = data.attributes.shaketicketAward.owner;//发奖公司
				var cardId = data.attributes.shaketicketAward.cardId;//卡券ID
				var isCard = data.attributes.shaketicketAward.isCard;//是否卡券
				var img = data.attributes.shaketicketAward.img;//奖品图片
			} catch (e) {
			}
			
			if(drawStatus == '0'){//未中奖					
				var msg = "真遗憾，再接再厉";
				if(msg==null||msg==""){
					msg = (NOGIFT_TIPs[~~(Math.random()*NOGIFT_TIPs.length)]||NOGIFT_TIPs[0]);
				}
				var dlg = box_dialog({ title:'没有摇到~', content:'<p style="margin:0;margin-top:9px;">' + msg + '</p>' });
				dlg.find('.foot .-gifts').text('继续摇').click(function(){
					dlg.remove();
					shakeEvent.start();
				});
				
			}else{//已中奖
				var gift ="";
				var hf ="";
				if(isCard=='1'){//是卡券
					gift = '<div><span style="margin:4px 0;display:inline-block;color:#999">恭喜你获得</span><br><b style="font-size:16px;"></b><br><b style="font-size:16px;">' + awardsName + '</b></div>';
					var dlg = box_dialog2({ title:'恭喜！中奖啦！', content:gift });
					dlg.find('.foot .-gifts').attr('onclick', 'addCard("'+data.attributes.shaketicketRecord.id+'")');
				}else{
					gift += '<div><b style="font-size:16px;">' + awardsName + '</b><br>';
                    gift += '<img src="'+img+'"  style="width:30%"></div>';
					var dlg = box_dialog({ title:'恭喜！中奖啦！', content:gift });
					dlg.find('.foot .-share').text('领奖');
					$(".foot .-share").attr("onclick","updateRecord('"+awardsId+"','','','','"+awardsCode+"');");
					dlg.find('.foot .-share').click(function(){
						dlg.remove();
						shakeEvent.start();
					});
					dlg.find('.foot .-gifts').text('关闭').click(function(){
						dlg.remove();
						shakeEvent.start();
					});
				}
			}	
			var use_times = $("#count").html();//每天剩余抽奖次数
			if(use_times==null){
				use_times = $("#num").html();//剩余抽奖次数
				use_times--;
				$("#num").html(use_times);
			}else{						
				use_times--;
				$("#count").html(use_times);
			}
		} else {
			if(data.obj=="isNotFoucs"){
				$('#no_focus').show();
    		}else if(data.obj=="isNotBind"){
				var dlg = box_dialog({ title:'友情提示', content:$('#no_binding_phone').html() });
				dlg.find('.foot .-gifts').text('关闭').click(function(){
					dlg.remove();
					shakeEvent.start();
				});
    		}else{   			
				var msg = data.msg;
				var dlg = box_dialog({ title:'友情提示~', content:'<p style="margin:0;margin-top:9px;">' + msg + '</p>' });
				dlg.find('.foot .-gifts').text('关闭').click(function(){
					dlg.remove();
					shakeEvent.start();
				});
    		}
		}
	});

}  

//初始化下标
function resetIndex(idv) {
	$box = $("#"+idv+"");
	$box.find("tr").each(function(i){
		$(this).find("td").each(function(){
			var $this = $(this).children(), name = $this.attr('name'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
		});
	});	
}

Date.prototype.format=function(format){
	if(!format){
		format="yyyy年MM月dd日 HH:mm:ss";
	}
	var paddNum = function(num){
		num += "";
		return num.replace(/^(\d)$/,"0$1");
	}
	 var cfg = {
		yyyy :this.getFullYear()//年
		,yy : this.getFullYear().toString().substring(2)//年
		,M  : this.getMonth() + 1  //月
		,MM : paddNum(this.getMonth() + 1) //月
		,d  : this.getDate()//日
		,dd : paddNum(this.getDate())//日
		,HH : paddNum(this.getHours())//时
		,mm : paddNum(this.getMinutes())//分
		,ss : paddNum(this.getSeconds())//秒
	};
	return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
}
function clwz(content){
	if(!content){
		return "";
	}
	if(content.length>5){
		return content.substring(0,5)+"...";
	}
	return content;
}