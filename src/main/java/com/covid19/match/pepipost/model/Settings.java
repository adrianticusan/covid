
package com.covid19.match.pepipost.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bcc",
    "clicktrack",
    "footer",
    "opentrack",
    "unsubscribe",
    "ippool"
})
public class Settings {

    /**
     * You can set the email address on which a Bcc copy of the email will be sent.
     * 
     */
    @JsonProperty("bcc")
    @JsonPropertyDescription("You can set the email address on which a Bcc copy of the email will be sent.")
    private String bcc;
    /**
     * You can enable or disable click tracking for your emails.
     * 
     */
    @JsonProperty("clicktrack")
    @JsonPropertyDescription("You can enable or disable click tracking for your emails.")
    private Boolean clicktrack;
    /**
     * You can set common footer under your Pepipost account. And in case you want to use that footer than set this value to 1.
     * 
     */
    @JsonProperty("footer")
    @JsonPropertyDescription("You can set common footer under your Pepipost account. And in case you want to use that footer than set this value to 1.")
    private Boolean footer;
    /**
     * You can enable or disable open tracking for your emails.
     * 
     */
    @JsonProperty("opentrack")
    @JsonPropertyDescription("You can enable or disable open tracking for your emails.")
    private Boolean opentrack;
    /**
     * You can enable or disable unsubscribe tracking for your emails.
     * 
     */
    @JsonProperty("unsubscribe")
    @JsonPropertyDescription("You can enable or disable unsubscribe tracking for your emails.")
    private Boolean unsubscribe;
    /**
     * The name of the pool. Example: "pool0", "pool1" etc.
     * 
     * If you want to send the mail through the default IP of the account, pass the value as "default".
     * 
     * If you want the mail through the shared pool, pass the value as "shared".
     * 
     */
    @JsonProperty("ippool")
    @JsonPropertyDescription("The name of the pool. Example: \"pool0\", \"pool1\" etc.\n\nIf you want to send the mail through the default IP of the account, pass the value as \"default\".\n\nIf you want the mail through the shared pool, pass the value as \"shared\".")
    private String ippool;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * You can set the email address on which a Bcc copy of the email will be sent.
     * 
     */
    @JsonProperty("bcc")
    public String getBcc() {
        return bcc;
    }

    /**
     * You can set the email address on which a Bcc copy of the email will be sent.
     * 
     */
    @JsonProperty("bcc")
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * You can enable or disable click tracking for your emails.
     * 
     */
    @JsonProperty("clicktrack")
    public Boolean getClicktrack() {
        return clicktrack;
    }

    /**
     * You can enable or disable click tracking for your emails.
     * 
     */
    @JsonProperty("clicktrack")
    public void setClicktrack(Boolean clicktrack) {
        this.clicktrack = clicktrack;
    }

    /**
     * You can set common footer under your Pepipost account. And in case you want to use that footer than set this value to 1.
     * 
     */
    @JsonProperty("footer")
    public Boolean getFooter() {
        return footer;
    }

    /**
     * You can set common footer under your Pepipost account. And in case you want to use that footer than set this value to 1.
     * 
     */
    @JsonProperty("footer")
    public void setFooter(Boolean footer) {
        this.footer = footer;
    }

    /**
     * You can enable or disable open tracking for your emails.
     * 
     */
    @JsonProperty("opentrack")
    public Boolean getOpentrack() {
        return opentrack;
    }

    /**
     * You can enable or disable open tracking for your emails.
     * 
     */
    @JsonProperty("opentrack")
    public void setOpentrack(Boolean opentrack) {
        this.opentrack = opentrack;
    }

    /**
     * You can enable or disable unsubscribe tracking for your emails.
     * 
     */
    @JsonProperty("unsubscribe")
    public Boolean getUnsubscribe() {
        return unsubscribe;
    }

    /**
     * You can enable or disable unsubscribe tracking for your emails.
     * 
     */
    @JsonProperty("unsubscribe")
    public void setUnsubscribe(Boolean unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    /**
     * The name of the pool. Example: "pool0", "pool1" etc.
     * 
     * If you want to send the mail through the default IP of the account, pass the value as "default".
     * 
     * If you want the mail through the shared pool, pass the value as "shared".
     * 
     */
    @JsonProperty("ippool")
    public String getIppool() {
        return ippool;
    }

    /**
     * The name of the pool. Example: "pool0", "pool1" etc.
     * 
     * If you want to send the mail through the default IP of the account, pass the value as "default".
     * 
     * If you want the mail through the shared pool, pass the value as "shared".
     * 
     */
    @JsonProperty("ippool")
    public void setIppool(String ippool) {
        this.ippool = ippool;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
