var CallCenterController = (function() {
	return {
		callCenterStatus: {
            //拨打中
            dial : 'Dial',
            //已呼出
            dialed : 'Dialed',
            //拨打失败
            dialFailed : 'DialFailed',
            //接通
            connected : 'Connected',
            //挂断
            hangup : 'Hangup',
            //已断开
            disconnect : 'Disconnect',
            //登入
            login : 'login',
            //登出
            logout : 'logout',
            //开始录音
            recording : 'recording'
		},
		login: function(optional) {
			var agent = optional.agent;
			var ext = optional.extension;
			var group = optional.group;
			if(!$.isNumeric(ext)) {
				return {sucess: false, msg: '分机号不正确：'+ext};
			}
			var o = MyPhoner.Login(agent, group, ext, null, null);
			if(o == 0) {
				var obj = MyPhoner.GetLoginInfo();
				if(obj == 0) {
					console.log("Agent:"+MyPhoner.Agent+", Extension:"+MyPhoner.Extension);
				}
				var events = MyPhoner.GetEvents(function(message) {
					console.log("message:", message);
					CallCenterController().callCenterEventCallback(message);
				})
				return {success: true};
			}else {
				return {success: false};
			}
		},
		setup: function(optional) {
			var callCenterAddress = optional.callCenterAddress;
			console.log(callCenterAddress);
			$.getScript('http://'+callCenterAddress+'/MyCallProxy/MyPhoner.js', function() {
				MyPhoner.host = callCenterAddress;
				var callCenterAgent = optional.callCenterAgent;
				var callCenterExtension = optional.callCenterExtension;
				var callCenterGroup = optional.callCenterGroup;
				console.log('callCenterLogin: agent:'+callCenterAgent+", extension:"+callCenterExtension+", group:"+callCenterGroup);;
				var callCenterLoginResult = CallCenterController().login({agent: callCenterAgent, extension: callCenterExtension, group: callCenterGroup});
				if(!callCenterLoginResult.success) {
					toast({type: 'error', body: '登录呼叫中心失败'}, true);
				}else {
					toast({type: 'success', body: '已成功登录呼叫中心'}, true);
				}
			})
		},
        callCenterEventCallback: function(message) {
            var status = CallCenterController().callCenterGetStatusFromMessage(message);
            console.log('status : ' + status);
            var callCenterStatus = CallCenterController().callCenterStatus;
            if(!status) {
                return;
            }
            if(status == callCenterStatus.dialed) {
                callCenterCurrentStatus = callCenterStatus.dialed;
                toast({type : 'info', body : '已呼出,等待接听...'}, true);
            } else if(status == callCenterStatus.connected) {
                callCenterCurrentStatus = callCenterStatus.connected;
                toast({type : 'info', body : '已接听,通话中...'}, true);
            } else if(status == callCenterStatus.hangup) {
                callCenterCurrentStatus = callCenterStatus.hangup;
                toast({type : 'info', body : '已主动挂断'}, true);
            } else if(status == callCenterStatus.disconnect) {
                callCenterCurrentStatus = callCenterStatus.disconnect;
                toast({type : 'info', body : '对方已挂断'}, true);
            } else if(status == callCenterStatus.logout) {
                toast({type : 'info', body : '已退出登录'}, true);
            } else if(status == callCenterStatus.recording) {
            	toast({type : 'info', body : '录音已开始...'});
            	CallCenterController().callCenterAddRecording();
            } else {
                console.log("undeal status : " + status);
            }
        },
        callCenterGetStatusFromMessage(message) {
        	var callCenterStatus = CallCenterController().callCenterStatus;
            if(!message) {
                return null;
            }
            //拨打中但未呼出
            var pdial = /Dial.0.Succeed.*/;
            //拨打中已呼出
            var pdialed = /Dial.[0-9]{11}/;
            //拨打失败
            var pdialFailed = /Dial:-1.*/;
            //已接通
            var pconnected = /Connected/;
            //主动挂断
            var phangup = /Hangup.*Succeed.*/;
            //已挂断
            var pdisconnect = /Disconnect/;
            //登入
            var plogin = /Login:0.Succeed.*/;
            //登出
            var plogout = /Close/;
            //开始录音
            var precording = /Recording/;

            if(pdial.test(message)) {
                return callCenterStatus.dial;
            } else if(pdialed.test(message)) {
                return callCenterStatus.dialed;
            } else if(pconnected.test(message)) {
                return callCenterStatus.connected;
            } else if(phangup.test(message)) {
                return callCenterStatus.hangup;
            } else if(pdisconnect.test(message)) {
                return callCenterStatus.disconnect;
            } else if(plogin.test(message)) {
                return callCenterStatus.login;
            } else if(plogout.test(message)) {
                return callCenterStatus.logout;
            } else if(pdialFailed.test(message)) {
                return callCenterStatus.dialFailed;
            } else if(precording.test(message)) {
                return callCenterStatus.recording;
            } else {
                return message;
            }
        },
        callCenterAddRecording: function() {
        	 var totalCount = MyPhoner.GetCallInfo();
             if(MyPhoner.callback == ""){
                 if(totalCount >= 0) {
                     console.log("GetCallInfo:" + JSON.stringify(MyPhoner.Calls));
                     var recording = {};
                     recording.order_no = orderNo;
                     recording.record_id = MyPhoner.Calls.recordId;
                     $.ajax({
                         type: 'POST',
                         dataType: 'json',
                         url: '/OMS/add_recording.action',
                         data: recording,
                         success: function(data){
                        	 if(data.desc == "1") {
                        		 toast({type : 'success', body : data.info});
                        	 }else {
                        		 toast({type : 'warning', body : data.info});
                        	 }
                         }
                     });
                 } else {
                     console.log("GetCallInfo:" + totalCount +"(" + MyPhoner.Reason + ")");
                 }
             }
        }
	}
})