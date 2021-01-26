package com.citibank.rewards.balance.validator;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.citibank.rewards.balance.exception.BalanceRequestInvalidDataException;
import com.citibank.rewards.balance.model.BalanceRequest;

public class BalanceValidatorTest {

	BalanceRequest request = null;

	@Before
	public void setup() {
		buildRequest();
	}

	private void buildRequest() {
		request = new BalanceRequest();
		request.setCardNum("23232323233232");
		request.setClientId("web");
		request.setRequestId("232323");
		request.setMsgTs("21-01-2021");
	}

	@Test
	public void testCardnumber_Null_Scenarios() {

		request.setCardNum(null);

		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal001", e.getRespCode());

		}
	}

	@Test
	public void testCardnumber_Empty_Scenarios() {

		request.setCardNum(" ");

		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal001", e.getRespCode());

		}
	}

	@Test
	public void testClientId_Null_Scenarios() {

		request.setClientId(null);
		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal002", e.getRespCode());

		}
	}

	@Test
	public void testClientId_Empty_Scenarios() {

		request.setClientId(" ");
		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal002", e.getRespCode());

		}
	}

	@Test
	public void testRequestId_Null_Scenarios() {

		request.setRequestId(null);

		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal03", e.getRespCode());

		}
	}

	@Test
	public void testRequestId_Empty_Scenarios() {

		request.setRequestId(" ");
		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal03", e.getRespCode());

		}
	}
	
	@Test
	public void testMessageTS_Empty_Scenarios() {

		request.setMsgTs(" ");

		BalanceValidator validator = new BalanceValidator();
		try {
			validator.validateRequest(request);
		} catch (BalanceRequestInvalidDataException e) {

			assertEquals("bal004", e.getRespCode());

		}
	}

	@After
	public void tierDown() {

		request = null;

	}

}
