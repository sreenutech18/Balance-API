package com.citibank.rewards.balance.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citibank.rewards.balance.exception.BalanceRequestInvalidDataException;
import com.citibank.rewards.balance.exception.BusinessException;
import com.citibank.rewards.balance.exception.SystemException;
import com.citibank.rewards.balance.model.BalanceRequest;
import com.citibank.rewards.balance.model.BalanceResponse;
import com.citibank.rewards.balance.service.BalanceService;
import com.citibank.rewards.balance.validator.BalanceValidator;

@RestController
public class BalanceController {
	private  Logger logger = Logger.getLogger(BalanceController.class);
	@Autowired
	BalanceValidator balanceValidator;
	@Autowired
	BalanceService balanceService;

	@RequestMapping(method = RequestMethod.GET , value = "/balance/{cardNum}", produces = {"application/json","application/xml"})
	public BalanceResponse getBalance( @PathVariable("cardNum" ) String cardNum,
			                           @RequestHeader(value="client-Id", required=false) String clientId, 
			                           @RequestHeader(value="request-id", required=false) String requestId,
			                           @RequestHeader(value="msg-ts", required=false) String msgts) throws BalanceRequestInvalidDataException, BusinessException, SystemException {

		logger.info("In Controller Request from Resource:"+cardNum);
		// prepare the balance request
		BalanceRequest request = new BalanceRequest();
		request.setCardNum(cardNum);
		request.setClientId(clientId);
		request.setRequestId(requestId);
		request.setMsgTs(msgts);

		// validate the request
		balanceValidator.validateRequest(request);
		
		// Call service layer 
		BalanceResponse response  = balanceService.getBalance(request);
		
		logger.info("In Controller Response to Resource:"+response);
		return response;

	}

}
