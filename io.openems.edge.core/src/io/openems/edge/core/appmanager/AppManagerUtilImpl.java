package io.openems.edge.core.appmanager;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import com.google.gson.JsonObject;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.session.Language;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AppManagerUtilImpl implements AppManagerUtil {

	
	
	private final Logger log = LoggerFactory.getLogger(AppManagerUtilImpl.class);

	@Reference(policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY, cardinality = ReferenceCardinality.OPTIONAL)
	private volatile AppManager appManager;

	@Activate
	public AppManagerUtilImpl() {
	}

	@Override
	public OpenemsApp getAppById(String appId) throws NoSuchElementException {
		try{
			return this.getAppManagerImpl().findAppById(appId);
			
		} catch (Exception err) {
			logDebug(err.toString());
			return null;
		}
	}

	@Override
	public OpenemsAppInstance getInstanceById(UUID instanceId) throws NoSuchElementException {
		return this.getAppManagerImpl().findInstanceById(instanceId);
	}

	@Override
	public AppConfiguration getAppConfiguration(ConfigurationTarget target, OpenemsApp app, String alias,
			JsonObject properties, Language language) throws OpenemsNamedException {
		if (alias != null) {
			properties.addProperty("ALIAS", alias);
		}
		AppConfiguration config = null;
		OpenemsNamedException error = null;
		try {
			config = app.getAppConfiguration(target, properties, language);
		} catch (OpenemsNamedException e) {
			error = e;
		}
		if (alias != null) {
			properties.remove("ALIAS");
		}
		if (error != null) {
			throw error;
		}
		return config;
	}

	private final AppManagerImpl getAppManagerImpl() {
		return (AppManagerImpl) this.appManager;
	}
	
	private void logDebug(String message) {
		this.log.debug(message);
	}


}
