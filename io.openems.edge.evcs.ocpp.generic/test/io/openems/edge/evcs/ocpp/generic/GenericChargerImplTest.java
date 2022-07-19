package io.openems.edge.evcs.ocpp.generic;

import org.junit.Test;

import io.openems.edge.common.test.AbstractComponentTest.TestCase;
import io.openems.edge.evcs.ocpp.generic.GenericChargerImpl;
import io.openems.edge.common.test.ComponentTest;

public class GenericChargerImplTest {

	private static final String COMPONENT_ID = "component0";

	@Test
	public void test() throws Exception {
		new ComponentTest(new GenericChargerImpl()) //
				.activate(TestConfig.create() //
						.setId(COMPONENT_ID) //
						.build())
				.next(new TestCase());
	}

}
