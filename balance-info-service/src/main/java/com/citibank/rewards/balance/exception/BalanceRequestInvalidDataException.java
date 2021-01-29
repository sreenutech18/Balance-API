package com.citibank.rewards.balance.exception;

public class BalanceRequestInvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String respMsg;

	public BalanceRequestInvalidDataException(String respCode, String respMsg) {

		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

}
