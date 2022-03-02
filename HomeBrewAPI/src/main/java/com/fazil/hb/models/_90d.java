package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class _90d{
    @JsonProperty("wget") 
    public int getWget() { 
		 return this.wget; } 
    public void setWget(int wget) { 
		 this.wget = wget; } 
    int wget;
    @JsonProperty("wget --HEAD") 
    public int getWgetHEAD() { 
		 return this.wgetHEAD; } 
    public void setWgetHEAD(int wgetHEAD) { 
		 this.wgetHEAD = wgetHEAD; } 
    int wgetHEAD;
}
