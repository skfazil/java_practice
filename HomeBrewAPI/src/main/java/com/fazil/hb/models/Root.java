package com.fazil.hb.models; 
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 
public class Root{
    @JsonProperty("name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
    @JsonProperty("full_name") 
    public String getFull_name() { 
		 return this.full_name; } 
    public void setFull_name(String full_name) { 
		 this.full_name = full_name; } 
    String full_name;
    @JsonProperty("tap") 
    public String getTap() { 
		 return this.tap; } 
    public void setTap(String tap) { 
		 this.tap = tap; } 
    String tap;
    @JsonProperty("oldname") 
    public Object getOldname() { 
		 return this.oldname; } 
    public void setOldname(Object oldname) { 
		 this.oldname = oldname; } 
    Object oldname;
    @JsonProperty("aliases") 
    public ArrayList<Object> getAliases() { 
		 return this.aliases; } 
    public void setAliases(ArrayList<Object> aliases) { 
		 this.aliases = aliases; } 
    ArrayList<Object> aliases;
    @JsonProperty("versioned_formulae") 
    public ArrayList<Object> getVersioned_formulae() { 
		 return this.versioned_formulae; } 
    public void setVersioned_formulae(ArrayList<Object> versioned_formulae) { 
		 this.versioned_formulae = versioned_formulae; } 
    ArrayList<Object> versioned_formulae;
    @JsonProperty("desc") 
    public String getDesc() { 
		 return this.desc; } 
    public void setDesc(String desc) { 
		 this.desc = desc; } 
    String desc;
    @JsonProperty("license") 
    public String getLicense() { 
		 return this.license; } 
    public void setLicense(String license) { 
		 this.license = license; } 
    String license;
    @JsonProperty("homepage") 
    public String getHomepage() { 
		 return this.homepage; } 
    public void setHomepage(String homepage) { 
		 this.homepage = homepage; } 
    String homepage;
    @JsonProperty("versions") 
    public Optional<Versions> getVersions() { 
		 return this.versions; } 
    public void setVersions(Optional<Versions> versions) { 
		 this.versions = versions; } 
    Optional<Versions> versions;
    @JsonProperty("urls") 
    public Urls getUrls() { 
		 return this.urls; } 
    public void setUrls(Urls urls) { 
		 this.urls = urls; } 
    Urls urls;
    @JsonProperty("revision") 
    public int getRevision() { 
		 return this.revision; } 
    public void setRevision(int revision) { 
		 this.revision = revision; } 
    int revision;
    @JsonProperty("version_scheme") 
    public int getVersion_scheme() { 
		 return this.version_scheme; } 
    public void setVersion_scheme(int version_scheme) { 
		 this.version_scheme = version_scheme; } 
    int version_scheme;
    @JsonProperty("bottle") 
    public Bottle getBottle() { 
		 return this.bottle; } 
    public void setBottle(Bottle bottle) { 
		 this.bottle = bottle; } 
    Bottle bottle;
    @JsonProperty("keg_only") 
    public boolean getKeg_only() { 
		 return this.keg_only; } 
    public void setKeg_only(boolean keg_only) { 
		 this.keg_only = keg_only; } 
    boolean keg_only;
    @JsonProperty("keg_only_reason") 
    public Object getKeg_only_reason() { 
		 return this.keg_only_reason; } 
    public void setKeg_only_reason(Object keg_only_reason) { 
		 this.keg_only_reason = keg_only_reason; } 
    Object keg_only_reason;
    @JsonProperty("bottle_disabled") 
    public boolean getBottle_disabled() { 
		 return this.bottle_disabled; } 
    public void setBottle_disabled(boolean bottle_disabled) { 
		 this.bottle_disabled = bottle_disabled; } 
    boolean bottle_disabled;
    @JsonProperty("options") 
    public ArrayList<Object> getOptions() { 
		 return this.options; } 
    public void setOptions(ArrayList<Object> options) { 
		 this.options = options; } 
    ArrayList<Object> options;
    @JsonProperty("build_dependencies") 
    public ArrayList<String> getBuild_dependencies() { 
		 return this.build_dependencies; } 
    public void setBuild_dependencies(ArrayList<String> build_dependencies) { 
		 this.build_dependencies = build_dependencies; } 
    ArrayList<String> build_dependencies;
    @JsonProperty("dependencies") 
    public ArrayList<String> getDependencies() { 
		 return this.dependencies; } 
    public void setDependencies(ArrayList<String> dependencies) { 
		 this.dependencies = dependencies; } 
    ArrayList<String> dependencies;
    @JsonProperty("recommended_dependencies") 
    public ArrayList<Object> getRecommended_dependencies() { 
		 return this.recommended_dependencies; } 
    public void setRecommended_dependencies(ArrayList<Object> recommended_dependencies) { 
		 this.recommended_dependencies = recommended_dependencies; } 
    ArrayList<Object> recommended_dependencies;
    @JsonProperty("optional_dependencies") 
    public ArrayList<Object> getOptional_dependencies() { 
		 return this.optional_dependencies; } 
    public void setOptional_dependencies(ArrayList<Object> optional_dependencies) { 
		 this.optional_dependencies = optional_dependencies; } 
    ArrayList<Object> optional_dependencies;
    @JsonProperty("uses_from_macos") 
    public ArrayList<Object> getUses_from_macos() { 
		 return this.uses_from_macos; } 
    public void setUses_from_macos(ArrayList<Object> uses_from_macos) { 
		 this.uses_from_macos = uses_from_macos; } 
    ArrayList<Object> uses_from_macos;
    @JsonProperty("requirements") 
    public ArrayList<Object> getRequirements() { 
		 return this.requirements; } 
    public void setRequirements(ArrayList<Object> requirements) { 
		 this.requirements = requirements; } 
    ArrayList<Object> requirements;
    @JsonProperty("conflicts_with") 
    public ArrayList<Object> getConflicts_with() { 
		 return this.conflicts_with; } 
    public void setConflicts_with(ArrayList<Object> conflicts_with) { 
		 this.conflicts_with = conflicts_with; } 
    ArrayList<Object> conflicts_with;
    @JsonProperty("caveats") 
    public Object getCaveats() { 
		 return this.caveats; } 
    public void setCaveats(Object caveats) { 
		 this.caveats = caveats; } 
    Object caveats;
    @JsonProperty("installed") 
    public ArrayList<Installed> getInstalled() { 
		 return this.installed; } 
    public void setInstalled(ArrayList<Installed> installed) { 
		 this.installed = installed; } 
    ArrayList<Installed> installed;
    @JsonProperty("linked_keg") 
    public String getLinked_keg() { 
		 return this.linked_keg; } 
    public void setLinked_keg(String linked_keg) { 
		 this.linked_keg = linked_keg; } 
    String linked_keg;
    @JsonProperty("pinned") 
    public boolean getPinned() { 
		 return this.pinned; } 
    public void setPinned(boolean pinned) { 
		 this.pinned = pinned; } 
    boolean pinned;
    @JsonProperty("outdated") 
    public boolean getOutdated() { 
		 return this.outdated; } 
    public void setOutdated(boolean outdated) { 
		 this.outdated = outdated; } 
    boolean outdated;
    @JsonProperty("deprecated") 
    public boolean getDeprecated() { 
		 return this.deprecated; } 
    public void setDeprecated(boolean deprecated) { 
		 this.deprecated = deprecated; } 
    boolean deprecated;
    @JsonProperty("deprecation_date") 
    public Object getDeprecation_date() { 
		 return this.deprecation_date; } 
    public void setDeprecation_date(Object deprecation_date) { 
		 this.deprecation_date = deprecation_date; } 
    Object deprecation_date;
    @JsonProperty("deprecation_reason") 
    public Object getDeprecation_reason() { 
		 return this.deprecation_reason; } 
    public void setDeprecation_reason(Object deprecation_reason) { 
		 this.deprecation_reason = deprecation_reason; } 
    Object deprecation_reason;
    @JsonProperty("disabled") 
    public boolean getDisabled() { 
		 return this.disabled; } 
    public void setDisabled(boolean disabled) { 
		 this.disabled = disabled; } 
    boolean disabled;
    @JsonProperty("disable_date") 
    public Object getDisable_date() { 
		 return this.disable_date; } 
    public void setDisable_date(Object disable_date) { 
		 this.disable_date = disable_date; } 
    Object disable_date;
    @JsonProperty("disable_reason") 
    public Object getDisable_reason() { 
		 return this.disable_reason; } 
    public void setDisable_reason(Object disable_reason) { 
		 this.disable_reason = disable_reason; } 
    Object disable_reason;
    @JsonProperty("analytics") 
    public Analytics getAnalytics() { 
		 return this.analytics; } 
    public void setAnalytics(Analytics analytics) { 
		 this.analytics = analytics; } 
    Analytics analytics;
    @JsonProperty("analytics-linux") 
    public AnalyticsLinux getAnalyticsLinux() { 
		 return this.analyticsLinux; } 
    public void setAnalyticsLinux(AnalyticsLinux analyticsLinux) { 
		 this.analyticsLinux = analyticsLinux; } 
    AnalyticsLinux analyticsLinux;
    @JsonProperty("generated_date") 
    public String getGenerated_date() { 
		 return this.generated_date; } 
    public void setGenerated_date(String generated_date) { 
		 this.generated_date = generated_date; } 
    String generated_date;
}
