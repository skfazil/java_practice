package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class BigSur{
    @JsonProperty("cellar") 
    public String getCellar() { 
		 return this.cellar; } 
    public void setCellar(String cellar) { 
		 this.cellar = cellar; } 
    String cellar;
    @JsonProperty("url") 
    public String getUrl() { 
		 return this.url; } 
    public void setUrl(String url) { 
		 this.url = url; } 
    String url;
    @JsonProperty("sha256") 
    public String getSha256() { 
		 return this.sha256; } 
    public void setSha256(String sha256) { 
		 this.sha256 = sha256; } 
    String sha256;
}
