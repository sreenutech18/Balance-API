package com.citibank.rewards.balance.validator;

import org.springframework.stereotype.Component;

import com.citibank.rewards.balance.exception.BalanceRequestInvalidDataException;
import com.citibank.rewards.balance.model.BalanceRequest;

@Component
public class BalanceValidator {

	public void validateRequest(BalanceRequest request) throws BalanceRequestInvalidDataException {
	
		System.out.println("entered into validator");
		
		//validate the cardnumber
		if (request.getCardNum() == null || " ".equals(request.getCardNum())) {

			throw new BalanceRequestInvalidDataException("bal001", "Invalid CardNum");
		}
		
		//validate the clientid

		if (request.getClientId() == null || " ".equals(request.getClientId())) {

			throw new BalanceRequestInvalidDataException("bal002", "Invalid clientId");
		}
		
		//validate the requestid

		if (request.getRequestId() == null || " ".equals(request.getRequestId())) {

			throw new BalanceRequestInvalidDataException("bal03", "Invalid RequestId");
		}
		
		
        //validate the message ts
		if (request.getMsgTs() == null || " ".equals(request.getMsgTs())) {

			throw new BalanceRequestInvalidDataException("bal004", "Invalid message timestamp");
		}

	}

}
