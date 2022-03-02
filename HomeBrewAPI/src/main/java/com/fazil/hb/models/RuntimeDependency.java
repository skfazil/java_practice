package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class RuntimeDependency{
    @JsonProperty("full_name") 
    public String getFull_name() { 
		 return this.full_name; } 
    public void setFull_name(String full_name) { 
		 this.full_name = full_name; } 
    String full_name;
    @JsonProperty("version") 
    public String getVersion() { 
		 return this.version; } 
    public void setVersion(String version) { 
		 this.version = version; } 
    String version;
    @JsonProperty("declared_directly") 
    public boolean getDeclared_directly() { 
		 return this.declared_directly; } 
    public void setDeclared_directly(boolean declared_directly) { 
		 this.declared_directly = declared_directly; } 
    boolean declared_directly;
}
