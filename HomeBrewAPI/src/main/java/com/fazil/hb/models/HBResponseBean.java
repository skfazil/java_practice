package com.fazil.hb.models;

import java.util.List;

public class HBResponseBean {

	private String description;
	private String version;
	private List<String> dependencies;
	private String generated_date;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	public String getGenerated_date() {
		return generated_date;
	}

	public void setGenerated_date(String generated_date) {
		this.generated_date = generated_date;
	}

	@Override
	public String toString() {
		return "HBResponseBean [description=" + description + ", version=" + version + ", dependencies=" + dependencies
				+ ", generated_ate=" + generated_date + "]";
	}

}
