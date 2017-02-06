package com.prosnav.oms.util;

import com.prosnav.common.Constants;

import tab.MyPhoner;

public class CallCenter {
	public static void main(String[] args) {
		String sAgent = "1014";
		String sGroups = "";
		String sExtension = "1014";
		String sPhoneNumber = "18521522556";
		String sData = "";
		System.out.println(Constants.CALLCENTER_HOST);
		System.out.println(Constants.CALLCENTER_PORT);
		try{
//			MyPhoner phoner = new MyPhoner(Constants.CALLCENTER_HOST, Constants.CALLCENTER_PORT);
//			System.out.println("登录!");
//			System.out.println(phoner.Login(sAgent, sGroups, sExtension));
//			System.out.println(phoner.Dial(sPhoneNumber, sData, sExtension));
//			System.out.println(phoner.Answer(sExtension));
//			System.out.println(phoner.Hangup(sExtension));
//			System.out.println(phoner.Ready(1, sExtension));
//			System.out.println(phoner.Callback(sPhoneNumber, sPhoneNumber, sExtension));
//			System.out.println(phoner.Logout(sExtension));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
