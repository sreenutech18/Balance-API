package com.citibank.rewards.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citibank.rewards.balance.dao.BalanceDAO;
import com.citibank.rewards.balance.dao.impl.BalanceDAOImpl;
import com.citibank.rewards.balance.exception.BusinessException;
import com.citibank.rewards.balance.exception.SystemException;
import com.citibank.rewards.balance.model.BalanceDAORequest;
import com.citibank.rewards.balance.model.BalanceDAOResponse;
import com.citibank.rewards.balance.model.BalanceInfo;
import com.citibank.rewards.balance.model.BalanceRequest;
import com.citibank.rewards.balance.model.BalanceResponse;
import com.citibank.rewards.balance.model.StatusBlock;
import com.citibank.rewards.balance.service.BalanceService;

@Component
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	BalanceDAO balanceDAO;

	public BalanceResponse getBalance(BalanceRequest request) throws BusinessException, SystemException {

		System.out.println("Entered into service BalanceServiceImpl"+request);
		// prepare the dao request
		BalanceDAORequest daoReq = new BalanceDAORequest();
		daoReq.setCardNum(request.getCardNum());
		daoReq.setClientId(request.getClientId());

		BalanceDAOResponse daoResp = balanceDAO.getBalance(daoReq);
		
	

		// prepare the service response

		BalanceResponse response = new BalanceResponse();

		StatusBlock statusBlock = new StatusBlock();
		statusBlock.setRespCode(daoResp.getRespCode());
		statusBlock.setRespMsg(daoResp.getRespMsg());

		BalanceInfo balanceInfo = new BalanceInfo();
		balanceInfo.setAvailablePts(daoResp.getAvailablePts());
		balanceInfo.setEarnedPts(daoResp.getEarnedPts());
		balanceInfo.setPendingPts(daoResp.getPendingPts());

		response.setStatusBlock(statusBlock);
		response.setBalanceInfo(balanceInfo);

		return response;
	}

}
