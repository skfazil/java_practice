package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Analytics{
    @JsonProperty("install") 
    public Install getInstall() { 
		 return this.install; } 
    public void setInstall(Install install) { 
		 this.install = install; } 
    Install install;
    @JsonProperty("install_on_request") 
    public InstallOnRequest getInstall_on_request() { 
		 return this.install_on_request; } 
    public void setInstall_on_request(InstallOnRequest install_on_request) { 
		 this.install_on_request = install_on_request; } 
    InstallOnRequest install_on_request;
    @JsonProperty("build_error") 
    public BuildError getBuild_error() { 
		 return this.build_error; } 
    public void setBuild_error(BuildError build_error) { 
		 this.build_error = build_error; } 
    BuildError build_error;
}
