package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List; 
public class Installed{
    @JsonProperty("version") 
    public String getVersion() { 
		 return this.version; } 
    public void setVersion(String version) { 
		 this.version = version; } 
    String version;
    @JsonProperty("used_options") 
    public ArrayList<Object> getUsed_options() { 
		 return this.used_options; } 
    public void setUsed_options(ArrayList<Object> used_options) { 
		 this.used_options = used_options; } 
    ArrayList<Object> used_options;
    @JsonProperty("built_as_bottle") 
    public boolean getBuilt_as_bottle() { 
		 return this.built_as_bottle; } 
    public void setBuilt_as_bottle(boolean built_as_bottle) { 
		 this.built_as_bottle = built_as_bottle; } 
    boolean built_as_bottle;
    @JsonProperty("poured_from_bottle") 
    public boolean getPoured_from_bottle() { 
		 return this.poured_from_bottle; } 
    public void setPoured_from_bottle(boolean poured_from_bottle) { 
		 this.poured_from_bottle = poured_from_bottle; } 
    boolean poured_from_bottle;
    @JsonProperty("runtime_dependencies") 
    public ArrayList<RuntimeDependency> getRuntime_dependencies() { 
		 return this.runtime_dependencies; } 
    public void setRuntime_dependencies(ArrayList<RuntimeDependency> runtime_dependencies) { 
		 this.runtime_dependencies = runtime_dependencies; } 
    ArrayList<RuntimeDependency> runtime_dependencies;
    @JsonProperty("installed_as_dependency") 
    public boolean getInstalled_as_dependency() { 
		 return this.installed_as_dependency; } 
    public void setInstalled_as_dependency(boolean installed_as_dependency) { 
		 this.installed_as_dependency = installed_as_dependency; } 
    boolean installed_as_dependency;
    @JsonProperty("installed_on_request") 
    public boolean getInstalled_on_request() { 
		 return this.installed_on_request; } 
    public void setInstalled_on_request(boolean installed_on_request) { 
		 this.installed_on_request = installed_on_request; } 
    boolean installed_on_request;
}
