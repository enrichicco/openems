package io.openems.edge.core.appmanager;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.JsonObject;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.session.Language;
import io.openems.edge.common.component.ComponentManager;

@Component
public class AppManagerUtilImpl implements AppManagerUtil {

	private final ComponentManager componentManager;

	@Activate
	public AppManagerUtilImpl(@Reference ComponentManager componentManager) {
		this.componentManager = componentManager;
	}

	@Override
	public OpenemsApp getAppById(String appId) throws NoSuchElementException {
		return this.getAppManagerImpl().findAppById(appId);
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
		var appManagerList = this.componentManager.getEnabledComponentsOfType(AppManager.class);
		if (appManagerList.size() == 0) {
			return null;
		}
		return (AppManagerImpl) appManagerList.get(0);
	}

}
