package lee.won.hcv1.impl;

import java.io.Serializable;

public class ConfigFile implements Serializable{
	private String os = "default";

	public ConfigFile(String os) {
		// TODO Auto-generated constructor stub
		this.os = os;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

}
