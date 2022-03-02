package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Bottle{
    @JsonProperty("stable") 
    public Stable getStable() { 
		 return this.stable; } 
    public void setStable(Stable stable) { 
		 this.stable = stable; } 
    Stable stable;
}
