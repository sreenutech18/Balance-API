package com.citibank.rewards.balance.dao.impl;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.citibank.rewards.balance.dao.BalanceDAO;
import com.citibank.rewards.balance.exception.BusinessException;
import com.citibank.rewards.balance.exception.SystemException;
import com.citibank.rewards.balance.model.BalanceDAORequest;
import com.citibank.rewards.balance.model.BalanceDAOResponse;
import com.citibank.rewards.balance.util.BalanceConstants;

@Component
public class BalanceDAOImpl implements BalanceDAO {
	private final static Logger logger = Logger.getLogger(BalanceDAOImpl.class);

	public BalanceDAOResponse getBalance(final BalanceDAORequest daoReq) throws BusinessException, SystemException {

		logger.info("Entered into DAO Layer,Request is coming from Service layer" + daoReq);
		// System.out.println("Entered into dao :" + daoReq);

		String env = System.getProperty("env");
		String fileName = "properties/balance-" + env + "-db.properties";

		System.out.println("env : " + env + " fileName :" + fileName);

		final BalanceDAOResponse daoResponse = new BalanceDAOResponse();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			InputStream input = BalanceDAOImpl.class.getClassLoader().getResourceAsStream(fileName);

			Properties properties = new Properties();

			properties.load(input);

			String url = properties.getProperty(BalanceConstants.DB_URL);
			String uname = properties.getProperty(BalanceConstants.USER_NAME);
			String pwd = properties.getProperty(BalanceConstants.PASSWORD);

			Connection connection = DriverManager.getConnection(url, uname, pwd);
			String sql = BalanceConstants.SP_CALL;

			// csmt object
			CallableStatement cs = connection.prepareCall(sql);
			// prepare the input params
			cs.setString(1, daoReq.getClientId());
			cs.setString(2, daoReq.getCardNum());

			// register out params
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);

			// call DB and get the result set

			cs.execute();
			ResultSet rs = cs.executeQuery();

			String dbRespCode = cs.getString(3); // get it from backend
			String dbRespMsg = cs.getString(4);

			daoResponse.setRespCode(dbRespCode);
			daoResponse.setRespMsg(dbRespMsg);

			if ("000".equals(dbRespCode)) {
				// prepare the dao response. i.e convert ResultSet response into dao response
				while (rs.next()) {
					daoResponse.setAvailablePts(rs.getString(BalanceConstants.AVAIL_PTS));
					daoResponse.setEarnedPts(rs.getString(BalanceConstants.EARNED_PTS));
					daoResponse.setPendingPts(rs.getString(BalanceConstants.ADJ_PTS));

				}

			} else if ("100".equals(dbRespCode) || "101".equals(dbRespCode) || "102".equals(dbRespCode)
					|| "103".equals(dbRespCode)) {

				throw new BusinessException(dbRespCode, dbRespMsg);

			} else {
				throw new SystemException(dbRespCode, dbRespMsg);
			}
		} catch (BusinessException e) {

			// TODO : print error log

			throw e;
		} catch (SystemException e) {

			// TODO : print error log
			throw e;
		} catch (Exception e) {
			logger.fatal("Exception in DAO Layer", e);

			// e.printStackTrace();
			// TODO : print error log
			throw new SystemException("1111", "unknown error from database");
		}

		logger.info("Exit from DAO Layer,Response to Service layer" + daoResponse);
		// System.out.println("Exit from dao :" + daoResponse);
		return daoResponse;
	}

}