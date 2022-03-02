package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Files{
    @JsonProperty("arm64_monterey") 
    public Arm64Monterey getArm64_monterey() { 
		 return this.arm64_monterey; } 
    public void setArm64_monterey(Arm64Monterey arm64_monterey) { 
		 this.arm64_monterey = arm64_monterey; } 
    Arm64Monterey arm64_monterey;
    @JsonProperty("arm64_big_sur") 
    public Arm64BigSur getArm64_big_sur() { 
		 return this.arm64_big_sur; } 
    public void setArm64_big_sur(Arm64BigSur arm64_big_sur) { 
		 this.arm64_big_sur = arm64_big_sur; } 
    Arm64BigSur arm64_big_sur;
    @JsonProperty("monterey") 
    public Monterey getMonterey() { 
		 return this.monterey; } 
    public void setMonterey(Monterey monterey) { 
		 this.monterey = monterey; } 
    Monterey monterey;
    @JsonProperty("big_sur") 
    public BigSur getBig_sur() { 
		 return this.big_sur; } 
    public void setBig_sur(BigSur big_sur) { 
		 this.big_sur = big_sur; } 
    BigSur big_sur;
    @JsonProperty("catalina") 
    public Catalina getCatalina() { 
		 return this.catalina; } 
    public void setCatalina(Catalina catalina) { 
		 this.catalina = catalina; } 
    Catalina catalina;
    @JsonProperty("x86_64_linux") 
    public X8664Linux getX86_64_linux() { 
		 return this.x86_64_linux; } 
    public void setX86_64_linux(X8664Linux x86_64_linux) { 
		 this.x86_64_linux = x86_64_linux; } 
    X8664Linux x86_64_linux;
}
