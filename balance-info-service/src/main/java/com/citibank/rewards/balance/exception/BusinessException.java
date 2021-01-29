package com.citibank.rewards.balance.exception;

public class BusinessException extends Exception{
	
	/**
	 * 
	 */
	private  String respCode;
	private  String respMsg;

	public BusinessException(final String respCode, final String respMsg) {

		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}
	
	public void setRespCode(final String respCode) {
		this.respCode = respCode;
	}

	public void setRespMsg(final String respMsg) {
		this.respMsg = respMsg;
	}

}
