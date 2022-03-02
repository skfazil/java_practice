package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class InstallOnRequest{
    @JsonProperty("30d") 
    public _30d get_30d() { 
		 return this._30d; } 
    public void set_30d(_30d _30d) { 
		 this._30d = _30d; } 
    _30d _30d;
    @JsonProperty("90d") 
    public _90d get_90d() { 
		 return this._90d; } 
    public void set_90d(_90d _90d) { 
		 this._90d = _90d; } 
    _90d _90d;
    @JsonProperty("365d") 
    public _365d get_365d() { 
		 return this._365d; } 
    public void set_365d(_365d _365d) { 
		 this._365d = _365d; } 
    _365d _365d;
}
