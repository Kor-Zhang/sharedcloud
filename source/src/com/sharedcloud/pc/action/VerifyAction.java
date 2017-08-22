package com.sharedcloud.pc.action;

import java.awt.image.BufferedImage;

import cn.itcast.vcode.utils.VerifyCode;

import com.sharedcloud.pc.model.Users;

public class VerifyAction extends BaseAction {

	/**
	 * 获取验证码
	 * @throws Exception
	 */
	public void getVerifyCode() throws Exception{
		/*
		 * 必须放在get
		 */
		VerifyCode vc=new VerifyCode();
		BufferedImage bi=vc.getImage();
		//设置验证码文本到application
		setSessionAttr(Users.VERIFY_CODE_NAME, vc.getText().toLowerCase());
		//写入图片
		VerifyCode.output(bi, getResponse().getOutputStream());
	}
	
}
