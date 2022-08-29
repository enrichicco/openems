package io.openems.edge.evcs.ocpp.serveryp.api;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "Evcs.Ocpp.Serveryp.Api", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE //
)

public class OcppApiImpl implements OcppApi {

	private final Logger log = LoggerFactory.getLogger(OcppApiImpl.class);
	protected Config config;
	
	public String sayHello(String name) {
		return "Hello " + name;
	}
	
}
