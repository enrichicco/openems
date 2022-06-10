package io.openems.edge.evcs.ocpp.versinetic;

import io.openems.edge.common.test.AbstractComponentConfig;

@SuppressWarnings("all")
public class TestConfig extends AbstractComponentConfig implements Config {

	protected static class Builder {
		private String id;
//		private String setting0;

		private Builder() {
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

//		public Builder setSetting0(String setting0) {
//			this.setting0 = setting0;
//			return this;
//		}

		public TestConfig build() {
			return new TestConfig(this);
		}
	}

	/**
	 * Create a Config builder.
	 * 
	 * @return a {@link Builder}
	 */
	public static Builder create() {
		return new Builder();
	}

	private final Builder builder;

	private TestConfig(Builder builder) {
		super(Config.class, builder.id);
		this.builder = builder;
	}

	@Override
	public String ocpp_id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int connectorId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String logicalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String limitId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int maxHwCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minHwCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public String setting0() {
//		return this.builder.setting0;
//	}

}