
package com.covid19.match.pepipost.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A collection of key/value pairs following the pattern "NAME":"John". All are assumed to be strings. These attributes will apply to the html content of the body of your email, sender name and subject parameters. The total collective size of your attributes may not exceed 10,000 bytes per personalization object.
 * 
 * Each attribute can be used in your HTML as [%NAME%] for NAME , [%AGE%] for AGE. Spaces are not allowed within the attribute name, but special character underscore can be used.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ACCOUNT_BAL",
    "NAME"
})
public class Attributes {

    @JsonProperty("ACCOUNT_BAL")
    private String aCCOUNTBAL;
    @JsonProperty("NAME")
    private String nAME;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ACCOUNT_BAL")
    public String getACCOUNTBAL() {
        return aCCOUNTBAL;
    }

    @JsonProperty("ACCOUNT_BAL")
    public void setACCOUNTBAL(String aCCOUNTBAL) {
        this.aCCOUNTBAL = aCCOUNTBAL;
    }

    @JsonProperty("NAME")
    public String getNAME() {
        return nAME;
    }

    @JsonProperty("NAME")
    public void setNAME(String nAME) {
        this.nAME = nAME;
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
