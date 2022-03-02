package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Versions{
    @JsonProperty("stable") 
    public String getStable() { 
		 return this.stable; } 
    public void setStable(String stable) { 
		 this.stable = stable; } 
    String stable;
    @JsonProperty("head") 
    public String getHead() { 
		 return this.head; } 
    public void setHead(String head) { 
		 this.head = head; } 
    String head;
    @JsonProperty("bottle") 
    public boolean getBottle() { 
		 return this.bottle; } 
    public void setBottle(boolean bottle) { 
		 this.bottle = bottle; } 
    boolean bottle;
}
