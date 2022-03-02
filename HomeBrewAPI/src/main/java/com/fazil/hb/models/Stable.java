package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Stable{
    @JsonProperty("url") 
    public String getUrl() { 
		 return this.url; } 
    public void setUrl(String url) { 
		 this.url = url; } 
    String url;
    @JsonProperty("tag") 
    public Object getTag() { 
		 return this.tag; } 
    public void setTag(Object tag) { 
		 this.tag = tag; } 
    Object tag;
    @JsonProperty("revision") 
    public Object getRevision() { 
		 return this.revision; } 
    public void setRevision(Object revision) { 
		 this.revision = revision; } 
    Object revision;
    @JsonProperty("rebuild") 
    public int getRebuild() { 
		 return this.rebuild; } 
    public void setRebuild(int rebuild) { 
		 this.rebuild = rebuild; } 
    int rebuild;
    @JsonProperty("root_url") 
    public String getRoot_url() { 
		 return this.root_url; } 
    public void setRoot_url(String root_url) { 
		 this.root_url = root_url; } 
    String root_url;
    @JsonProperty("files") 
    public Files getFiles() { 
		 return this.files; } 
    public void setFiles(Files files) { 
		 this.files = files; } 
    Files files;
}
