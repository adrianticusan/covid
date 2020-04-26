
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
    "fromEmail",
    "fromName"
})
public class From {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("fromEmail")
    private String fromEmail;
    /**
     * The name of the recipient to whom you are sending an email.
     * 
     */
    @JsonProperty("fromName")
    @JsonPropertyDescription("The name of the recipient to whom you are sending an email.")
    private String fromName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("fromEmail")
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("fromEmail")
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * The name of the recipient to whom you are sending an email.
     * 
     */
    @JsonProperty("fromName")
    public String getFromName() {
        return fromName;
    }

    /**
     * The name of the recipient to whom you are sending an email.
     * 
     */
    @JsonProperty("fromName")
    public void setFromName(String fromName) {
        this.fromName = fromName;
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
