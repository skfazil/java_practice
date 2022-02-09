package com.cushina.common.util;

import java.util.ResourceBundle;



public class PropUtil {
	
	public static final ResourceBundle MAIL_BUNDLE = ResourceBundle.getBundle("mail");
	
	public static final String MAIL_HOST = PropUtil.MAIL_BUNDLE.getString("mail.host");
	
	public static final String MAIL_PORT = PropUtil.MAIL_BUNDLE.getString("mail.port");
	
	public static final String MAIL_CLASS = PropUtil.MAIL_BUNDLE.getString("mail.class");
	
	public static final String MAIL_AUTH = PropUtil.MAIL_BUNDLE.getString("mail.auth");
	
	public static final String MAIL_EMAIL = PropUtil.MAIL_BUNDLE.getString("mail.email");
	
	public static final String MAIL_PASSWORD = PropUtil.MAIL_BUNDLE.getString("mail.password");
	
	public static final String MAIL_URL = PropUtil.MAIL_BUNDLE.getString("mail.url");
	
	public static final String MAIL_MESSAGE = PropUtil.MAIL_BUNDLE.getString("mail.message");
	
	public static final String MAIL_SUBJECT = PropUtil.MAIL_BUNDLE.getString("mail.subject");
	
	public static final String FORGOT_MAIL_SUBJECT = PropUtil.MAIL_BUNDLE.getString("mail.forgotsubject");
	
	public static final long PASS_RESET_LINK_DDEFAULT_VALID_TIME = 24*60*60*1000;
	
}
